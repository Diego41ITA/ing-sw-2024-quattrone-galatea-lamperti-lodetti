package it.polimi.ingsw.networking.socket.server;

import it.polimi.ingsw.model.GameView;
import it.polimi.ingsw.model.card.GoalCard;
import it.polimi.ingsw.model.gameDataManager.Game;
import it.polimi.ingsw.model.gameDataManager.GameStation;
import it.polimi.ingsw.networking.socket.server.serverToClientMessage.*;
import it.polimi.ingsw.observer.GameObserver;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;


/*
questa classe serve a notificare al client tutti i cambiamenti
 */
public class GameObserverHandlerSocket implements GameObserver, Serializable {
    private final ObjectOutputStream out;
    public GameObserverHandlerSocket(ObjectOutputStream o){
        this.out = o;
    }

    public void completeForwarding() throws IOException{
        out.flush();
        out.reset();
    }

    //Bisogna fare l' override dei metodi di gameObserver, come per GameObserverHandlerClient bisognerà inviare un
    //messaggio questo verrà scritto sull' OutputStream
    @Override
    public void invalidCardPlacement()throws RemoteException{
        try{
            out.writeObject(new NotEnoughResource());
            completeForwarding();
        }catch(IOException e){
            //does something
        }
    }
    @Override
    public void updatePlayerAndMaxNumberPlayer(GameView game)throws RemoteException{
        try{
            out.writeObject(new UpdatePlayerAndMaxPlayer(game));
            completeForwarding();
        }catch(IOException e){
            //does something
        }
    }
    @Override
    public void updateTableOfDecks(GameView game)throws RemoteException{
        try{
            out.writeObject(new UpdateTableOfDecks(game));
            completeForwarding();
        }catch(IOException e){
            //does something
        }
    }
    @Override
    public void updateGamestation(GameView game, GameStation gameStation)throws RemoteException{
        try{
            out.writeObject(new UpdateGameStation(game, gameStation));
            completeForwarding();
        }catch(IOException e){
            //does something
        }
    }
    @Override
    public void updatePlayerStatus(GameView game)throws RemoteException{
        try{
            out.writeObject(new UpdatePlayerStatus(game));
            completeForwarding();
        }catch(IOException e){
            //does something
        }
    }

    @Override
    public void updateColor(GameView game)throws RemoteException{
        try{
            out.writeObject(new UpdateColor(game));
            completeForwarding();
        }catch(IOException e){
            //does something
        }
    }
    @Override
    public void updateTableAndTurn(GameView game)throws RemoteException{
        try{
            out.writeObject(new UpdateTableAndTurn(game));
            completeForwarding();
        }catch(IOException e){
            //does something
        }
    }

    @Override
    public void updateCurrentPlayer(GameView game)throws RemoteException{
        try{
            out.writeObject(new UpdateCurrentPlayer(game));
            completeForwarding();
        }catch(IOException e){
            //does something
        }
    }
    @Override
    public void updatePoints(GameView game)throws RemoteException{
        try{
            out.writeObject(new UpdatePoints(game));
            completeForwarding();
        }catch(IOException e){
            //does something
        }
    }
    @Override
    public void updateGoalPlayer(GameView game)throws RemoteException{
        try{
            out.writeObject(new UpdateGoalPlayer(game));
            completeForwarding();
        }catch(IOException e){
            //does something
        }
    }
    @Override
    public void updateHandAndTable(GameView game, String nick)throws RemoteException{
        try{
            out.writeObject(new UpdateHandAndTable(game, nick));
            completeForwarding();
        }catch(IOException e){
            //does something
        }
    }
    @Override
    public void updatePlayerInGame(GameView game)throws RemoteException{
        try{
            out.writeObject(new UpdatePlayerInGame(game));
            completeForwarding();
        }catch(IOException e){
            //does something
        }
    }
    @Override
    public void updateGameStations(GameView game)throws RemoteException{
        try{
            out.writeObject(new UpdateGameStations(game));
            completeForwarding();
        }catch(IOException e){
            //does something
        }
    }
    @Override
    public void updateGameStatus(GameView game)throws  RemoteException{
        try{
            out.writeObject(new UpdateGameStatus(game));
            completeForwarding();
        }catch(IOException e){
            //does something
        }
    }
    @Override
    public void newGameCreated(String gameID)throws RemoteException{
        try{
            out.writeObject(new NewGameCreated(gameID));
            completeForwarding();
        }catch(IOException e){
            //does something
        }
    }
    @Override
    public void randomGameJoined(String gameID)throws RemoteException{
        try{
            out.writeObject(new RandomGameJoined(gameID));
            completeForwarding();
        }catch(IOException e){
            //does something
        }
    }
    @Override
    public void reconnectedToGame(String gameID)throws RemoteException{
        try{
            out.writeObject(new ReconnectedToGame(gameID));
            completeForwarding();
        }catch(IOException e){
            //does something
        }
    }
    @Override
    public void goalCardsDrawed(ArrayList<GoalCard> cards)throws RemoteException{
        try{
            out.writeObject(new GoalCardsDrawn(cards));
            completeForwarding();
        }catch(IOException e){
            //does something
        }
    }

    @Override
    public void startGame(GameView game)throws RemoteException{
        try{
            out.writeObject(new StartGame(game));
            completeForwarding();
        }catch(IOException e){
            //does something
        }
    }

    @Override
    public void genericErrorWhenEnteringGame(String msg, String gameID) throws RemoteException{
        try{
            out.writeObject(new GenericError(msg, gameID));
            completeForwarding();
        }catch(IOException e){
            //does something
        }
    }
    @Override
    public void gameIdNotExists(String gameId) throws RemoteException{
        try{
            out.writeObject(new GameIdNotExists(gameId));
            completeForwarding();
        }catch(IOException e){
            //does something
        }
    }

    @Override
    public void winner(GameView view, String winner){
        //does nothing for now;
    }

    @Override
    public void update20PointsReached(GameView game)throws RemoteException{
        //does nothing for now;
    }
}
