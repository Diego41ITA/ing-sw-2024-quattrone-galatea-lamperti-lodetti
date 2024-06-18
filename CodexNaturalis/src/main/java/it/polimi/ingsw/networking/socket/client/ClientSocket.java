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
 * Receives all the incoming notifications from the server, and it processes them by passing
 * {@link //GameObserverHandlerClient}, that calls the methods of {@link FsmGame}
 */
public class ClientSocket extends Thread implements ClientAction {
    private Socket client;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    //private final GameObserverHandlerClient gameObserverHandler;    //invoca i metodi di gameFlow
    private FsmGame flow;

    private BlockingQueue<ServerNotification> notificationQueue;
    private String ipAddress;

    public ClientSocket(FsmGame flow,String ip){
        this.flow = flow;
        ipAddress = ip;
        connect(ipAddress, 50000 );
        //gameObserverHandler = new GameObserverHandlerClient(flow);
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
                System.out.println("something went wrong :( ...");
                attempt = true;
            }
        }while(attempt);
    }

    //serve per leggere le notifiche inviate dal server.
    public void run(){
        while(!this.isInterrupted()){
            try{
                ServerNotification notification = (ServerNotification) in.readObject();
                //System.out.println("ho ricevuto: " + notification.getClass().getName());
                if(notification.isSynchronous())
                    notificationQueue.put(notification);
                else{
                    Thread trd = new Thread(() -> executeAsync(notification));
                    trd.start();
                }
            }catch(IOException | ClassNotFoundException | InterruptedException e) {
                System.err.println("socket server disconnected: detection happened in ClientSocket Thread");
                this.interrupt();
            }
        }
    }

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

    void completeForwarding() throws IOException{
        out.flush();
        out.reset();
    }

    public void waitForNotification() throws InterruptedException{
        ServerNotification notification = notificationQueue.take();
        try {
            notification.execute(flow); //invece di notificationGetter
        } catch (IOException e) {
            System.out.println("something went wrong ...");
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
        }catch(IOException | InterruptedException e){
            throw new RemoteException();
        }
    }

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

    @Override
    public void leaveGame(String nick, String gameId) throws RemoteException, NotBoundException{
        try{
            out.writeObject(new LeaveGameMessage(nick, gameId));
            completeForwarding();
        }catch(IOException e){
            throw new RemoteException();
        }
    }

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
            waitForNotification();  //aspetta quella di turn o quella di updateGameStations
        }catch(IOException | InterruptedException e){
            throw new RuntimeException(e);
        }

    }

    @Override
    public void goOn(){
        try{
            out.writeObject(new Pass());
            completeForwarding();
            //waitForNotification();
        }catch(IOException /*| InterruptedException*/ e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setColor(String color, String nick){
        try{
            out.writeObject(new Color(nick, color));
            completeForwarding();
            waitForNotification();
        }catch(IOException | InterruptedException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void drawPlayableCardFromTableOfDecks(String nick, String deck){
        try{
            out.writeObject(new DrawFromDeck(nick, deck));
            completeForwarding();
            waitForNotification();
        }catch(IOException | InterruptedException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void drawFromTable(String nick, Card card){
        try{
            out.writeObject(new DrawFromTable(nick, card));
            completeForwarding();
            waitForNotification();
        }catch(IOException | InterruptedException e){
            throw new RuntimeException(e);
        }
    }

    //forse va messo waitForNotification però è no usage
    @Override
    public void initializeHandPlayer(String nick){
        try{
            out.writeObject(new InitializeHand(nick));
            completeForwarding();
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    /**
     * PER SETTARE LA PRIMA CARTA
     * @param nick
     * @param card
     * @param isFront
     */
    @Override
    public void setGameStation(String nick, InitialCard card, boolean isFront){
        try{
            out.writeObject(new SetGameStation(nick, card, isFront));
            completeForwarding();
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    //questo metodo non ha senso
    @Override
    public String calculateWinner(){
        try{
            out.writeObject(new CalculateWinner());
            completeForwarding();
        }catch(IOException e){
            throw new RuntimeException(e);
        }

        return "ciao";
    }

    @Override //prova
    public void startGame() throws IOException {
        try{
            out.writeObject(new StartGame());
            completeForwarding();
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    //aggiunti per risolvere i problemi dei socket
    @Override
    public void initializeTurn(String nick) throws RemoteException{
        try{
            out.writeObject(new InitializeTurn(nick));
            completeForwarding();
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void definePlayer(String nick) throws RemoteException{
        try{
            out.writeObject(new DefinePlayer(nick));
            completeForwarding();
            waitForNotification();  //aspetta updateInitialCard
            waitForNotification(); //aspetta goalCardsDrawed
            waitForNotification(); //aspetta updateHandAndTable
        }catch(IOException | InterruptedException e){
            throw new RuntimeException(e);
        }
    }
}
