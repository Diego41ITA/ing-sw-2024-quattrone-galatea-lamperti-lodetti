package it.polimi.ingsw.observer;
import it.polimi.ingsw.GameView.GameView;
import it.polimi.ingsw.model.card.GoalCard;
import it.polimi.ingsw.model.card.InitialCard;
import it.polimi.ingsw.model.gameDataManager.Color;
import it.polimi.ingsw.model.gameDataManager.GameStation;
import it.polimi.ingsw.networking.socket.server.GameObserverHandlerSocket;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * exposes all {@link HandleObserver} methods,
 * these methods are exposed to be used on the client and server side to notify changes
 */
public interface GameObserver extends Remote{

    void invalidCardPlacement()throws RemoteException;
    void winner(GameView game, List<String> nick)throws RemoteException;
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
    void reconnectedToGame(GameView view)throws RemoteException;
    void goalCardsDrawed(ArrayList<GoalCard> cards)throws RemoteException;
    void startGame(GameView game)throws IOException;

    void genericErrorWhenEnteringGame(String msg, String gameID) throws RemoteException;
    void gameIdNotExists(String gameId) throws RemoteException;

    /**
     * this method pings the client constantly, and it passes it the GameView in this way it can refresh the view
     * faster and the game should be more fluid.
     * @param game the updated view of the model
     */
    void pingTheClient(GameView game) throws RemoteException;

    /**
     * this method tells the clients that the game is no longer available due to some player's disconnection during
     * the setup.
     * @throws RemoteException
     */
    void abortGame() throws RemoteException;
}

