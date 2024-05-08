package it.polimi.ingsw.networking.socket.client.message;

import it.polimi.ingsw.controller.GameControllerInterface;
import it.polimi.ingsw.controller.MainControllerInterface;
import it.polimi.ingsw.model.exceptions.GameEndedException;
import it.polimi.ingsw.observer.GameObserver;

import java.rmi.RemoteException;

public class Rejoin extends Message{
    private final String gameId;
    public Rejoin(String nick, String gameId){
        this.nickname = nick;
        this.gameId = gameId;
        this.forMainController = true;
    }

    @Override
    public void execute(GameControllerInterface game) throws RemoteException, GameEndedException {
        //... no operations need to be implemented
    }

    @Override
    public GameControllerInterface execute(GameObserver obs, MainControllerInterface operation) throws RemoteException {
        return operation.rejoin(obs, this.nickname, this.gameId); //aggiungere il numero massimo di giocatori.
    }
}
