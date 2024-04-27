package it.polimi.ingsw.networking.RMI;
import it.polimi.ingsw.model.gameDataManager.Game;
import it.polimi.ingsw.networking.socket.SocketClient;
import java.net.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ServerRMI{
    public void start(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started");
            while (true) {
                Socket client = serverSocket.accept();
                System.out.println("Client connected");
                // Qui puoi eseguire la logica per gestire il client, come fatto nel SocketServer(parallelismo)
                new Thread(new SocketClient(client, new StartingState())).start();
            }
        } catch (Exception e) {
            // Gestione delle eccezioni
            e.printStackTrace();
        }
    }

    public void startRmi() {
        try {
            // Creazione dell'oggetto remoto
            ImplementationServer setupImpl = new ImplementationServer();

            // Esportazione dell'oggetto remoto
            RMIInterface stub = (RMIInterface) UnicastRemoteObject.exportObject(setupImpl, 0);

            // Ottieni il registry RMI
            Registry registry = LocateRegistry.createRegistry(1099);

            // Pubblica lo stub nel registry RMI
            registry.rebind("1", stub);

            System.out.println("Server RMI pronto");
        } catch (Exception e) {
            System.err.println("Server RMI eccezione: " + e.toString());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ServerRMI server = new ServerRMI();
        server.startRmi();
        server.start(1099);
    }
}