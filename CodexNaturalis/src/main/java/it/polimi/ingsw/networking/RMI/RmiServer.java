package it.polimi.ingsw.networking.rmi;

import it.polimi.ingsw.controller.*;
import it.polimi.ingsw.controller.FSM.*;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

@SuppressWarnings("LanguageDetectionInspection")
public class RmiServer implements VirtualServer {
    final GameController controller;
    final List<VirtualView> clients = new ArrayList<>();

    public RmiServer(GameController controller) {
        this.controller = controller;
    }

    @Override
    public void connect(VirtualView client) throws RemoteException {
        System.err.println("new client connected");
        synchronized(clients){
            this.clients.add(client);
        }
    }

    @Override
    public String start() throws RemoteException {
//        System.err.println("start request");
        return this.controller.start();

        /*
        if (this.controller.start()) {
            State current = this.controller.getCurrentState();
            // soluzione migliore può fare update non bloccante dei clients(usare blockingQueue)
            synchronized (this.clients) {
                for (var c : this.clients) {
                    c.notifica(current.toString());
                }
            }
        } else {
            // soluzione migliore può fare update non bloccante dei clients(usare blockingQueue)
            synchronized (this.clients) {
                for (var c : this.clients) {
                    c.reportError("method failed");
                }
            }
        }

         */
    }

    @Override
    public int handleInput(String input) throws RemoteException {
        String current = this.controller.handleInput(input);
        if (!current.equals("nextState")) {
            // soluzione migliore può fare update non bloccante dei clients(usare blockingQueue)
            synchronized (this.clients) {
                for (var c : this.clients) {
                    c.notifica(current);
                }
            }
            return 0;
        } else {
            return 1;
        }
    }

    public void changeState() throws RemoteException{
        this.controller.changeState();
    }

    public static void main(String[] args) throws RemoteException {
        String name = "VirtualServer";

        VirtualServer engine = new RmiServer(new GameController());
        VirtualServer stub = (VirtualServer) UnicastRemoteObject.exportObject(engine, 0);
        Registry registry = LocateRegistry.createRegistry(1234);
        registry.rebind(name, stub);
        System.out.println("Server on");
    }
}