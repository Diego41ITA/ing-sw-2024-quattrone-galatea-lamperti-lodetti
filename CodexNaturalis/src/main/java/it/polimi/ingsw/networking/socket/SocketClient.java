package it.polimi.ingsw.networking.socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SocketClient {
    public static void main(String[] args) {
        try(Socket clientSocket = new Socket("localHost", 1234)){
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            Scanner message = new Scanner(System.in);

            while(true){ //cambiare condizione con conferma fine game
                System.out.println(in.readLine());
                String input = message.nextLine();
                out.println(input);
            }
        } catch (Exception e) {
            //gestione Exception
        }
    }
}
