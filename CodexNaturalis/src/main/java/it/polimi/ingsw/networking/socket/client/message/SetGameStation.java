package it.polimi.ingsw.networking.socket.client.message;

import it.polimi.ingsw.controller.GameControllerInterface;
import it.polimi.ingsw.controller.MainControllerInterface;
import it.polimi.ingsw.model.card.GoalCard;
import it.polimi.ingsw.model.card.InitialCard;
import it.polimi.ingsw.model.exceptions.GameEndedException;
import it.polimi.ingsw.observer.GameObserver;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

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
    public void execute(GameControllerInterface game) throws RemoteException, GameEndedException {
        game.setGameStation(this.nickname, this.card, this.isFront);
    }

    @Override
    public GameControllerInterface execute(GameObserver obs, MainControllerInterface operation) throws RemoteException {
        return null;
    }
}
