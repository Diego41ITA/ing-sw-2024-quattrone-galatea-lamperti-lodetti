package it.polimi.ingsw.networking.rmiNew;

import it.polimi.ingsw.controller.FSM.*;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualView extends Remote {

    public void notifica(State current) throws RemoteException;

    public void reportError(String error) throws RemoteException;
    //metodi per notificare cambiamenti errori al client
}

