/*
package it.polimi.ingsw.networking.socket;

import it.polimi.ingsw.controller.FSM.*;
import it.polimi.ingsw.model.gameDataManager.Game;

import java.io.*;
import java.net.Socket;

public class SocketClientHandler implements Runnable {
    private Game game;
    private Socket clientSocket;
    private State currentState;

    public SocketClientHandler(Socket clientSocket, State currentState) {
        this.clientSocket = clientSocket ;
        this.currentState = currentState;
    }

    @Override
    public void run() {
        String input;
        String response;
        try(PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))){

            while (true) { //modificare true con condizione che accerta termine partita
                out.println(currentState.start());
                do {
                    input = in.readLine();
                    response = currentState.HandleInput(input);
                } while (!response.equals("nextState"));
                //metodo per annunciare agli altri client in partita della modifica del model?
               // currentState.NextState(); //metodo da aggiungere a State
            }
        }catch (Exception e){
            // gestire Exception
        }
    }
}

 */
