package it.polimi.ingsw.networking.socket.client.message;

import it.polimi.ingsw.controller.GameControllerInterface;
import it.polimi.ingsw.controller.MainControllerInterface;
import it.polimi.ingsw.observer.GameObserver;

import java.rmi.RemoteException;

public class JoinRandom extends Message{
    public JoinRandom(String nick){
        this.nickname = nick;
        this.forMainController = true;
    }

    @Override
    public void execute(GameControllerInterface game) throws RemoteException {
        //nothing
    }

    @Override
    public GameControllerInterface execute(GameObserver obs, MainControllerInterface operation) throws RemoteException{
        return operation.joinRandomGame(obs, this.nickname);
    }

}
