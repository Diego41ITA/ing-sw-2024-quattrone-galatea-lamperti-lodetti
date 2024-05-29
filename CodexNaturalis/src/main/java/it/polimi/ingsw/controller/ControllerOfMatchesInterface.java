package it.polimi.ingsw.controller;

import it.polimi.ingsw.observer.GameObserver;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * this interface exposes all the useful methods to create and  leave a game.
 */
public interface ControllerOfMatchesInterface extends Remote {


    /**
     * it creates a new game
     * @param obs it is the object that handles the notification for the client.
     * @param nick the client's nickname
     * @param maxNumPlayers the maximum number of player that can join this specific game.
     * @return returns an interface: GameControllerInterface.
     * @throws RemoteException
     */
    ControllerOfGameInterface createGame(GameObserver obs, String nick, int maxNumPlayers) throws RemoteException;

    /**
     * with this method the client can join a random game
     * @param obs it is the object that handles the notification for the client.
     * @param nick the client's nickname
     * @return returns an interface: GameControllerInterface.
     * @throws RemoteException
     */
    ControllerOfGameInterface joinRandomGame(GameObserver obs, String nick) throws RemoteException;

    /**
     * with this method the client can leave a game
     * @param nick the client's nickname
     * @param gameId the id of the game that the client wants to leave.
     * @throws RemoteException
     */
    void leaveGame(String gameId, String nick) throws RemoteException;

}
