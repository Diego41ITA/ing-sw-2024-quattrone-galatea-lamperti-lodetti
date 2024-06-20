package it.polimi.ingsw.view;

import it.polimi.ingsw.GameView.GameView;
import it.polimi.ingsw.model.card.GoalCard;
import it.polimi.ingsw.model.card.InitialCard;
import it.polimi.ingsw.model.gameDataManager.Color;
import it.polimi.ingsw.model.gameDataManager.GameStation;
import it.polimi.ingsw.observer.GameObserver;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;

//implementa GameObserver e nel caso di RMI viene esportata in modo che quando si effettua una chiamata a metodi di
//GameObserver si comporta come un oggetto remoto => ha come effetto quello di chiamare metodi di flow di conseguenza
//notifica il flusso di gioco.

//funziona sia con socket che con Rmi
public class GameObserverHandlerClient implements GameObserver, Serializable {
    private final FsmGame flow;
    public GameObserverHandlerClient(FsmGame flow){
        this.flow = flow;
    }

    //Override di tutti gli altri messaggi che il server può mandare al client
    @Override
    public void invalidCardPlacement()throws RemoteException{
        flow.invalidCardPlacement();
    }

    @Override
    public void winner(GameView game, String nick) throws RemoteException {
        flow.winner(game, nick);
    }

    @Override
    public void updatePlayerAndMaxNumberPlayer(GameView game)throws RemoteException{
        flow.updatePlayerAndMaxNumberPlayer(game);
    }

    @Override
    public void update20PointsReached(GameView game) throws RemoteException {
        flow.update20PointsReached(game);
    }

    @Override
    public void updateTableOfDecks(GameView game)throws RemoteException{
        flow.updateTableOfDecks(game);
    }
    @Override
    public void updateGamestation(GameView game, GameStation gameStation)throws RemoteException{
        flow.updateGamestation(game, gameStation);
    }
    @Override
    public void updatePlayerStatus(GameView game)throws RemoteException{
        flow.updatePlayerStatus(game);
    }
    @Override
    public void updateColor(GameView game)throws RemoteException{
        flow.updateColor(game);
    }

    @Override
    public void updateSetAvailableColors(GameView game, ArrayList<Color> colors) throws RemoteException{
        flow.updateSetAvailableColors(game, colors);
    }
    @Override
    public void updateTableAndTurn(GameView game)throws RemoteException{
        flow.updateTableAndTurn(game);
    }
    @Override
    public void updateCurrentPlayer(GameView game)throws RemoteException{
        flow.updateCurrentPlayer(game);
    }
    @Override
    public void updatePoints(GameView game)throws RemoteException{
        flow.updatePoints(game);
    }
    @Override
    public void updateGoalPlayer(GameView game, GoalCard card)throws RemoteException{
        flow.updateGoalPlayer(game, card);
    }

    @Override
    public void updateInitialCardsDrawn(InitialCard card) throws RemoteException {
        flow.updateInitialCardsDrawn(card);
    }

    @Override
    public void updateHandAndTable(GameView game, String nick)throws RemoteException{
        flow.updateHandAndTable(game, nick);
    }
    @Override
    public void updatePlayerInGame(GameView game)throws RemoteException{
        flow.updatePlayerInGame(game);
    }
    @Override
    public void updateGameStations(GameView game)throws RemoteException{
        flow.updateGameStations(game);
    }
    @Override
    public void updateGameStatus(GameView game)throws  RemoteException{
        flow.updateGameStatus(game);
    }

    @Override
    public void newGameCreated(String gameID)throws RemoteException{
        flow.newGameCreated(gameID);
    }
    @Override
    public void randomGameJoined(String gameID)throws RemoteException{
        flow.randomGameJoined(gameID);
    }
    @Override
    public void reconnectedToGame(GameView game)throws RemoteException /*deve ritornare anche una GameView*/{
        flow.reconnectedToGame(game);
}
    @Override
    public void goalCardsDrawed(ArrayList<GoalCard> cards)throws RemoteException{
        flow.goalCardsDrawed(cards);
    }

    @Override
    public void startGame(GameView game)throws IOException {
        flow.startGame(game);
    }

    @Override
    public void genericErrorWhenEnteringGame(String msg, String gameID) throws RemoteException{
        flow.genericErrorWhenEnteringGame(msg, gameID);
    }
    @Override
    public void gameIdNotExists(String gameId) throws RemoteException{
        flow.gameIdNotExists(gameId);
    }

    @Override
    public void pingTheClient(GameView game)throws RemoteException{
        flow.setGameView(game);
    }

    @Override
    public void abortGame() throws RemoteException{
        flow.abortGame();
    }
}
