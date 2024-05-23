package it.polimi.ingsw.networking.socket.client.message;

import it.polimi.ingsw.controller.ControllerOfGameInterface;
import it.polimi.ingsw.controller.ControllerOfMatchesInterface;
import it.polimi.ingsw.model.exceptions.GameEndedException;
import it.polimi.ingsw.observer.GameObserver;

import java.rmi.RemoteException;

public class InitializeTurn extends Message {
    public InitializeTurn(String nick){
        this.nickname = nick;
        this.forMainController = false;
    }

    @Override
    public void execute(ControllerOfGameInterface game) throws RemoteException, GameEndedException {
        game.initializeTurn(nickname);
    }

    @Override
    public ControllerOfGameInterface execute(GameObserver obs, ControllerOfMatchesInterface operation) throws RemoteException {
        return null;
    }
}
