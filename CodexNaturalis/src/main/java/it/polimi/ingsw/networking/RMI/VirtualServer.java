package it.polimi.ingsw.networking.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualServer extends Remote {

    //metodi per modificare il game presenti in state

    public void connect(VirtualView client) throws RemoteException;

    public String start() throws RemoteException;

    public int handleInput(String input) throws RemoteException;

    public void changeState()throws RemoteException;

}
