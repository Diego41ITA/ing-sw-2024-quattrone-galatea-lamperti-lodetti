package it.polimi.ingsw.networking.socket.client;

import it.polimi.ingsw.model.card.Card;
import it.polimi.ingsw.model.card.GoalCard;
import it.polimi.ingsw.model.card.InitialCard;
import it.polimi.ingsw.model.card.PlayableCard;
import it.polimi.ingsw.model.exceptions.illegalOperationException;
import it.polimi.ingsw.networking.ClientAction;
import it.polimi.ingsw.networking.socket.client.message.*;
import it.polimi.ingsw.networking.socket.client.message.Color;
import it.polimi.ingsw.networking.socket.server.serverToClientMessage.ServerNotification;
import it.polimi.ingsw.view.GameFlow;
import it.polimi.ingsw.view.GameObserverHandlerClient;
import it.polimi.ingsw.view.PrintlnThread;

import java.awt.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.io.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ClientSocket extends Thread implements ClientAction {
    private Socket client;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private GameObserverHandlerClient gameObserverHandler;
    private GameFlow flow;

    private BlockingQueue<ServerNotification> notificationQueue;

    public ClientSocket(GameFlow flow){
        this.flow = flow;
        connect("127.0.0.1", 50000 );
        gameObserverHandler = new GameObserverHandlerClient(flow);
        notificationQueue = new LinkedBlockingQueue<>();
        this.start();
    }

    private void connect(String ip, int port){

        boolean attempt = false;
        do{
            try{
                client = new Socket(ip, port);
                out = new ObjectOutputStream(client.getOutputStream());
                in = new ObjectInputStream(client.getInputStream());
                attempt = false;
            }catch(IOException e){
                PrintlnThread.Println("something went wrong :( ...");
                attempt = true;
            }
        }while(attempt);
    }

    //serve per leggere le notifiche inviate dal server.
    public void run(){
        while(true){
            try{
                ServerNotification notification = (ServerNotification) in.readObject();
                notificationQueue.put(notification);
            }catch(IOException | ClassNotFoundException e){
                PrintlnThread.Println("something went wrong :( ...");
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void stopConnection() throws IOException{
        in.close();
        out.close();
        client.close();
    }

    void completeForwarding() throws IOException{
        out.flush();
        out.reset();
    }

    public void waitForNotification() throws InterruptedException{
        ServerNotification notification = notificationQueue.take();
        try {
            notification.execute(gameObserverHandler);
        } catch (IOException e) {
            PrintlnThread.Println("something went wrong ...");
            throw new RuntimeException(e);
        }
    }

    //tutte le operazioni, sono quelle presenti nel client RMI, con la differenza che ora scriveranno sull'out un
    // messaggio che riflette il tipo di operazione che si vuole eseguire.

    @Override
    public void createGame(String nick, int maxNumberOfPlayer) throws RemoteException, NotBoundException {
        try {
            out.writeObject(new CreateGameMessage(nick, maxNumberOfPlayer));
            completeForwarding();
            waitForNotification();
            waitForNotification();
        }catch(IOException | InterruptedException e){
            throw new RemoteException();
        }
    }

    @Override
    public void joinRandomGame(String nick) throws NotBoundException, RemoteException{
        try{
            out.writeObject(new JoinRandomMessage(nick));
            completeForwarding();
            //waitForNotification();
        }catch(IOException /*| InterruptedException*/ e){
            throw new RemoteException();
        }
    }

    @Override
    public void leaveGame(String nick, String gameId) throws RemoteException, NotBoundException{
        try{
            out.writeObject(new LeaveGameMessage(nick, gameId));
            completeForwarding();
            waitForNotification();
        }catch(IOException | InterruptedException e){
            throw new RemoteException();
        }
    }

    @Override
    public void rejoin(String nick, String gameID) throws RemoteException, NotBoundException{
        try{
            out.writeObject(new RejoinMessage(nick, gameID));
            completeForwarding();
            waitForNotification();
        }catch(IOException | InterruptedException e){
            throw new RemoteException();
        }
    }

    //ora scrivo tutti i metodi per cooperare con il GameController
    //nota, il controllo va fatto in game flow => se è errato deve inserire nuovamente un valore valido

    @Override
    public void playCard(PlayableCard playedCard, Point cord, String nick, boolean front) throws illegalOperationException, RemoteException {
        try{
            out.writeObject(new PlayCard(nick, playedCard, cord, front));
            completeForwarding();
            waitForNotification();
        }catch(IOException | InterruptedException e){
            throw new RemoteException();
        }
    }

    @Override
    public void chooseGoal(ArrayList<GoalCard> goals, int num, String nick){
        try{
            out.writeObject(new ChooseGoal(nick, num, goals));
            completeForwarding();
            waitForNotification();
        }catch(IOException | InterruptedException e){
            //throw new RemoteException();
        }

    }

    @Override
    public void goOn(){
        try{
            out.writeObject(new Pass());
            completeForwarding();
            waitForNotification();
        }catch(IOException | InterruptedException e){
            //nothing
        }
    }

    @Override
    public void setColor(String color, String nick){
        try{
            out.writeObject(new Color(nick, color));
            completeForwarding();
            waitForNotification();
        }catch(IOException | InterruptedException e){
            //nothing
        }
    }

    @Override
    public void drawPlayableCardFromTableOfDecks(String nick, String deck){
        try{
            out.writeObject(new DrawFromDeck(nick, deck));
            completeForwarding();
            waitForNotification();
        }catch(IOException | InterruptedException e){
            //nothing
        }
    }

    @Override
    public void drawFromTable(String nick, Card card){
        try{
            out.writeObject(new DrawFromTable(nick, card));
            completeForwarding();
            waitForNotification();
        }catch(IOException | InterruptedException e){
            //nothing
        }
    }

    @Override
    public void initializeHandPlayer(String nick){
        try{
            out.writeObject(new InitializeHand(nick));
            completeForwarding();
            waitForNotification();
        }catch(IOException | InterruptedException e){
            //nothing
        }
    }

    @Override
    public void setGameStation(String nick, InitialCard card, boolean isFront){
        try{
            out.writeObject(new SetGameStation(nick, card, isFront));
            completeForwarding();
            waitForNotification();
        }catch(IOException | InterruptedException e){
            //nothing
        }
    }

    //questo metodo non ha senso
    @Override
    public String calculateWinner(){
        try{
            out.writeObject(new CalculateWinner());
            completeForwarding();
            waitForNotification();
        }catch(IOException | InterruptedException e){
            //nothing
        }

        return "ciao";
    }

    @Override //prova
    public void startGame() throws IOException {
        try{
            out.writeObject(new StartGame());
            completeForwarding();
            waitForNotification();
        }catch(IOException | InterruptedException e){
            //nothing
        }
    }
}
