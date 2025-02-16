package it.polimi.ingsw.controller;

import com.google.gson.Gson;
import it.polimi.ingsw.GameView.GameView;
import it.polimi.ingsw.model.exceptions.GameEndedException;
import it.polimi.ingsw.model.exceptions.MaxPlayersInException;
import it.polimi.ingsw.model.gameDataManager.Game;
import it.polimi.ingsw.model.gameDataManager.Player;
import it.polimi.ingsw.model.gameDataManager.Status;
import it.polimi.ingsw.networking.PingTheClient;
import it.polimi.ingsw.observer.GameObserver;
import it.polimi.ingsw.observer.HandleObserver;
import it.polimi.ingsw.parse.SaverReader;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

/**
 * this class exposes all the ControllerOfMatches methods to operate on a list of GameController
 */
public class ControllerOfMatches extends UnicastRemoteObject implements ControllerOfMatchesInterface {
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

    private final IdSet idSet;

    private RoutineDelete routine;
    /**
     * Default constructor that initialize the ArrayList for the activeGames, the saved game ecc...
     */
    private ControllerOfMatches() throws RemoteException{
        super();
        this.activeGames = new ArrayList<ControllerOfGame>();
        restoreAllStoredGames();
        printActiveGames();

        //initializes the set of possible id.
        this.idSet = new IdSet(this.activeGames);

        //devo avviare le routine di ping per ogni controller dei game attivi.
        for(ControllerOfGame game: activeGames){
            new PingTheClient(game).start();
        }

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

    /**
     * this method returns the active games; it's use only for testing purpose.
     * @return the list of games
     */
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
        String gameID = "Game" + idSet.getRandomId();
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

        new PingTheClient(controller).start();

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
    public synchronized ControllerOfGameInterface joinRandomGame(GameObserver obs, String nick) throws RemoteException{
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

    /**
     * this method allows to rejoin a specific game by providing the nickname and the id
     * @param obs it's a reference to the client
     * @param gameId it is the game id
     * @param nick this is the nickname of the player
     * @return the interface of the game or null, if there are no matches.
     * @throws RemoteException
     */
    public ControllerOfGameInterface rejoinGame(GameObserver obs, String gameId, String nick) throws RemoteException{
        Optional<ControllerOfGame> availableGame = activeGames.stream()
                .filter(gameController ->
                        gameController.getGameId().equals(gameId) &&
                                gameController.getPlayers().keySet().stream()
                                        .anyMatch(player -> player.getNick().equals(nick))
                )
                .findFirst();

        if(availableGame.isPresent()){
            //the player can rejoin the game
            ControllerOfGame game = availableGame.get();
            game.addObserver(obs, nick);

            try {
                game.reconnectPlayer(nick);
            } catch (GameEndedException | MaxPlayersInException e) {
                throw new RuntimeException(e);
            }

            System.out.println("\t>Game " + game.getGameId() + " player:\"" + nick + "\" entered player");
            printActiveGames();

            for (HashMap.Entry<String, HandleObserver> entry : game.getObservers().entrySet()) {
                HandleObserver observer = entry.getValue();
                observer.notify_AddedPlayer(game.returnGame());
            }

            obs.reconnectedToGame(new GameView(game.returnGame()));
            return game;
        }
        else
            obs.genericErrorWhenEnteringGame("game not found", "");
        return null;
    }

    /**
     * this metho delete all the active games; used only for test purpose
     */
    @Deprecated
    public void clearActiveGames(){
        activeGames.clear();
    }

    /**
     * this method removes a game when someone of the player disconnect: it is either impossible to wait for other player
     * or to reload the game
     * @param gameID the id of game that needs to be removed.
     */
    public void removeGame(String gameID){
        Iterator<ControllerOfGame> iterator = activeGames.iterator();
        while(iterator.hasNext()){
            ControllerOfGame game = iterator.next();
            if(game.getGameId().equals(gameID)){
                for(HandleObserver obs: game.getObservers().values()) {
                    try {
                        obs.notify_abortGame();
                    } catch (IOException e) {
                        System.err.println("client " + obs + " not reachable");
                        throw new RuntimeException(e);
                    }
                }
                iterator.remove();
            }
        }

        printActiveGames();
    }

    /**
     * this method is called when this class is build: it reads all the files saved in specific directory, and it
     * recreates the ControllerOfGame object (for each game). Pay attention that an observer is added only when
     * it is passed with a rejoin method.
     */
    private void restoreAllStoredGames(){
        //read
        List<Game> storedGames = new ArrayList<>();
        String userHome = System.getProperty("user.home");
        String saveDirPath = userHome + File.separator + "SavedGames";
        File directory  = new File(saveDirPath);

        System.out.println("the path to the directory is: " + saveDirPath);

        File[] files = directory.listFiles();
        if(files != null) {
            for (File f : files) {
                if (!f.getName().equals("dummy.txt"))
                    storedGames.add(SaverReader.gameReader(f.getPath()));
            }
        }
        //Controller creation
        for(Game g : storedGames){
            try {
                ControllerOfGame controller = new ControllerOfGame(g);

                Map<String, Boolean> activity = g.getActivity();
                //every player is set as inactive
                activity.replaceAll((p, v) -> false);

                g.setActivity(activity);
                g.suspend(); //the game should start only when there are at least two people.

                this.activeGames.add(controller);
            }catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }

    }
}