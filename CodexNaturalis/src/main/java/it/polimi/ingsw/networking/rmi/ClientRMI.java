package it.polimi.ingsw.networking.rmi;
import it.polimi.ingsw.controller.*;
import it.polimi.ingsw.model.card.Card;
import it.polimi.ingsw.model.card.GoalCard;
import it.polimi.ingsw.model.card.InitialCard;
import it.polimi.ingsw.model.card.PlayableCard;
import it.polimi.ingsw.model.exceptions.illegalOperationException;
import it.polimi.ingsw.networking.ClientAction;
import it.polimi.ingsw.observer.GameObserver;
import it.polimi.ingsw.view.FsmGame;
import it.polimi.ingsw.view.GameObserverHandlerClient;

import java.awt.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

//si occupa di implementare le azioni del client lavorando sulla rete.
public class ClientRMI extends UnicastRemoteObject implements ClientAction {
    //tutti i client istanziati su questa macchina avranno lo stesso riferimento
    private static ControllerOfMatchesInterface request;
    private ControllerOfGameInterface gameController = null;

    //notificationGetter è un oggetto su cui il SERVER chiamerà i metodi remoti (per notificare che è successo qualcosa)
    private GameObserver notificationGetter;
    private GameObserverHandlerClient gameObserverHandler;

    private String nickname;
    private Registry registry;
    private FsmGame flow;

    private String ipAddress;
    //private Boolean firstCard;

    //ora ho il costruttore piu dei metodi ausiliari.
    public ClientRMI(FsmGame flow, String ip) throws RemoteException {
        //super();
        gameObserverHandler = new GameObserverHandlerClient(flow);
        ipAddress = ip;
        connect(); //connette il client con il server RMI
        this.flow = flow;
        //firstCard = true;
    }

    public void connect(){
        //va reso più solido chiaramente
        try{
            registry = LocateRegistry.getRegistry(ipAddress, 1099);
            request = (ControllerOfMatchesInterface) registry.lookup("server name");

            notificationGetter = (GameObserver) UnicastRemoteObject.exportObject(gameObserverHandler, 0);

            //il client è pronto
        }catch(Exception e){
            //fa qualcosa...
        }
    }

    //bisogna implementare i metodi di ClientAction
    @Override
    public void createGame(String nick, int maxNumberOfPlayers) throws RemoteException, NotBoundException {


            //registry = LocateRegistry.getRegistry("localhost", 1099);
            //request = (MainControllerInterface) registry.lookup("server name");
            gameController = request.createGame(notificationGetter, nick, maxNumberOfPlayers);
            nickname = nick;

    }

    @Override
    public void leaveGame(String nick, String idGame) throws NotBoundException, RemoteException {
        //registry = LocateRegistry.getRegistry("localhost", 1099);
        //request = (MainControllerInterface) registry.lookup("server name");
        request.leaveGame(idGame, nick);
        gameController = null;
        nickname = null;
        //firstCard = false;
    }

    @Override
    public void joinRandomGame(String nick) throws RemoteException, NotBoundException/*, NoAvailableGameToJoinException */{
        //registry = LocateRegistry.getRegistry("localhost", 1099);
        // request = (MainControllerInterface) registry.lookup("server name");
        gameController = request.joinRandomGame(notificationGetter, nick);
        nickname = nick;
    }

    @Override // questo metodo oltre a piazzare la carta calcola e aggiunge i punti generati dalla carta piazzata(sia se sia gold che risorsa)
    public void playCard(PlayableCard playedCard, Point cord, String nick, boolean front) throws RemoteException, illegalOperationException {
        gameController.playCard(playedCard, nick, front, cord);
    }

    @Override
    public void chooseGoal(ArrayList<GoalCard> goals, int num, String nick) throws RemoteException{
        gameController.chooseGoal(goals, num, nick);
    }

    @Override
    public void goOn() throws RemoteException{
        gameController.goOn();
    }

    @Override//serve a pescare dai deck
    public void drawPlayableCardFromTableOfDecks(String nick, String deck) throws RemoteException{
        gameController.drawPlayableCardFromTableOfDecks(deck, nick);
    }

    @Override
    public void drawFromTable(String nick, Card card) throws RemoteException{
        gameController.drawFromTable(card, nick);
    }

    @Override
    public void setGameStation(String nick, InitialCard card, boolean isFront) throws RemoteException {
        gameController.setGameStation(nick, card, isFront);
    }

    @Override
    public void setColor(String color, String name) throws RemoteException{
        gameController.setColor(color, name);
    }

    @Override
    public void initializeHandPlayer(String nick) throws RemoteException{
        gameController.initializeHandPlayer(nick);
    }

    @Override
    public String calculateWinner() throws RemoteException{
        return gameController.calculateWinner();
    }

    @Override //prova
    public void startGame() throws RemoteException {
        gameController.start_Game();
    }

    @Override
    public void definePlayer(String nickname) throws RemoteException{
        gameController.definePlayer(nickname);
    }

    @Override
    public void initializeTurn(String nick) throws RemoteException {
        gameController.initializeTurn(nick);
    }
}
