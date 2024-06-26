package it.polimi.ingsw.networking.socket.client.message;

import it.polimi.ingsw.controller.ControllerOfGameInterface;
import it.polimi.ingsw.controller.ControllerOfMatchesInterface;
import it.polimi.ingsw.observer.GameObserver;

import java.rmi.RemoteException;

public class Color extends Message{
    private final String color;
    public Color(String nick, String color){
        this.forMainController = false;
        this.nickname = nick;
        this.color = color;
    }
    @Override
    public void execute(ControllerOfGameInterface game) throws RemoteException{
        game.setColor(this.color, this.nickname);
    }

    @Override
    public ControllerOfGameInterface execute(GameObserver obs, ControllerOfMatchesInterface operation) throws RemoteException {
        return null;
    }
}
