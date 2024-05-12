package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.exceptions.GameEndedException;
import it.polimi.ingsw.model.exceptions.MaxPlayersInException;
import it.polimi.ingsw.model.exceptions.NoAvailableGameToJoinException;
import it.polimi.ingsw.model.exceptions.PlayerAlreadyInException;
import it.polimi.ingsw.model.gameDataManager.Player;
import it.polimi.ingsw.model.gameDataManager.Status;
import it.polimi.ingsw.observer.GameObserver;
import it.polimi.ingsw.observer.HandleObserver;
import it.polimi.ingsw.view.PrintlnThread;
import static it.polimi.ingsw.view.PrintlnThread.Println;

import java.rmi.RemoteException;
import java.util.*;
import java.io.Serializable;

public class MainController implements Serializable, MainControllerInterface /*, Runnable*/ {
    /**
     * List of all the activeGames, represented by their controllers
     */
    private List<GameController> activeGames;

    /**
     * Attribute for the SingleTon pattern
     */
    private static MainController mainController;

    /**
     * Useful to generate a random Number
     */
    private final Random random = new Random();

    /**
     * Default constructor that initialize the ArrayList for the activeGames
     */
    private MainController(){
        this.activeGames = new ArrayList<GameController>();
    }

    /**
     * SingleTon pattern: allows to have only 1 instance of the MainController class
     * @return the only possible instance
     */
    public synchronized static MainController getMainController(){
        if(mainController == null)
            mainController = new MainController();
        return mainController;
    }
    public List<GameController> getActiveGames(){
        return activeGames;
    }

    /**
     * Create a new game by creating his gameController and
     * allows a Player to join it
     *
     * @param obs           GameObserver associated with the player who is creating the game
     * @param nick          Player's nickname
     * @param maxNumPlayers Max number of players selected during game creation
     * @return GameControllerInterface of the created game
     * @throws RemoteException it could throw this exception if something goes wrong
     */
    @Override
    public synchronized GameControllerInterface createGame(GameObserver obs, String nick, int maxNumPlayers) throws RemoteException{
        Player player = new Player(nick);
        String gameID = "Game" + (activeGames.size() + 1);
        GameController controller = new GameController(gameID, maxNumPlayers);
        controller.addObserver(obs, player);
        activeGames.add(controller);

        PrintlnThread.Println("\t>Game " + controller.getGameId() + " added to runningGames, created by player:\"" + nick + "\"");
        printActiveGames();

        try {
            controller.addPlayer(player);
            obs.newGameCreated(gameID);
        } catch (MaxPlayersInException e) {
            obs.genericErrorWhenEnteringGame(e.getMessage(), gameID);
        }

        return controller;
    }

    /**
     * Allows Player to join a random game from the activeGame list. If the number of connected players
     * is equal to the maxNumberPlayer, this method start the game.
     * @param obs GameObserver associated with the player who is joining the game
     * @param nick Player's nickname
     * @return GameControllerInterface of the created game
     * @throws RemoteException it could throw this exception when something goes wrong
     */
    @Override
    public synchronized GameControllerInterface joinRandomGame(GameObserver obs, String nick) throws RemoteException/*, NoAvailableGameToJoinException*/ {
        List<GameController> availableGames = activeGames.stream()
                .filter(gameController ->
                        gameController.getStatus().equals(Status.WAITING) &&
                                !gameController.checkIfStart() &&
                                gameController.getPlayers().keySet().stream()
                                        .noneMatch(player -> player.getNick().equals(nick))
                )
                .toList();

        // If there are available games, try to join one
        if (!availableGames.isEmpty()) {
            GameController randomAvailableGame = availableGames.get(random.nextInt(availableGames.size()));
            Player player = new Player(nick);
            try {
                randomAvailableGame.addObserver(obs, player);
                randomAvailableGame.addPlayer(player);

                Println("\t>Game " + randomAvailableGame.getGameId() + " player:\"" + nick + "\" entered player");
                printActiveGames();

                obs.randomGameJoined(randomAvailableGame.getGameId());

                if (randomAvailableGame.checkIfStart()){
                    randomAvailableGame.startGame();
                }

                return randomAvailableGame;
            } catch (MaxPlayersInException e) {
                randomAvailableGame.removeObserver(player);
                obs.genericErrorWhenEnteringGame(e.getMessage(), randomAvailableGame.getGameId());
            }
        } else {
        }
        return null;
    }

    /**
     * Allows Player to rejoin the game after being disconnected
     * @param obs GameObserver associated with the player who is rejoining the game
     * @param nick Player's nickname
     * @param GameID unique ID of the game to rejoin
     * @return GameControllerInterface of the joined game
     * @throws RemoteException it could throw this when something goes wrong
     */
    public synchronized GameControllerInterface rejoin(GameObserver obs, String nick, String GameID) throws RemoteException {
        for (GameController game : activeGames) {
            if (game.getGameId().equals(GameID)) {
                try {
                    for (Player player : game.getPlayers().keySet()) {
                        if (player.getNick().equals(nick)) {
                            game.addObserver(obs, player);
                            game.reconnectPlayer(player);
                            obs.reconnectedToGame(GameID);
                            return game;
                        }
                    }
                    obs.genericErrorWhenEnteringGame("The player" + nick + "was not connected in the running game.", GameID);
                    return null;
                } catch (MaxPlayersInException | GameEndedException e) {
                    obs.genericErrorWhenEnteringGame("An error occured during reconnection to the game", GameID);
                }
            }
        }
        obs.gameIdNotExists(GameID);
        return null;
    }

    /**
     * Allows a player to leave a game
     *
     * @param obs GameObserver associated with the player who is rejoining the game
     * @param nick Player's nickname
     * @param gameID unique ID of the game to leave
     * @return the new game controller interface
     * @throws RemoteException it could throw it if something goes wrong
     */
    @Override
    public GameControllerInterface leaveGame(GameObserver obs, String nick, String gameID) throws RemoteException {
        for (GameController game : activeGames) {
            if (game.getGameId().equals(gameID)) {
                game.leave(nick);
                Println("\t>Game " + game.getGameId() + " player: \"" + nick + "\" decided to leave");
                printActiveGames();
                if (game.getNumOfOnlinePlayers() == 0) {
                    deleteGame(gameID);
                }
            }
        }
        return null;
    }

    /**
     * Remove the @param idGame from the {@link MainController#activeGames}
     *
     * @param GameID unique ID of the game to delete
     */
    public synchronized void deleteGame(String GameID) {
        for (GameController game : activeGames) {
            if (game.getGameId().equals(GameID)) {
                activeGames.remove(game);
                Println("\t>Game " + GameID + " removed from activeGames");
                printActiveGames();
            }
        }
    }

    /**
     * Print all games currently running
     */
    private void printActiveGames() {
        Println("\t\trunningGames: ");
        for (GameController game : activeGames) {
            Println("\t\t" + game.getGameId() + " ");
        }
        Println("");
    }

    //bisogna aggiungere anche i metodi per il salvataggio e l'eliminazione (questo conviene farlo con un thread).
}
