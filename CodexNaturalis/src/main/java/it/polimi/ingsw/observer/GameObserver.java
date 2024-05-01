package it.polimi.ingsw.observer;
import it.polimi.ingsw.model.GameView;

import java.rmi.Remote;
import java.rmi.RemoteException;

//questa interfaccia è usata per notificare al client qualcosa: server -> client
public interface GameObserver extends Remote{

    void playerJoined(GameView model) throws RemoteException;
    void theGameIsFull(GameView model) throws RemoteException;
    void commonTableReady(GameView model) throws RemoteException;
    void nextTurn(GameView model) throws RemoteException;

    //ecc...
}

