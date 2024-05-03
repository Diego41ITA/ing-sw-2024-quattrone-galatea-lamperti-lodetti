package it.polimi.ingsw.observer;

import it.polimi.ingsw.model.GameView;
import it.polimi.ingsw.model.gameDataManager.GameStation;

import java.rmi.RemoteException;
import java.util.*;

public class HandleObserver {
    private GameObserver observer;

    public HandleObserver(GameObserver obs) throws RemoteException {
        this.observer = obs;
    }
    public synchronized List<GameObserver> getObservers(){
        return observer;
    }

    public synchronized void  notify_setMaxNumberPlayers(Game game){
        observer.updatePlayerAndMaxNumberPlayer(new GameView(game));
    }

    public synchronized void  notify_notEnoughResource() throws RemoteException {
        observer.notEnoughResource();
    }

    public synchronized void notify_color(Game game){
        observer.updateColor(new Gameview(game));

    }
    //game mi serve per aggiornare l'oggetto immutabile Game, e gamestation serve alla UI
    public synchronized void notify_PlayCard(Game game, GameStation gamestation){
        observer.updateGamestation(new GameView(game), gamestation);
    }

    public synchronized void notify_DrawCard(Game game){
        observer.updateTableOfDecks(new GameView(game));

    }

    public synchronized void notify_ChangedPlayerStatus(Game game)){
        observer.updatePlayerStatus(new GameView(game));

    }

    public synchronized void notify_InitializeTable(Game game)){
        observer.updateTableAndTurn(new GameView(game));
    }

    public synchronized void notify_FinalsPoint(Game game){
        observer.updatePoints(new GameView(game));

    }

    public synchronized void notify_CurrentPlayerUpdated(Game game){
        observer.updateCurrentPlayer(new GameView(game));
    }

    public synchronized void notify_UpdatePoints(Game game){
            observer.updatePoints(new Gameview(game));
    }

    public synchronized void notify_chooseGoal(Game game){
        observer.updateGoalPlayer(new GameView(game));
    }


    public synchronized void notify_updatedHandAndTable(Game game){
        observer.updateHandAndTable(new GameView(game));
    }

    public synchronized void notify_AddedPlayer(Game game){
        observer.updatePlayerInGame(new GameView(game));
    }
}
