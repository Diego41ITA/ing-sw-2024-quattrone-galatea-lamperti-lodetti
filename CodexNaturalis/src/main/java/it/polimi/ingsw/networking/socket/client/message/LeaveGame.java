package it.polimi.ingsw.networking.socket.client.message;

import it.polimi.ingsw.controller.GameControllerInterface;
import it.polimi.ingsw.controller.MainControllerInterface;
import it.polimi.ingsw.model.exceptions.GameEndedException;
import it.polimi.ingsw.observer.GameObserver;

import java.rmi.RemoteException;

public class LeaveGame extends Message {
    private final String gameId;
    public LeaveGame(String nick, String gameId){
        this.gameId = gameId;
        this.nickname = nick;
        this.forMainController = true;
    }

    @Override
    public void execute(GameControllerInterface game) throws RemoteException {
        //... no operations need to be implemented
    }

    @Override
    public GameControllerInterface execute(GameObserver obs, MainControllerInterface operation) throws RemoteException {
        return operation.leaveGame(obs, this.nickname, this.gameId); //aggiungere il numero massimo di giocatori.
    }
}
