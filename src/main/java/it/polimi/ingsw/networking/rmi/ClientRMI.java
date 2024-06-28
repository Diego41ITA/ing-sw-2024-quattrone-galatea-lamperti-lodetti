package it.polimi.ingsw.networking.rmi;
import it.polimi.ingsw.controller.*;
import it.polimi.ingsw.model.card.Card;
import it.polimi.ingsw.model.card.GoalCard;
import it.polimi.ingsw.model.card.InitialCard;
import it.polimi.ingsw.model.card.PlayableCard;
import it.polimi.ingsw.networking.ClientAction;
import it.polimi.ingsw.observer.GameObserver;
import it.polimi.ingsw.view.FsmGame;

import java.awt.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * This class defines all the method to establish a connection with RMI, and also overrides the method
 * from {@link ClientAction} to allow them to be called on the exported remote object. It is the link
 * between {@link FsmGame} and {@link ControllerOfGame}.
 */
public class ClientRMI extends UnicastRemoteObject implements ClientAction {
    private static ControllerOfMatchesInterface request;
    private ControllerOfGameInterface gameController = null;
    private GameObserver notify;
    private String nickname;
    private Registry registry;
    private FsmGame flow;
    private String ipAddress;

    public ClientRMI(FsmGame flow, String ip) throws RemoteException {
        ipAddress = ip;
        this.flow = flow;
        connect(); //it connects the client to the RMI server

    }

    public void connect(){
        try{
            registry = LocateRegistry.getRegistry(ipAddress, 1099);
            request = (ControllerOfMatchesInterface) registry.lookup("server name");
            notify = (GameObserver) UnicastRemoteObject.exportObject(flow, 0);
        }catch(Exception e){
            System.err.println("impossible to connect to the RMI server");
        }
    }

    /**
     {@inheritDoc}
     */
    @Override
    public void createGame(String nick, int maxNumberOfPlayers) throws RemoteException, NotBoundException {
            gameController = request.createGame(notify, nick, maxNumberOfPlayers);
            nickname = nick;

    }

    /**
     {@inheritDoc}
     */
    @Override
    public void leaveGame(String nick, String idGame) throws NotBoundException, RemoteException {
        request.leaveGame(idGame, nick);
        gameController = null;
        nickname = null;
    }

    /**
     {@inheritDoc}
     */
    @Override
    public void rejoin(String idGame, String nickname) throws RemoteException{
        gameController = request.rejoinGame(notify, idGame, nickname);
        this.nickname = nickname;
    }

    /**
     {@inheritDoc}
     */
    @Override
    public void joinRandomGame(String nick) throws RemoteException, NotBoundException/*, NoAvailableGameToJoinException */{
        gameController = request.joinRandomGame(notify, nick);
        nickname = nick;
    }

    /**
     {@inheritDoc}
     */
    @Override
    public void playCard(PlayableCard playedCard, Point cord, String nick, boolean front) throws RemoteException{
        gameController.playCard(playedCard, nick, front, cord);
    }

    /**
     {@inheritDoc}
     */
    @Override
    public void chooseGoal(ArrayList<GoalCard> goals, int num, String nick) throws RemoteException{
        gameController.chooseGoal(goals, num, nick);
    }

    /**
     {@inheritDoc}
     */
    @Override
    public void goOn() throws RemoteException{
        gameController.goOn();
    }

    /**
     {@inheritDoc}
     */
    @Override
    public void drawPlayableCardFromTableOfDecks(String nick, String deck) throws RemoteException{
        gameController.drawPlayableCardFromTableOfDecks(deck, nick);
    }

    /**
     {@inheritDoc}
     */
    @Override
    public void drawFromTable(String nick, Card card) throws RemoteException{
        gameController.drawFromTable(card, nick);
    }

    /**
     {@inheritDoc}
     */
    @Override
    public void setGameStation(String nick, InitialCard card, boolean isFront) throws RemoteException {
        gameController.setGameStation(nick, card, isFront);
    }

    /**
     {@inheritDoc}
     */
    @Override
    public void setColor(String color, String name) throws RemoteException{
        gameController.setColor(color, name);
    }

    @Override
    public void initializeHandPlayer(String nick) throws RemoteException{
        gameController.initializeHandPlayer(nick);
    }

    /**
     {@inheritDoc}
     */
    @Override
    public void ping(String nick) throws RemoteException{
        gameController.ping(nick);
    }

    /**
     {@inheritDoc}
     */
    @Override
    public void startGame() throws RemoteException {
        gameController.start_Game();
    }

    /**
     {@inheritDoc}
     */
    @Override
    public void definePlayer(String nickname) throws RemoteException{
        gameController.definePlayer(nickname);
    }

    /**
     {@inheritDoc}
     */
    @Override
    public void initializeTurn(String nick) throws RemoteException {
        gameController.initializeTurn(nick);
    }
}
