package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.exceptions.NoAvailableGameToJoinException;
import it.polimi.ingsw.observer.GameObserver;

import java.rmi.Remote;
import java.rmi.RemoteException;

//questa interfaccia permetterà al server di invocare metodi del controller
public interface MainControllerInterface extends Remote {
    GameControllerInterface createGame(GameObserver obs, String nick, int maxNumPlayers) throws RemoteException;
    GameControllerInterface joinRandomGame(GameObserver obs, String nick) throws RemoteException, NoAvailableGameToJoinException;
    GameControllerInterface rejoin(GameObserver obs, String nick, String gameId) throws RemoteException;
    GameControllerInterface leaveGame(GameObserver obs, String nick, String gameId) throws RemoteException;
}
