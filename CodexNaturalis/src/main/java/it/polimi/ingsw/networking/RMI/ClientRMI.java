package it.polimi.ingsw.networking.RMI;

import it.polimi.ingsw.model.gameDataManager.Game;
import java.util.Scanner;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class ClientRMI {
    public static void main(String[] args) {
        try {
            // Ottieni il riferimento al registry RMI
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);

            // Ottieni il riferimento all'interfaccia remota
            RMIInterface setupInterface = (RMIInterface) registry.lookup("1");//lookup va fatto sugli oggetti a cui abbiamo fatto bind

            // Simula l'oggetto State corrente
            State currentState = new StartingState(); // Supponiamo che StartingState sia una classe esistente

            // Simula l'oggetto Game
            Game game = new Game(); // Supponiamo che Game sia una classe esistente

            // Invia una richiesta al server tramite l'interfaccia remota
            //può essere un metodo nell implemetazione del server che inizilizza il game
            setupInterface.sendGameInformation(game, currentState);

            // Loop per ricevere e gestire le risposte dal server
            Scanner scanner = new Scanner(System.in);
            String input;
            while (true) {
                System.out.println("In attesa di risposta dal server...");
                input = scanner.nextLine();
                currentState.HandleInput(input);
            }
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}