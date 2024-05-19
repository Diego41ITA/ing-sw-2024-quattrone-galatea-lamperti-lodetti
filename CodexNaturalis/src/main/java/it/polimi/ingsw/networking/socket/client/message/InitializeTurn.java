package it.polimi.ingsw.networking.socket.client.message;

import it.polimi.ingsw.controller.GameControllerInterface;
import it.polimi.ingsw.controller.MainControllerInterface;
import it.polimi.ingsw.model.exceptions.GameEndedException;
import it.polimi.ingsw.observer.GameObserver;

import java.rmi.RemoteException;

public class InitializeTurn extends Message {
    public InitializeTurn(String nick){
        this.nickname = nick;
        this.forMainController = false;
    }

    @Override
    public void execute(GameControllerInterface game) throws RemoteException, GameEndedException {
        game.initializeTurn(nickname);
    }

    @Override
    public GameControllerInterface execute(GameObserver obs, MainControllerInterface operation) throws RemoteException {
        return null;
    }
}
