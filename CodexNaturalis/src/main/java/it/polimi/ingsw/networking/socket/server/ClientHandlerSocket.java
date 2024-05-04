package it.polimi.ingsw.networking.socket.server;

import it.polimi.ingsw.controller.GameControllerInterface;
import it.polimi.ingsw.networking.socket.client.message.Message;
import it.polimi.ingsw.view.GameObserverHandlerClient;
import it.polimi.ingsw.view.PrintlnThread;

import static it.polimi.ingsw.view.PrintlnThread.Println;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
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
        //devo lanciare un thread che processi i messaggi letti. in modo che questo thread sia sempre all'ascolto.
        while(true){
            try{
                Message incomingMessage = (Message) input.readObject();
                this.queue.add(incomingMessage);
            }catch(IOException | ClassNotFoundException e){
                Println("No comunication with the client");
            }
        }
    }

    public void manageRequests(){
        while(true){
            try{
                Message msg = this.queue.take();

                //bisogna verificare che non sia per il MainController (in questo caso andrebbero gestite più informazioni)
                msg.execute(GameController);

            }catch(InterruptedException e){
                Println("no action");

            }
        }
    }
}
