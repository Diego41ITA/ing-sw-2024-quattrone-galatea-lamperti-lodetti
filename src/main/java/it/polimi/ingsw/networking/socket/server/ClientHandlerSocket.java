package it.polimi.ingsw.networking.socket.server;

import it.polimi.ingsw.controller.ControllerOfGameInterface;
import it.polimi.ingsw.controller.ControllerOfMatches;
import it.polimi.ingsw.model.exceptions.GameEndedException;
import it.polimi.ingsw.networking.socket.client.message.Message;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * this class is associated to the server class, and it handles all the incoming requests (from a specific client)
 * that ara going to be performed on the controller.
 */
public class ClientHandlerSocket extends Thread{
    private String nick;
    private ObjectOutputStream out;
    private ObjectInputStream input;
    private Socket client;
    private LinkedBlockingQueue<Message> queue;

    //similar to Rmi we need to pass the object where the operation are available.
    private ControllerOfGameInterface game;
    private GameObserverHandlerSocket notify;

    public ClientHandlerSocket(Socket socket) throws IOException{
        this.client = socket;
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.input = new ObjectInputStream(socket.getInputStream());
        this.notify = new GameObserverHandlerSocket(out);
        this.queue = new LinkedBlockingQueue<Message>();
    }

    /**
     * this thread read all the incoming requests and add them to the queue
     */
    @Override
    public void run(){
        //A new thread that processes the received message is started
        Thread thread = new Thread(this::manageRequests);
        thread.start();

        try{
            Message incomingMessage;
            while(!this.isInterrupted()) {
                incomingMessage = (Message) input.readObject();
                this.queue.add(incomingMessage);
            }
        }catch(IOException | ClassNotFoundException e){
            System.out.println("No communication with the client");
            this.interrupt();
        }
    }

    /**
     * It reads messages from the blocking queue and it executes them.
     * This method avoids deadlock and allows to process all the possible messages without getting stuck.
     * It also detects disconnections, game termination and interruption.
     */
    public void manageRequests(){

        try{
            Message msg;

            while(!this.isInterrupted()) {

                msg = this.queue.take();

                if (msg.isForMainController()) {
                    game = msg.execute(notify, ControllerOfMatches.getMainController());
                    if (game != null)
                        nick = msg.getNickname();
                } else {
                    msg.execute(game);
                }
            }
        }catch(InterruptedException e){
            System.out.println("no action");

        } catch (GameEndedException | RemoteException e) {
            System.out.println("client disconnected catch in clientHandlerSocket: " + nick);
        }
    }
}
