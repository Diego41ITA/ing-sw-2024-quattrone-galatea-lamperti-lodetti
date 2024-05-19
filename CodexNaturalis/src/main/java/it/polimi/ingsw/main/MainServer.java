package it.polimi.ingsw.main;

import it.polimi.ingsw.networking.rmi.ServerRMI;
import it.polimi.ingsw.networking.socket.server.ServerS;

/**
 * this class starts the server (both RMI and Socket)
 */
public class MainServer {
    public static void main(String[] args){
        try{
            ServerRMI.bind();

            ServerS socket = new ServerS();
            socket.startConnection(50000);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
