package it.polimi.ingsw.networking.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualView extends Remote {

    public void notifica(String message) throws RemoteException;

    public void reportError(String error) throws RemoteException;
    //metodi per notificare cambiamenti errori al client
}

