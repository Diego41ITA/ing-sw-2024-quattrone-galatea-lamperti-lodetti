package it.polimi.ingsw.networking.socket.client.message;

import it.polimi.ingsw.controller.ControllerOfGameInterface;
import it.polimi.ingsw.controller.ControllerOfMatchesInterface;
import it.polimi.ingsw.observer.GameObserver;

import java.rmi.RemoteException;

public class InitializeHand extends Message{
    public InitializeHand(String nick){
        this.nickname = nick;
        this.forMainController = false;
    }
    @Override
    public void execute(ControllerOfGameInterface game) throws RemoteException {
        game.initializeHandPlayer(this.nickname);
    }

    @Override
    public ControllerOfGameInterface execute(GameObserver obs, ControllerOfMatchesInterface operation) throws RemoteException {
        return null;
    }
}
