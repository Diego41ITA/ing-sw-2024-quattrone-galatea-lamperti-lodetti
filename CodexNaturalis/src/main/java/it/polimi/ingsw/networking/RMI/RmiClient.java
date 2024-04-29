package it.polimi.ingsw.networking.RMI;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

//
public class RmiClient extends UnicastRemoteObject implements VirtualView {
    final VirtualServer server;
    private Object lock = new Object();

    protected RmiClient(VirtualServer server) throws RemoteException {
        this.server = server;
    }

    private void run() throws RemoteException {
        this.server.connect(this);
        runCli();
    }

    private void runCli() throws RemoteException {
        Scanner scan = new Scanner(System.in);
        int flag=0;
        String command;
        while (true) {
            System.out.println(server.start());
            do{
                command = scan.next();
                flag = server.handleInput(command);
            }while(flag==0);
            server.changeState();//stato successivo
        }
    }

        @Override
        public void notifica (String current) throws RemoteException {
            synchronized(lock) {
                System.out.println(current);
            }
        }

        @Override
        public synchronized void reportError (String details) throws RemoteException {
            synchronized(lock) {
                System.err.println("[ERROR] " + details);
            }
        }

        public static void main (String[]args) throws RemoteException, NotBoundException {
            Registry registry = LocateRegistry.getRegistry(args[0], 1234);
            VirtualServer server = (VirtualServer) registry.lookup("VirtualServer");

            new RmiClient(server).run();
        }
    }