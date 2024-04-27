package it.polimi.ingsw.networking.socket;

import it.polimi.ingsw.controller.SetupGame;
import it.polimi.ingsw.controller.State;
import it.polimi.ingsw.model.gameDataManager.Game;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class SocketClient implements Runnable {
    private Game game;
    private Socket clientSocket;
    private State currentState;

    public SocketClient(Socket clientSocket, State currentState) {
        this.clientSocket = clientSocket ;
        this.currentState = currentState;
    }

    @Override
    public void run() {
        String input;
        try(PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))){
            out.println("next move: ");
            input = in.readLine();
            currentState.HandleInput(input);
        }catch (Exception e){
            // gestire Exception
        }
    }

    public static void main(String[] args) {
        try(Socket clientSocket = new Socket("localHost", 1234)){
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            Scanner message = new Scanner(System.in);

            while(true){
                System.out.println(in.readLine());
                String input = message.nextLine();
                out.println(input);
            }
        } catch (Exception e) {
           //gestione Exception
        }
    }
}
