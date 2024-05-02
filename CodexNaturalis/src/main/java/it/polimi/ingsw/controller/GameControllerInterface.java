package it.polimi.ingsw.controller;

import java.rmi.Remote;
import java.rmi.RemoteException;

//questa interfaccia contiene le azioni che un player può fare all'interno di una partita
public interface GameControllerInterface extends Remote {
    void drawCardFromGoldDeck(String nick, /*TIPO DEL MAZZO*/) throws RemoteException;
    boolean isMyTurn(String nick) throws RemoteException;

    //tutte le altre azioni; chiaramente bisogna verificare quali possono essere controllando il model.
}
