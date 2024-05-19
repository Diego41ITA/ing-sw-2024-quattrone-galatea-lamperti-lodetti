package it.polimi.ingsw.observer;
import it.polimi.ingsw.model.GameView;
import it.polimi.ingsw.model.card.GoalCard;
import it.polimi.ingsw.model.card.InitialCard;
import it.polimi.ingsw.model.gameDataManager.Color;
import it.polimi.ingsw.model.gameDataManager.GameStation;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

//questa interfaccia è usata per notificare al client qualcosa: server -> client
public interface GameObserver extends Remote{
    //lancia il messaggio in cui non ci siano abbastanza risorse per piazzare le carte
    void invalidCardPlacement()throws RemoteException;
    void winner(GameView game, String nick)throws RemoteException;
    void updatePlayerAndMaxNumberPlayer(GameView game)throws RemoteException;
    void update20PointsReached(GameView game)throws RemoteException;
    void updateTableOfDecks(GameView game)throws RemoteException;
    void updateGamestation(GameView game, GameStation gameStation)throws RemoteException;
    void updatePlayerStatus(GameView game)throws RemoteException;

    void updateColor(GameView game)throws RemoteException;
    void updateSetAvailableColors(GameView game, ArrayList<Color> newAvailableColor) throws RemoteException;
    void updateTableAndTurn(GameView game)throws RemoteException;

    void updateCurrentPlayer(GameView game)throws RemoteException;
    void updatePoints(GameView game)throws RemoteException;
    void updateGoalPlayer(GameView game, GoalCard card)throws RemoteException;
    void updateInitialCardsDrawn(InitialCard card)throws RemoteException;
    void updateHandAndTable(GameView game, String nick)throws RemoteException;
    void updatePlayerInGame(GameView game)throws RemoteException;
    void updateGameStations(GameView game)throws RemoteException;
    void updateGameStatus(GameView game)throws  RemoteException;
    void newGameCreated(String gameID)throws RemoteException;
    void randomGameJoined(String gameID)throws RemoteException;
    void reconnectedToGame(String gameID)throws RemoteException; //deve ritornare anche una GameView
    void goalCardsDrawed(ArrayList<GoalCard> cards)throws RemoteException;
    void startGame(GameView game)throws IOException;

    void genericErrorWhenEnteringGame(String msg, String gameID) throws RemoteException;
    void gameIdNotExists(String gameId) throws RemoteException;

}

