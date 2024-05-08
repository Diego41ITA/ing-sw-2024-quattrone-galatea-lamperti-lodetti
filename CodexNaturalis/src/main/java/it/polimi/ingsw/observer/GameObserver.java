package it.polimi.ingsw.observer;
import it.polimi.ingsw.model.GameView;
import it.polimi.ingsw.model.gameDataManager.GameStation;

import java.rmi.Remote;
import java.rmi.RemoteException;

//questa interfaccia è usata per notificare al client qualcosa: server -> client
public interface GameObserver extends Remote{
    //lancia il messaggio in cui non ci siano abbastanza risorse per piazzare le carte
    void notEnoughResource()throws RemoteException;
    void updatePlayerAndMaxNumberPlayer(GameView game)throws RemoteException;
    void updateTableOfDecks(GameView game)throws RemoteException;
    void updateGamestation(GameView game, GameStation gameStation)throws RemoteException;
    void updatePlayerStatus(GameView game)throws RemoteException;

    void updateColor(GameView game)throws RemoteException;
    void updateTableAndTurn(GameView game)throws RemoteException;

    void updateCurrentPlayer(GameView game)throws RemoteException;
    void updatePoints(GameView game)throws RemoteException;
    void updateGoalPlayer(GameView game)throws RemoteException;
    void updateHandAndTable(GameView game, String nick)throws RemoteException;
    void updatePlayerInGame(GameView game)throws RemoteException;
    void updateGameStations(GameView game)throws RemoteException;
    void updateGameStatus(GameView game)throws  RemoteException;
    void newGameCreated(String GameID)throws RemoteException;
    void randomGameJoined(String GameID)throws RemoteException;
    void genericErrorWhenEnteringGame(String msg, String GameID) throws RemoteException;
    void gameIdNotExists(String gameId) throws RemoteException;

}

