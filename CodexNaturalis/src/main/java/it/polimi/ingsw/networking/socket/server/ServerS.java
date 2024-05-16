package it.polimi.ingsw.networking.socket.server;
import static it.polimi.ingsw.view.PrintlnThread.Println;

import java.util.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

/**
 * this class handles all the incoming requests from all the client, so it delegates another object (ClientHandlerSocket)
 * to manage the specific client. In order to manage all the incoming requests this class should extend Thread class.
 * @author Lodetti Alessandro
 */
public class ServerS extends Thread {
    private ServerSocket serverSocket;
    private List<ClientHandlerSocket> clients;

    public void startConnection(int port) throws IOException{
        try{
            serverSocket = new ServerSocket(port);
            clients = new ArrayList<>();
            this.start();
            Println("server  socket is active");
        }catch(IOException e){
            Println("something goes wrong the server is down :( ...");
        }
    }

    /**
     * the run() methods is always active, and it accepts all the incoming requests.
     */
    public void run(){

        try{
            while(!Thread.interrupted()) {
                ClientHandlerSocket newClient = new ClientHandlerSocket(serverSocket.accept());
                //now we need to start that specific connection so...
                clients.add(newClient);
                clients.getLast().start();
            }
        }catch(IOException e){
            Println("something goes wrong");
        }

        try{
            serverSocket.close();
        }catch(IOException e){
            throw new RuntimeException();
        }
    }

}
