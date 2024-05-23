package it.polimi.ingsw.networking.rmi;

import it.polimi.ingsw.controller.ControllerOfMatchesInterface;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import static it.polimi.ingsw.controller.ControllerOfMatches.getMainController;

/**
 * this class defines the server for RMI communication
 * This class implements the Singleton pattern
 */
public class ServerRMI /* extends UnicastRemoteObject implements MainControllerInterface */{
   // private final MainControllerInterface mainController;
   //private static ServerRMI server = null;
    //private static Registry registry = null;

    /**
     * manage all the necessary stuff to build the RMI server
     * @return an object Server RMI
     */
    /*
    public static ServerRMI bind(){
        try{
            server = new ServerRMI();
            registry = LocateRegistry.createRegistry(1099);//default local host
            getRegistry().rebind( "server name", server);
            System.out.println("the server is ready(RMI)");
        }catch(RemoteException e){
            System.err.println("Server error");
        }
        return getServer();
    }
     */

    public ServerRMI(){
        try {
            Registry registry = LocateRegistry.createRegistry(1099);
            ControllerOfMatchesInterface mainController = getMainController();
            registry.rebind("server name", mainController);
            System.out.println("Server RMI is running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void startServer(){
        new ServerRMI();
    }

    /*
    public ServerRMI() throws RemoteException {
        super(0);
        mainController = MainController.getMainController();
    }

    public static synchronized ServerRMI getServer(){
        if(server == null){
            try{
                server = new ServerRMI();
            }catch(RemoteException e){
                System.err.println("error getServer");
            }
        }
        return server;
    }
    public static synchronized Registry getRegistry() throws RemoteException{
        return registry;
    }
     */

    /**
     * this method handles the request to create a new game.
     * @param obs it is the object that handles the notification for the client.
     * @param nick the client's nickname
     * @param maxNumPlayers the maximum number of player that can join this specific game.
     * @return the gameController interface of the GameController
     * @throws RemoteException
     */
    /*
    @Override
    public GameControllerInterface createGame(GameObserver obs, String nick, int maxNumPlayers) throws RemoteException{
        GameControllerInterface stub = server.mainController.createGame(obs, nick, maxNumPlayers);

        try{
            //si prova ad esportare l'oggetto creato sulla porta 0 (dove è connesso il client per la ricezione degli input)
            UnicastRemoteObject.exportObject(stub, 0);
        }catch(RemoteException e){
            System.out.println("damn");
            e.printStackTrace();
            e.getCause();
            e.getMessage();
        }

        return stub;
    }

    /**
     * this method handles the request to join a new game
     * @param obs it is the object that handles the notification for the client.
     * @param nick the client's nickname
     * @return the gameController interface of the GameController
     * @throws RemoteException
     */
    /*
    @Override
    public GameControllerInterface joinRandomGame(GameObserver obs, String nick) throws RemoteException{
        GameControllerInterface stub = server.mainController.joinRandomGame(obs, nick);
        try{
            UnicastRemoteObject.exportObject(stub, 0);
        }catch (NullPointerException e){
            obs.genericErrorWhenEnteringGame("No games currently available to join...", null);
        }catch(RemoteException e){
            e.printStackTrace();
            e.getCause();
        }
        return stub;
    }

    /**
     * this method handles the request to rejoin a game
     * @param obs it is the object that handles the notification for the client.
     * @param nick the client's nickname
     * @param gameId It the id that the client wants to rejoin.
     * @return the gameController interface of the GameController
     * @throws RemoteException
     */
    /*
    @Override
    public GameControllerInterface rejoin(GameObserver obs, String nick, String gameId) throws RemoteException{
        GameControllerInterface stub = server.mainController.rejoin(obs, nick, gameId);
        try{
            UnicastRemoteObject.exportObject(stub, 0);
        }catch(RemoteException e){
            //...
        }
        return stub;
    }

    /**
     * this method handles the request to leave the game
     * @param obs it is the object that handles the notification for the client.
     * @param nick the client's nickname
     * @param gameId the id of the game that the client wants to leave.
     * @return the gameController interface of the GameController
     * @throws RemoteException
     */
    /*
    @Override
    public GameControllerInterface leaveGame(GameObserver obs, String nick, String gameId) throws RemoteException{
        return  server.mainController.leaveGame(obs, nick, gameId);
    }

     */


}
