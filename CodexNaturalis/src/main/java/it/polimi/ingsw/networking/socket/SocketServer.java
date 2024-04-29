/*
package it.polimi.ingsw.networking.socket;

import it.polimi.ingsw.controller.FSM.StartingState;
import it.polimi.ingsw.model.gameDataManager.Game;

import java.net.*;

//manca gestione cambio state
public class SocketServer {
    private Game game;

    public void start(int port){
        try (ServerSocket serverSocket = new ServerSocket(port)){

            System.out.println("Server started");
            while(true){
                Socket client = serverSocket.accept();
                System.out.println("Client connected");
                new Thread(new SocketClientHandler(client, new StartingState())).start();
            }
        }catch (Exception e){
            //Exception da gestire
        }
    }

    public static void main(String[] args) {
        SocketServer server = new SocketServer();
        server.start(1234);
    }
}

 */

