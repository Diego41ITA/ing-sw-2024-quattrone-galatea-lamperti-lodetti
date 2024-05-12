package it.polimi.ingsw.networking.rmi;

import it.polimi.ingsw.controller.GameControllerInterface;
import it.polimi.ingsw.controller.MainController;
import it.polimi.ingsw.controller.MainControllerInterface;
import it.polimi.ingsw.observer.GameObserver;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import static it.polimi.ingsw.view.PrintlnThread.Println;

public class ServerRMI extends UnicastRemoteObject implements MainControllerInterface {
    private final MainControllerInterface mainController;
    private static ServerRMI server = null;
    private static Registry registry = null;

    //questo crea un rmi server
    public static ServerRMI bind(){
        try{
            server = new ServerRMI();
            registry = LocateRegistry.createRegistry(1099);//default local host
            getRegistry().rebind( "server name", server);
            Println("the server is ready(RMI)");
        }catch(RemoteException e){
            System.err.println("Server error");
        }
        return getServer();
    }

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
    public static synchronized Registry getRegistry(){
        return registry;
    }

    //a questo punto abbiamo fatto la parte di creazione del server rimangono da implementare i metodi di
    // MainControllerInterface; sono quindi quei metodi per la creazione accessibili da un client connesso.
    @Override
    public GameControllerInterface createGame(GameObserver obs, String nick, int maxNumPlayers) throws RemoteException{
        GameControllerInterface stub = server.mainController.createGame(obs, nick, maxNumPlayers);

        try{
            //si prova ad esportare l'oggetto creato sulla porta 0 (dove è connesso il client per la ricezione degli input)
            UnicastRemoteObject.exportObject(stub, 0);
        }catch(RemoteException e){
            Println("damn");
        }

        return stub;
    }

    @Override
    public GameControllerInterface joinRandomGame(GameObserver obs, String nick) throws RemoteException/*, NoAvailableGameToJoinException */{
        GameControllerInterface stub = server.mainController.joinRandomGame(obs, nick);
        try{//manca da gestire exception per cui stub = null
            UnicastRemoteObject.exportObject(stub, 0);
        }catch(RemoteException e){
            Println("damn random game");
        }catch (NullPointerException e){
            obs.genericErrorWhenEnteringGame("No games currently available to join...", null);
        }
        return stub;
    }

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

    @Override
    public GameControllerInterface leaveGame(GameObserver obs, String nick, String gameId) throws RemoteException{
        return  server.mainController.leaveGame(obs, nick, gameId);
    }


}
