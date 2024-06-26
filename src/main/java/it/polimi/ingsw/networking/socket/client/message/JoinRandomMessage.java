package it.polimi.ingsw.networking.socket.client.message;

import it.polimi.ingsw.controller.ControllerOfGameInterface;
import it.polimi.ingsw.controller.ControllerOfMatchesInterface;
import it.polimi.ingsw.observer.GameObserver;

import java.rmi.RemoteException;

public class JoinRandomMessage extends Message{
    public JoinRandomMessage(String nick){
        this.nickname = nick;
        this.forMainController = true;
    }

    @Override
    public void execute(ControllerOfGameInterface game) throws RemoteException {
        //nothing
    }

    @Override
    public ControllerOfGameInterface execute(GameObserver obs, ControllerOfMatchesInterface operation) throws RemoteException{
        return operation.joinRandomGame(obs, this.nickname);
    }

}
