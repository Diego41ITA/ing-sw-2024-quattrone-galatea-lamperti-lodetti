package it.polimi.ingsw.networking.socket.client.message;

import it.polimi.ingsw.controller.GameControllerInterface;
import it.polimi.ingsw.controller.MainControllerInterface;
import it.polimi.ingsw.model.exceptions.GameEndedException;
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
    public void execute(GameControllerInterface game) throws RemoteException{
        game.setColor(this.color, this.nickname);
    }

    @Override
    public GameControllerInterface execute(GameObserver obs, MainControllerInterface operation) throws RemoteException {
        return null;
    }
}
