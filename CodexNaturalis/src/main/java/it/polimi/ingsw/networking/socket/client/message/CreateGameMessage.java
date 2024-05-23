package it.polimi.ingsw.networking.socket.client.message;

import it.polimi.ingsw.controller.ControllerOfGameInterface;
import it.polimi.ingsw.controller.ControllerOfMatchesInterface;
import it.polimi.ingsw.model.exceptions.GameEndedException;
import it.polimi.ingsw.observer.GameObserver;

import java.rmi.RemoteException;

public class CreateGameMessage extends Message{
    private final int numPlayer;
    public CreateGameMessage(String nick, int numPlayer){
        super.nickname = nick;
        this.forMainController = true;
        this.numPlayer = numPlayer;
    }

    @Override
    public void execute(ControllerOfGameInterface game) throws RemoteException, GameEndedException {
        //... no operations need to be implemented
    }

    @Override
    public ControllerOfGameInterface execute(GameObserver obs, ControllerOfMatchesInterface operation) throws RemoteException {
        return operation.createGame(obs, this.nickname, this.numPlayer); //aggiungere il numero massimo di giocatori.
    }
}
