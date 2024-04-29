package it.polimi.ingsw.networking.rmiNew;

import it.polimi.ingsw.controller.FSM.State;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

//
public class RmiClient extends UnicastRemoteObject implements VirtualView {
    final VirtualServer server;

    protected RmiClient(VirtualServer server) throws RemoteException {
        this.server = server;
    }

    private void run() throws RemoteException {
        this.server.connect(this);
        runCli();
    }

    private void runCli() throws RemoteException {
        Scanner scan = new Scanner(System.in);
        while (true) {
            server.start();
            //inserire possibili messaggi per ogni stato
            String command = scan.next();
            //in base al messaggio scrivere quali messaggi da invocare su RmiServer
        }
    }

    @Override
    public void notifica(State current) throws RemoteException {
        // da sistemare per possibili data race con il thread dell'interfaccia o un altro thread
        System.out.println(current.toString());
    }

    @Override
    public void reportError(String details) throws RemoteException {
        // da sistemare per possibili data race con il thread dell'interfaccia o un altro thread
        System.err.println("[ERROR] " + details);
    }

    public static void main(String[] args) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(args[0], 1234);
        VirtualServer server = (VirtualServer) registry.lookup("VirtualServer");

        new RmiClient(server).run();
    }
}