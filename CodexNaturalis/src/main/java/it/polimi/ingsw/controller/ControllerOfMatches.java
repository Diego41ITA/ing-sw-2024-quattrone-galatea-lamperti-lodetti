package it.polimi.ingsw.controller;

import it.polimi.ingsw.GameView.GameView;
import it.polimi.ingsw.model.exceptions.GameEndedException;
import it.polimi.ingsw.model.exceptions.MaxPlayersInException;
import it.polimi.ingsw.model.gameDataManager.Player;
import it.polimi.ingsw.model.gameDataManager.Status;
import it.polimi.ingsw.observer.GameObserver;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

/**
 * this class exposes all the ControllerOfMatches methods to operate on a list of GameController
 */
public class ControllerOfMatches extends UnicastRemoteObject implements /*Serializable,*/ ControllerOfMatchesInterface /*, Runnable*/ {
    /**
     * List of all the activeGames, represented by their controllers
     */
    private final List<ControllerOfGame> activeGames;

    /**
     * Attribute for the SingleTon pattern
     */
    private static ControllerOfMatches mainController;

    /**
     * Useful to generate a random number
     */
    private final Random random = new Random();

    private RoutineDelete routine;
    /**
     * Default constructor that initialize the ArrayList for the activeGames
     */
    private ControllerOfMatches() throws RemoteException{
        super();
        this.activeGames = new ArrayList<ControllerOfGame>();

        //if the server comes back up it can restore all the saved games


        //it starts a routine operation
        routine = new RoutineDelete(this);
        Thread routineDelete = new Thread(routine);
        routineDelete.start();
    }

    /**
     * SingleTon pattern: allows to have only 1 instance of the MainController class
     * @return the only possible instance
     */
    public synchronized static ControllerOfMatches getMainController() throws RemoteException {
        if(mainController == null)
            mainController = new ControllerOfMatches();
        return mainController;
    }
    public List<ControllerOfGame> getActiveGames(){
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
    public synchronized ControllerOfGameInterface createGame(GameObserver obs, String nick, int maxNumPlayers) throws RemoteException{
        Player player = new Player(nick);
        String gameID = "Game" + (activeGames.size() + 1);
        ControllerOfGame controller = new ControllerOfGame(gameID, maxNumPlayers);
        controller.addObserver(obs, nick);
        controller.readiness.put(nick, 0);
        activeGames.add(controller);

        System.out.println("\t>Game " + controller.getGameId() + " added to runningGames, created by player:\"" + nick + "\"");
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
    public synchronized ControllerOfGameInterface joinRandomGame(GameObserver obs, String nick) throws RemoteException/*, NoAvailableGameToJoinException*/ {
        List<ControllerOfGame> availableGames = activeGames.stream()
                .filter(gameController ->
                        gameController.getStatus().equals(Status.WAITING) &&
                                !gameController.checkIfStart() &&
                                gameController.getPlayers().keySet().stream()
                                        .noneMatch(player -> player.getNick().equals(nick))
                )
                .toList();

        // If there are available games, try to join one
        if (!availableGames.isEmpty()) {
            ControllerOfGame randomAvailableGame = availableGames.get(random.nextInt(availableGames.size()));
            Player player = new Player(nick);
            try {
                randomAvailableGame.addObserver(obs, nick);
                randomAvailableGame.addPlayer(player);

                randomAvailableGame.readiness.put(nick, 0);

                System.out.println("\t>Game " + randomAvailableGame.getGameId() + " player:\"" + nick + "\" entered player");
                printActiveGames();

                obs.randomGameJoined(randomAvailableGame.getGameId());

                return randomAvailableGame;
            } catch (MaxPlayersInException e) {
                randomAvailableGame.removeObserver(player);
                obs.genericErrorWhenEnteringGame(e.getMessage(), randomAvailableGame.getGameId());
            }
        }
        else{
            obs.genericErrorWhenEnteringGame("No games currently available to join...", "");
        }
        return null;
    }


    /**
     * Allows a player to leave a game
     *
     * @param nick Player's nickname
     * @param gameID unique ID of the game to leave
     * @throws RemoteException if something goes wrong
     */
    @Override
    public void leaveGame(String gameID, String nick) throws RemoteException {
        routine.leaveGame(gameID, nick);
    }

    /**
     * Print all games currently running
     */
    public void printActiveGames() {
        System.out.println("\t\trunningGames: ");
        for (ControllerOfGame game : activeGames) {
            System.out.println("\t\t" + game.getGameId() + " ");
        }
        System.out.println("");
    }

    public ControllerOfGameInterface rejoinGame(GameObserver obs, String gameId, String nick) throws RemoteException{
        Optional<ControllerOfGame> availableGames = activeGames.stream()
                .filter(gameController ->
                        gameController.getGameId().equals(gameId) &&
                                gameController.getPlayers().keySet().stream()
                                        .anyMatch(player -> player.getNick().equals(nick))
                )
                .findFirst();

        if(availableGames.isPresent()){
            //the player can rejoin the game
            ControllerOfGame game = availableGames.get();
            game.addObserver(obs, nick);
            System.out.println("\t>Game " + game.getGameId() + " player:\"" + nick + "\" entered player");
            printActiveGames();

            obs.reconnectedToGame(new GameView(game.returnGame()));
            return game;
        }
        else
            obs.genericErrorWhenEnteringGame("No games currently available to join...", "");
        return null;
    }

    //bisogna aggiungere anche i metodi per il salvataggio e l'eliminazione (questo conviene farlo con un thread).
    //some getter:
    public List<ControllerOfGame> getActiveGame(){
        return this.activeGames;
    }

    //It's used in TestController, leave this method
    public void clearActiveGames(){
        activeGames.clear();
    }
}