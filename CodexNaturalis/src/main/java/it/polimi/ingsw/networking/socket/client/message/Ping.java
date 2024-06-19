package it.polimi.ingsw.networking.socket.client.message;

import it.polimi.ingsw.controller.ControllerOfGameInterface;
import it.polimi.ingsw.controller.ControllerOfMatchesInterface;
import it.polimi.ingsw.observer.GameObserver;

import java.rmi.RemoteException;

public class Ping extends Message{
    private final String nick;

    public Ping(String nick){
        this.forMainController = false;
        this.nick = nick;
    }
    @Override
    public void execute(ControllerOfGameInterface game) throws RemoteException{
        game.ping(nick);
    }

    @Override
    public ControllerOfGameInterface execute(GameObserver obs, ControllerOfMatchesInterface operation) throws RemoteException {
        return null;
    }
}
