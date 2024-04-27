package it.polimi.ingsw.networking.socket;

import it.polimi.ingsw.controller.SetupGame;
import it.polimi.ingsw.controller.State;

import java.io.*;
import java.net.Socket;

public class SocketClient implements Runnable {
    private Socket clientSocket;
    private State currentState;

    public SocketClient(Socket clientSocket, State currentState) {
        this.clientSocket = clientSocket ;
        this.currentState = currentState;
    }

    @Override
    public void run() {
        try(BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))){
            String input;
            while ((input = in.readLine()) != null) {
                currentState.HandleInput(input);
            }
        }catch (Exception e){
            //Exception da gestire
        }
    }
}
