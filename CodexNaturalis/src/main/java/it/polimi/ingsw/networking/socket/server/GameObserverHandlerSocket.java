package it.polimi.ingsw.networking.socket.server;

import it.polimi.ingsw.GameView.GameView;
import it.polimi.ingsw.model.card.GoalCard;
import it.polimi.ingsw.model.card.InitialCard;
import it.polimi.ingsw.model.gameDataManager.Color;
import it.polimi.ingsw.model.gameDataManager.GameStation;
import it.polimi.ingsw.networking.socket.server.serverToClientMessage.*;
import it.polimi.ingsw.observer.GameObserver;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;


/**
 * Notifies to the client all the update in the game. It writes the on the outputStream the
 * message (it extends ServerNotification)
 */
public class GameObserverHandlerSocket implements GameObserver, Serializable {
    /**The server's stream output */
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
    public void updateSetAvailableColors(GameView game, ArrayList<Color> colors) throws RemoteException{
        try{
            out.writeObject(new UpdateNewColors(game, colors)); //va cambiato in UpdateNewColors
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
    public void updateGoalPlayer(GameView game, GoalCard card)throws RemoteException{
        try{
            out.writeObject(new UpdateGoalPlayer(game, card));
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
            e.printStackTrace();
            e.getCause();
            e.getMessage();
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
    public void updateInitialCardsDrawn(InitialCard card) throws RemoteException{
        try{
            out.writeObject(new UpdateInitialCard(card));
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
        try{
            out.writeObject(new Winner(view, winner));
        }catch(IOException e){
            //does something
        }
    }

    @Override
    public void update20PointsReached(GameView game)throws RemoteException{
        try{
            out.writeObject(new Update20Points(game));
        }catch(IOException e){
            //
        }
    }
}
