package it.polimi.ingsw.networking.socket.client.message;

import it.polimi.ingsw.controller.ControllerOfGameInterface;
import it.polimi.ingsw.controller.ControllerOfMatchesInterface;
import it.polimi.ingsw.model.exceptions.GameEndedException;
import it.polimi.ingsw.observer.GameObserver;

import java.rmi.RemoteException;

public class RejoinMessage extends Message {
    private final String gameId;

    public RejoinMessage(String nick, String gameId) {
        this.nickname = nick;
        this.gameId = gameId;
        this.forMainController = true;
    }

    @Override
    public void execute(ControllerOfGameInterface game) throws RemoteException, GameEndedException {
        //... no operations need to be implemented
    }

    @Override
    public ControllerOfGameInterface execute(GameObserver obs, ControllerOfMatchesInterface operation) throws RemoteException {
      return null;
    }
}
