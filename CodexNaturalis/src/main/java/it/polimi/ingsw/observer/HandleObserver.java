package it.polimi.ingsw.observer;

import it.polimi.ingsw.GameView.GameView;
import it.polimi.ingsw.model.card.GoalCard;
import it.polimi.ingsw.model.card.InitialCard;
import it.polimi.ingsw.model.gameDataManager.*;
import it.polimi.ingsw.networking.socket.server.GameObserverHandlerSocket;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Contains all the notifications that can be thrown and calls the correspondent
 * {@link GameObserverHandlerSocket} for the sockets.
 */
public class HandleObserver implements Serializable {
    private final GameObserver observer;

    public HandleObserver(GameObserver obs){
        this.observer = obs;
    }

    public synchronized GameObserver getObservers(){
        return observer;
    }

    public void notify_updateGameStations(Game game){
        try {
            observer.updateGameStations(new GameView(game));
        }catch(RemoteException e){
            //
        }
    }

    public void notify_changedGameStatus(Game game){
        try {
            observer.updateGameStatus(new GameView(game));
        }catch(RemoteException e){
            //
        }
    }

    public void  notify_setMaxNumberPlayers(Game game){
        try {
            observer.updatePlayerAndMaxNumberPlayer(new GameView(game));
        }catch(RemoteException e){
            //
        }
    }

    public void notify_invalidCardPlacement(){
        try {
            observer.invalidCardPlacement();
        }catch(RemoteException e){
            //
        }
    }

    public void notify_color(Game game) {
        try {
            observer.updateColor(new GameView(game));
        }catch(RemoteException e){
            //
        }
    }

    public void notify_colorTaken(Game game, ArrayList<Color> availableColors){
        try{
            observer.updateSetAvailableColors(new GameView(game), availableColors);
        }catch (RemoteException e){
            //
        }
    }

    //game mi serve per aggiornare l'oggetto immutabile Game, e gamestation serve alla UI
    public void notify_PlayCard(Game game, GameStation gamestation){
        try {
            observer.updateGamestation(new GameView(game), gamestation);
        }catch (RemoteException e){
            //
        }
    }

    public void notify_DrawCard(Game game){
        try {
            observer.updateTableOfDecks(new GameView(game));
        }catch(RemoteException e){
        //
        }
    }

    public void notify_ChangedPlayerStatus(Game game){
        try {
            observer.updatePlayerStatus(new GameView(game));
        }catch(RemoteException e){
        //
        }
    }

    public void notify_20PointsReached(Game game){
        try {
            observer.update20PointsReached(new GameView(game));
        }catch(RemoteException e){
            //
        }
    }

    public void notify_InitializeTable(Game game){
        try {
            observer.updateTableAndTurn(new GameView(game));
        }catch(RemoteException e){
        //
        }
    }

    public void notify_FinalsPoint(Game game){
        try {
            observer.updatePoints(new GameView(game));
        }catch(RemoteException e){
        //
        }
    }

    public void notify_CurrentPlayerUpdated(Game game){
        try {
            observer.updateCurrentPlayer(new GameView(game));
        }catch(RemoteException e){
        //
        }
    }

    public void notify_UpdatePoints(Game game){
        try {
            observer.updatePoints(new GameView(game));
        }catch(RemoteException e){
            //
        }
    }

    public void notify_winner(Game game, String nick){
        try {
            observer.winner(new GameView(game), nick);
        }catch(RemoteException e){
            //
        }
    }

    public void notify_chooseGoal(Game game, GoalCard card){
        try {
            observer.updateGoalPlayer(new GameView(game), card);
        }catch(RemoteException e){
            //
        }
    }

    public void notify_initialCardsDrawn(InitialCard card){
        try {
            observer.updateInitialCardsDrawn(card);
        }catch(RemoteException e){
            //
        }
    }

    public void notify_updatedHandAndTable(Game game, String nick){
        try {
            observer.updateHandAndTable(new GameView(game), nick);
        }catch(RemoteException e){
            //
        }
    }

    public void notify_startGame(Game game){
        try {
            observer.startGame(new GameView(game));
        }catch(IOException e){
            //
        }
    }

    public void notify_goalCardsDrawed(ArrayList<GoalCard> cards){
        try {
            observer.goalCardsDrawed(cards);
        }catch(RemoteException e){
            //
        }
    }

    public void notify_AddedPlayer(Game game){
        try {
            observer.updatePlayerInGame(new GameView(game));
        }catch(RemoteException e){
            //
        }
    }

    /**
     * this method tries to ping the client
     * @param game it passes the updated view
     */
    public void notify_PingClient(Game game) throws IOException{
        try{
            observer.pingTheClient(new GameView(game));
        }catch(RemoteException e){
            System.out.println("the client: " + this.observer + " disconnected ");
            throw new IOException();
        }
    }
}
