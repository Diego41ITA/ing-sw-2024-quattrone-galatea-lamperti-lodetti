package it.polimi.ingsw.networking.socket.client.message;

import it.polimi.ingsw.controller.ControllerOfGameInterface;
import it.polimi.ingsw.controller.ControllerOfMatchesInterface;
import it.polimi.ingsw.model.card.InitialCard;
import it.polimi.ingsw.model.exceptions.GameEndedException;
import it.polimi.ingsw.observer.GameObserver;

import java.rmi.RemoteException;

public class SetGameStation extends Message{
    private final InitialCard card;
    private final boolean isFront;
    public SetGameStation(String nick, InitialCard card, boolean isFront){
        this.nickname = nick;
        this.card = card;
        this.forMainController = false;
        this.isFront = isFront;
    }
    @Override
    public void execute(ControllerOfGameInterface game) throws RemoteException, GameEndedException {
        game.setGameStation(this.nickname, this.card, this.isFront);
    }

    @Override
    public ControllerOfGameInterface execute(GameObserver obs, ControllerOfMatchesInterface operation) throws RemoteException {
        return null;
    }
}
