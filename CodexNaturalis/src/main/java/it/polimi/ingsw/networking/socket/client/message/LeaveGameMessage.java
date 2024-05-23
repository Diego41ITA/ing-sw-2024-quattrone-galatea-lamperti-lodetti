package it.polimi.ingsw.networking.socket.client.message;

import it.polimi.ingsw.controller.ControllerOfGameInterface;
import it.polimi.ingsw.controller.ControllerOfMatchesInterface;
import it.polimi.ingsw.observer.GameObserver;

import java.rmi.RemoteException;

public class LeaveGameMessage extends Message {
    private final String gameId;
    public LeaveGameMessage(String nick, String gameId){
        this.gameId = gameId;
        this.nickname = nick;
        this.forMainController = true;
    }

    @Override
    public void execute(ControllerOfGameInterface game) throws RemoteException {
        //... no operations need to be implemented
    }

    @Override
    public ControllerOfGameInterface execute(GameObserver obs, ControllerOfMatchesInterface operation) throws RemoteException {
        return operation.leaveGame(obs, this.nickname, this.gameId); //aggiungere il numero massimo di giocatori.
    }
}
