package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.gameDataManager.Player;
import it.polimi.ingsw.model.gameDataManager.Status;
import it.polimi.ingsw.observer.GameObserver;

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
        this.activeGames = new ArrayList<>();
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

    /**
     * Create a new game by creating his gameController and
     * allows a Player to join to it
     *
     * @param obs GameObserver associated with the player who is creating the game
     * @param nick Player's nickname
     * @return GameControllerInterface of the created game
     * @throws RemoteException
     */
    @Override
    public synchronized GameControllerInterface createGame(GameObserver obs, String nick) throws RemoteException{
        Player player = new Player(nick);
        String gameID = "Game" + (activeGames.size() + 1);
        GameController controller = new GameController(gameID);
        controller.addObserver(obs, player);
        activeGames.add(controller);

        printAsync("\t>Game " + controller.getGameId() + " added to runningGames, created by player:\"" + nick + "\"");
        printActiveGames();

        try {
            controller.addPlayer(player);
        } catch (MaxPlayersInException | PlayerAlreadyInException e) {
            obs.genericErrorWhenEnteringGame(e.getMessage());
        }

        return controller;
    }

    /**
     * Allows Player to join a random game from the activeGame list
     * @param obs GameObserver associated with the player who is joining the game
     * @param nick Player's nickname
     * @return GameControllerInterface of the created game
     * @throws RemoteException
     */
    @Override
    public synchronized GameControllerInterface joinRandomGame(GameObserver obs, String nick) throws RemoteException {
        List<GameController> availableGames = new ArrayList<>();

        // Filter available games
        for (GameController gameController : activeGames) {
            if (gameController.getStatus().equals(Status.WAITING) && !gameController.checkIfFull) {
                availableGames.add(gameController);
            }
        }

        // If there are available games, try to join the first one
        if (!availableGames.isEmpty()) {
            GameController randomAvailableGame = availableGames.get(random.nextInt(availableGames.size()));
            Player player = new Player(nick);
            try {
                randomAvailableGame.addObserver(obs, player);
                randomAvailableGame.addPlayer(player);

                printAsync("\t>Game " + randomAvailableGame.getGameId() + " player:\"" + nick + "\" entered player");
                printActiveGames();
                return randomAvailableGame;
            } catch (MaxPlayersInException | PlayerAlreadyInException e) {
                randomAvailableGame.removeObserver(lis, player);
                obs.genericErrorWhenEnteringGame(e.getMessage());
            }
        } else {
            // No available games
            obs.genericErrorWhenEnteringGame("No games currently available to join...");
        }
        return null;
    }

    /**
     * Allows Player to rejoin the game after being disconnected
     * @param obs GameObserver associated with the player who is rejoining the game
     * @param nick Player's nickname
     * @param GameID univoque ID of the game to rejoin
     * @return GameControllerInterface of the joined game
     * @throws RemoteException
     */
    public synchronized GameControllerInterface rejoin(GameObserver obs, String nick, String GameID) throws RemoteException {
        for (GameController game : activeGames) {
            if (game.getGameId().equals(GameID)) {
                try {
                    for (Player player : game.getPlayers()) {
                        if (player.getNickname().equals(nick)) {
                            game.addListener(obs, player);
                            game.reconnectPlayer(player);
                            return game;
                        }
                    }
                    obs.genericErrorWhenEnteringGame("The nickname used was not connected in the running game.");
                    return null;
                } catch (MaxPlayersInException e) {
                    throw new RuntimeException(e);
                } catch (GameEndedException e) {
                    throw new RuntimeException(e);
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
     * @param GameID univoque ID of the game to leave
     * @return
     * @throws RemoteException
     */
    @Override
    public GameControllerInterface leaveGame(GameObserver obs, String nick, String GameID) throws RemoteException {
        for (GameController game : activeGames) {
            if (game.getGameId().equals(GameID)) {
                game.leave(obs, nick);
                printAsync("\t>Game " + ris.get(0).getGameId() + " player: \"" + nick + "\" decided to leave");
                printActiveGames();

                if (game.getNumOfOnlinePlayers() == 0) {
                    deleteGame(idGame);
                }
            }
        }
        return null;
    }

    /**
     * Remove the @param idGame from the {@link MainController#activeGames}
     *
     * @param GameID univoque ID of the game to delete
     */
    public synchronized void deleteGame(String GameID) {
        for (GameController game : activeGames) {
            if (game.getGameId().equals(GameID)) {
                activeGames.remove(game);
                printAsync("\t>Game " + GameID + " removed from activeGames");
                printActiveGames();
            }
        }
    }

    /**
     * Print all games currently running
     */
    private void printActiveGames() {
        printAsyncNoLine("\t\trunningGames: ");
        for (GameController game : activeGames) {
            printAsync(game.getGameId() + " "));
        }
        printAsync("");
    }

    //bisogna aggiungere anche i metodi per il salvataggio e l'eliminazione (questo conviene farlo con un thread).
}
