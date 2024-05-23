package it.polimi.ingsw.networking.socket.client.message;

import it.polimi.ingsw.controller.ControllerOfGameInterface;
import it.polimi.ingsw.controller.ControllerOfMatchesInterface;
import it.polimi.ingsw.model.exceptions.GameEndedException;
import it.polimi.ingsw.observer.GameObserver;

import java.io.Serializable;
import java.rmi.RemoteException;

public abstract class Message implements Serializable {
    protected boolean forMainController;
    protected String nickname;
    public abstract void execute(ControllerOfGameInterface game) throws RemoteException, GameEndedException;
    public abstract ControllerOfGameInterface execute(GameObserver obs, ControllerOfMatchesInterface operation) throws RemoteException;

    public String getNickname(){
        return this.nickname;
    }

    public boolean isForMainController() {
        return forMainController;
    }
}
