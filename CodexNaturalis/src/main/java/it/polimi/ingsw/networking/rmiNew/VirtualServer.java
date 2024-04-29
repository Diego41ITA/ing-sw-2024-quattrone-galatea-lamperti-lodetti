package it.polimi.ingsw.networking.rmiNew;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualServer extends Remote {

    //metodi per modificare il game presenti in state

    public void connect(VirtualView client) throws RemoteException;

    public void start() throws RemoteException;

    public void handleInput(String input) throws RemoteException;

}
