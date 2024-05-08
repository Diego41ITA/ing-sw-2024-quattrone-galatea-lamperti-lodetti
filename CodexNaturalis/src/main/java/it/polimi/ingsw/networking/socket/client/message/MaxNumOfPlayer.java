package it.polimi.ingsw.networking.socket.client.message;

import it.polimi.ingsw.controller.GameControllerInterface;
import it.polimi.ingsw.controller.MainControllerInterface;
import it.polimi.ingsw.model.exceptions.GameEndedException;
import it.polimi.ingsw.observer.GameObserver;

import java.rmi.RemoteException;

public class MaxNumOfPlayer extends Message{
    private final int max;
    public MaxNumOfPlayer(String nick, int max){
        this.nickname = nick;
        this.max = max;
        this.forMainController = false;
    }

    @Override
    public void execute(GameControllerInterface game) throws RemoteException, GameEndedException {
        game.setMaxNumberPlayers(this.nickname, this.max);
    }

    @Override
    public GameControllerInterface execute(GameObserver obs, MainControllerInterface operation) throws RemoteException {
        return null;
    }
}
