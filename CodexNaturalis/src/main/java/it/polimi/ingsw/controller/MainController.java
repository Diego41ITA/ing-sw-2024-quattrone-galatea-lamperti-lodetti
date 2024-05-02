package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.gameDataManager.Player;
import it.polimi.ingsw.model.gameDataManager.Status;
import it.polimi.ingsw.observer.GameObserver;

import java.rmi.RemoteException;
import java.util.*;
import java.io.Serializable;

// questa classe rappresenta semplicemente il controller per la creazione ed eliminazione delle partite
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
        printRunningGames();

        try {
            controller.addPlayer(player);
        } catch (MaxPlayersInException | PlayerAlreadyInException e) {
            obs.genericErrorWhenEnteringGame(e.getMessage());
        }

        return controller;
    }

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
                printRunningGames();
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



    @Override
    public GameControllerInterface leaveGame(GameObserver obs, String nick, String gameId) throws RemoteException {
        return null;
    }

    //stessa cosa per tutti gli altri.

    //bisogna aggiungere anche i metodi per il salvataggio e l'eliminazione (questo conviene farlo con un thread).

}
