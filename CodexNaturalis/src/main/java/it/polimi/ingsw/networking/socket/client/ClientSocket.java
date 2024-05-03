package it.polimi.ingsw.networking.socket.client;

import it.polimi.ingsw.networking.ClientAction;
import it.polimi.ingsw.networking.socket.server.serverToClientMessage.ServerNotification;
import it.polimi.ingsw.view.GameFlow;
import it.polimi.ingsw.view.GameObserverHandlerClient;
import it.polimi.ingsw.view.PrintlnThread;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.io.*;

public class ClientSocket extends Thread implements ClientAction {
    private Socket client;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private GameObserverHandlerClient gameObserverHandler;
    private GameFlow flow;

    public ClientSocket(GameFlow flow){
        this.flow = flow;
        connect("server ip", "port socket");
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
                notification.execute(gameObserverHandler);
            }catch(IOException | ClassNotFoundException | InterruptedException e){
                PrintlnThread.Println("something went wrong :( ...");
                throw new RuntimeException(e);
            }
        }
    }

    public void stopConnection() throws IOException{
        in.close();
        out.close();
        client.close();
    }

    //tutte le operazioni, sono quelle presenti nel client RMI, con la differenza che ora scriveranno sull'out un
    // messaggio che riflette il tipo di operazione che si vuole eseguire.
}
