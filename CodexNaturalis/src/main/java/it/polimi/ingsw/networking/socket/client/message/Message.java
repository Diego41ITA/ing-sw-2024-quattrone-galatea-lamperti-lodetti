package it.polimi.ingsw.networking.socket.client.message;

import it.polimi.ingsw.controller.GameControllerInterface;
import it.polimi.ingsw.controller.MainControllerInterface;
import it.polimi.ingsw.model.exceptions.GameEndedException;
import it.polimi.ingsw.observer.GameObserver;

import java.io.Serializable;
import java.rmi.RemoteException;

public abstract class Message implements Serializable {
    protected boolean forMainController;
    protected String nickname;
    public abstract void execute(GameControllerInterface game) throws RemoteException, GameEndedException;
    public abstract GameControllerInterface execute(GameObserver obs, MainControllerInterface operation) throws RemoteException;

    public String getNickname(){
        return this.nickname;
    }

    public boolean isForMainController() {
        return forMainController;
    }
}
