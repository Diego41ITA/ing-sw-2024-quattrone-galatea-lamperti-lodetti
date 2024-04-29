package it.polimi.ingsw.networking.rmiNew;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualServer extends Remote {

    public void connect(VirtualView client) throws RemoteException;

    public void start() throws RemoteException;
    //metodi per modificare il game presenti in state
}
