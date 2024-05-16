package it.polimi.ingsw.networking.socket.server;

import it.polimi.ingsw.controller.GameControllerInterface;
import it.polimi.ingsw.controller.MainController;
import it.polimi.ingsw.model.exceptions.GameEndedException;
import it.polimi.ingsw.networking.socket.client.message.Message;
import it.polimi.ingsw.view.GameObserverHandlerClient;
import it.polimi.ingsw.view.PrintlnThread;

import static it.polimi.ingsw.view.PrintlnThread.Println;

import java.io.IOException;
import java.io.InterruptedIOException;
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
    private GameControllerInterface game;
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
        //devo lanciare un thread che processi i messaggi letti. In modo che questo thread sia sempre all'ascolto.
        Thread thread = new Thread(this::manageRequests);
        thread.start();

        try{
            Message incomingMessage;
            while(!this.isInterrupted()) {
                incomingMessage = (Message) input.readObject();
                this.queue.add(incomingMessage);
            }
        }catch(IOException | ClassNotFoundException e){
            Println("No communication with the client");
        }
    }

    public void manageRequests(){

        try{
            Message msg;

            while(!this.isInterrupted()) {

                msg = this.queue.take();

                //bisogna verificare che non sia per il MainController (in questo caso andrebbero gestite più informazioni)
                if (msg.isForMainController()) {
                    game = msg.execute(notify, MainController.getMainController());
                    if (game != null)
                        nick = msg.getNickname();
                } else {
                    msg.execute(game);
                }
            }
        }catch(InterruptedException e){
            Println("no action");

        } catch (GameEndedException | RemoteException e) {
            Println("client disconnected");
        }
    }
}
