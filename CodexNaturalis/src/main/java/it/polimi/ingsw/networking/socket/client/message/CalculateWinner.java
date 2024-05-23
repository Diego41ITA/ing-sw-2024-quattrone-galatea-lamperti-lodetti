package it.polimi.ingsw.networking.socket.client.message;

import it.polimi.ingsw.controller.ControllerOfGameInterface;
import it.polimi.ingsw.controller.ControllerOfMatchesInterface;
import it.polimi.ingsw.observer.GameObserver;

import java.rmi.RemoteException;

public class CalculateWinner extends Message{
    public CalculateWinner(){
        this.forMainController = false;
    }
    @Override
    public void execute(ControllerOfGameInterface game) throws RemoteException{
        game.calculateWinner();
    }

    @Override
    public ControllerOfGameInterface execute(GameObserver obs, ControllerOfMatchesInterface operation) throws RemoteException {
        return null;
    }
}
