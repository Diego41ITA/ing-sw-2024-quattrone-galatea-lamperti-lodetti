package it.polimi.ingsw.controller;

import it.polimi.ingsw.observer.GameObserver;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * this interface exposes all the useful methods to create, join or leave a game.
 */
public interface MainControllerInterface extends Remote {

    /**
     * it creates a new game
     * @param obs it is the object that handles the notification for the client.
     * @param nick the client's nickname
     * @param maxNumPlayers the maximum number of player that can join this specific game.
     * @return returns an interface: GameControllerInterface.
     * @throws RemoteException
     */
    GameControllerInterface createGame(GameObserver obs, String nick, int maxNumPlayers) throws RemoteException;

    /**
     * with this method the client can join a random game
     * @param obs it is the object that handles the notification for the client.
     * @param nick the client's nickname
     * @return returns an interface: GameControllerInterface.
     * @throws RemoteException
     */
    GameControllerInterface joinRandomGame(GameObserver obs, String nick) throws RemoteException;

    /**
     * with this method the client can rejoin a game
     * @param obs it is the object that handles the notification for the client.
     * @param nick the client's nickname
     * @param gameId It the id that the client wants to rejoin.
     * @return returns an interface: GameControllerInterface.
     * @throws RemoteException
     */
    GameControllerInterface rejoin(GameObserver obs, String nick, String gameId) throws RemoteException;

    /**
     * with this method the client can leave a game
     * @param obs it is the object that handles the notification for the client.
     * @param nick the client's nickname
     * @param gameId the id of the game that the client wants to leave.
     * @return returns an interface: GameControllerInterface.
     * @throws RemoteException
     */
    GameControllerInterface leaveGame(GameObserver obs, String nick, String gameId) throws RemoteException;
}
