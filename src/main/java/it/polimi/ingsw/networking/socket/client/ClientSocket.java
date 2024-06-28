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
import it.polimi.ingsw.view.FsmGame;

import java.awt.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.io.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Receives all the incoming notifications from the server, and it processes them
 * calling the methods of {@link FsmGame}
 */
public class ClientSocket extends Thread implements ClientAction {
    private Socket client;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private FsmGame flow;

    private BlockingQueue<ServerNotification> notificationQueue;
    private String ipAddress;

    public ClientSocket(FsmGame flow,String ip){
        this.flow = flow;
        ipAddress = ip;
        connect(ipAddress, 50000 );
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
                System.err.println("something went wrong :( ...");
                attempt = false;
            }
        }while(attempt);
    }

    /**
     * It read the notification from the server
     */
    public void run(){
        while(!this.isInterrupted()){
            try{
                ServerNotification notification = (ServerNotification) in.readObject();
                if(notification.isSynchronous())
                    notificationQueue.put(notification);
                else{
                    Thread trd = new Thread(() -> executeAsync(notification));
                    trd.start();
                }
            }catch(IOException | ClassNotFoundException | InterruptedException | NullPointerException e) {
                System.err.println("socket server disconnected: detection happened in ClientSocket Thread");
                e.printStackTrace();
                this.interrupt();
            }
        }
    }

    /**
     * It exectues an async notification to avoid deadlocks
     * @param notification the notification to execute
     */
    public void executeAsync(ServerNotification notification){
        try {
            notification.execute(flow);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            System.out.println("something went wrong during the execution of: " + notification.getClass().getName());
        }
    }

    public void stopConnection() throws IOException{
        in.close();
        out.close();
        client.close();
    }

    /**
     * Ensure the correct forwarding of the message
     * @throws IOException
     */
    void completeForwarding() throws IOException{
        out.flush();
        out.reset();
    }

    /**
     * Waits for notifications
     * @throws InterruptedException
     */
    public void waitForNotification() throws InterruptedException{
        ServerNotification notification = notificationQueue.take();
        try {
            notification.execute(flow); //invece di notificationGetter
        } catch (IOException e) {
            System.out.println("something went wrong ...");
            throw new RuntimeException(e);
        }
    }


    //all these methods are similar to the one in ClientRMI, but they also write on the "out" the message
    //corresponding to the operation to execute

    /**
     *{@inheritDoc}
     */
    @Override
    public void createGame(String nick, int maxNumberOfPlayer) throws RemoteException, NotBoundException {
        try {
            out.writeObject(new CreateGameMessage(nick, maxNumberOfPlayer));
            completeForwarding();
            waitForNotification();
        }catch(IOException | InterruptedException e){
            throw new RemoteException();
        }
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public void joinRandomGame(String nick) throws NotBoundException, RemoteException{
        try{
            out.writeObject(new JoinRandomMessage(nick));
            completeForwarding();
            waitForNotification();
        }catch(IOException | InterruptedException e){
            throw new RemoteException();
        }
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public synchronized void leaveGame(String nick, String gameId) throws RemoteException, NotBoundException{
        try{
            out.writeObject(new LeaveGameMessage(nick, gameId));
            completeForwarding();
        }catch(IOException e){
            throw new RemoteException();
        }
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public void rejoin(String idGame, String nick) throws RemoteException{
        try{
            out.writeObject(new RejoinMessage(nick, idGame));
            completeForwarding();
            waitForNotification();
        }catch(IOException | InterruptedException e){
            throw new RemoteException();
        }
    }

    //now all the methods to link with the ControllerOfGame
    //all the needed checks are done in the controller, that eventually catch an exception

    /**
     *{@inheritDoc}
     */
    @Override
    public synchronized void playCard(PlayableCard playedCard, Point cord, String nick, boolean front) throws RemoteException {
        try{
            out.writeObject(new PlayCard(nick, playedCard, cord, front));
            completeForwarding();
            waitForNotification();
        }catch(IOException | InterruptedException e){
            throw new RemoteException();
        }
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public synchronized void chooseGoal(ArrayList<GoalCard> goals, int num, String nick){
        try{
            out.writeObject(new ChooseGoal(nick, num, goals));
            completeForwarding();
            waitForNotification();  //aspetta quella di turn o quella di updateGameStations
        }catch(IOException | InterruptedException e){
            throw new RuntimeException(e);
        }

    }

    /**
     *{@inheritDoc}
     */
    @Override
    public synchronized void goOn(){
        try{
            out.writeObject(new Pass());
            completeForwarding();
        }catch(IOException /*| InterruptedException*/ e){
            throw new RuntimeException(e);
        }
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public synchronized void setColor(String color, String nick){
        try{
            out.writeObject(new Color(nick, color));
            completeForwarding();
            waitForNotification();
        }catch(IOException | InterruptedException e){
            throw new RuntimeException(e);
        }
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public synchronized void drawPlayableCardFromTableOfDecks(String nick, String deck){
        try{
            out.writeObject(new DrawFromDeck(nick, deck));
            completeForwarding();
            waitForNotification();
        }catch(IOException | InterruptedException e){
            throw new RuntimeException(e);
        }
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public synchronized void drawFromTable(String nick, Card card){
        try{
            out.writeObject(new DrawFromTable(nick, card));
            completeForwarding();
            waitForNotification();
        }catch(IOException | InterruptedException e){
            throw new RuntimeException(e);
        }
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public synchronized void initializeHandPlayer(String nick){
        try{
            out.writeObject(new InitializeHand(nick));
            completeForwarding();
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public synchronized void setGameStation(String nick, InitialCard card, boolean isFront){
        try{
            out.writeObject(new SetGameStation(nick, card, isFront));
            completeForwarding();
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public synchronized void ping(String nick){
        try{
            out.writeObject(new Ping(nick));
            completeForwarding();
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    /**
     *{@inheritDoc}
     */
    @Override //prova
    public synchronized void startGame() throws IOException {
        try{
            out.writeObject(new StartGame());
            completeForwarding();
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public synchronized void initializeTurn(String nick) throws RemoteException{
        try{
            out.writeObject(new InitializeTurn(nick));
            completeForwarding();
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public synchronized void definePlayer(String nick) throws RemoteException{
        try{
            out.writeObject(new DefinePlayer(nick));
            completeForwarding();
            waitForNotification(); //waits updateInitialCard
            waitForNotification(); //waits goalCardsDrawed
            waitForNotification(); //waits updateHandAndTable
        }catch(IOException | InterruptedException e){
            throw new RuntimeException(e);
        }
    }
}
