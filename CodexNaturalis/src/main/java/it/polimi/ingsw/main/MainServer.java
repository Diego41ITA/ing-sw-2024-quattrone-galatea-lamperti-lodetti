package it.polimi.ingsw.main;

import it.polimi.ingsw.networking.rmi.ServerRMI;
import it.polimi.ingsw.networking.socket.server.SocketServer;

import java.net.ServerSocket;

public class MainServer {
    public static void main(String[] args){
        try{
            ServerRMI.bind();

            SocketServer socket = new SocketServer();
            socket.startConnection(0 /* need to add the port number*/);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
