package it.polimi.ingsw.networking2;
import it.polimi.ingsw.controller.*;
import it.polimi.ingsw.observer.GameObserver;
import it.polimi.ingsw.view.GameFlow;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

//si occupa di implementare le azioni del client lavorando sulla rete.
public class ClientRMI implements ClientAction {
    //tutti i client istanziati su questa macchina avranno lo stesso riferimento
    private static MainControllerInterface request;
    private GameControllerInterface gameController = null;

    //notificationGetter è un oggetto su cui il SERVER chiamerà i metodi remoti (per notificare che è successo qualcosa)
    private GameObserver notificationGetter;
    private GameObserverHandlerClient gameObserverHandler;

    private String nickname;
    private Registry registry;
    private GameFlow flow;

    //ora ho il costruttore piu dei metodi ausiliari.
    public ClientRMI(GameFlow flow){
        super();
        gameObserverHandler = new GameObserverHandlerClient(flow);
        connect(); //connette il client con il server RMI

        this.flow = flow;
    }

    public void connect(){
        //va reso più solido chiaramente
        try{
            registry = LocateRegistry.getRegistry(/*...*/);
            request = (MainControllerInterface) registry.lookup(/* nome del server RMI */);

            notificationGetter = (GameObserver) UnicastRemoteObject.exportObject(gameObserverHandler, 0);

            //il client è pronto
        }catch(Exception e){
            //fa qualcosa...
        }
    }

    //bisogna implementare i metodi di ClientAction
    public void createGame(String nick) throws RemoteException{
        registry = LocateRegistry.getRegistry(/*...*/);
        request = (MainControllerInterface) registry.lookup(/*server name*/);
        gameController = request.createGame(notificationGetter, nick);
        nickname = nick;
    }
    //stessa cosa per gli altri


    //un esmpio di metodo di GameControllerInterface è:
    public boolean isMyTurn() throws RemoteException{
        return gameController.isMyTurn(nickname);
    }

    //... si possono invocare direttamente dall'attributo.

}
