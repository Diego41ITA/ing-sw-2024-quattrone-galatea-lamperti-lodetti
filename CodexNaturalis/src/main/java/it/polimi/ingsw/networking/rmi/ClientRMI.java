package it.polimi.ingsw.networking.rmi;
import it.polimi.ingsw.controller.*;
import it.polimi.ingsw.networking.ClientAction;
import it.polimi.ingsw.observer.GameObserver;
import it.polimi.ingsw.view.GameFlow;
import it.polimi.ingsw.view.GameObserverHandlerClient;

import java.awt.*;
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
            request = (MainControllerInterface) registry.lookup("nome del server");

            notificationGetter = (GameObserver) UnicastRemoteObject.exportObject(gameObserverHandler, 0);

            //il client è pronto
        }catch(Exception e){
            //fa qualcosa...
        }
    }

    //bisogna implementare i metodi di ClientAction
    @Override
    public void createGame(String nick, int maxNumberOfPlayers) throws RemoteException{
        registry = LocateRegistry.getRegistry(/*...*/);
        request = (MainControllerInterface) registry.lookup("server name");
        gameController = request.createGame(notificationGetter, nick, maxNumberOfPlayers);
        nickname = nick;
    }

    @Override
    public void joinRandomGame(String nick) throws  RemoteException{
        registry = LocateRegistry.getRegistry(/*...*/);
        request = (MainControllerInterface) registry.lookup("server name");
        gameController = request.joinRandomGame(notificationGetter, nick);
        nickname = nick;
    }

    @Override
    public void rejoin(String nick, String gameId) throws RemoteException{
        registry = LocateRegistry.getRegistry(/*...*/);
        request = (MainControllerInterface) registry.lookup("server name");
        gameController = request.rejoin(notificationGetter, nick, gameId);
        nickname = nick;
    }

    //metodi del GameController
    @Override
    public void setMaxNumOfPlayer(String name, int max){
        gameController.setMaxNumberPlayers(name, max);
    }

    @Override
    public void playCard(int numberOfCard, Point cord, String nick){
        gameController.playCard(numberOfCard, cord, nick); //per visualizzazione (anche TUI) sarebbe meglio
        //lavorare con degli int piuttosto che con tutto l'oggetto card (tanto il controller sa quali carte
        // ha in mano il giocatore).
    }

    @Override
    public void chooseGoal()...

    @Override
    public void pass(String nick){
        //...
    }

    @Override
    public void drawFromDeck(String nick, String deck){
        ...
    }

    @Override
    public void drawFromTable(String nick, int card){
        ...
    }
}
