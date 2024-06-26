package it.polimi.ingsw.networking.socket.client.message;

import it.polimi.ingsw.controller.ControllerOfGameInterface;
import it.polimi.ingsw.controller.ControllerOfMatchesInterface;
import it.polimi.ingsw.model.card.Card;
import it.polimi.ingsw.model.exceptions.GameEndedException;
import it.polimi.ingsw.observer.GameObserver;

import java.rmi.RemoteException;

public class DrawFromTable extends Message{
    private final Card nCard;
    public DrawFromTable(String nick, Card card){
        this.nickname = nick;
        this.forMainController = false;
        this.nCard = card;
    }
    @Override
    public void execute(ControllerOfGameInterface game) throws RemoteException, GameEndedException {
        game.drawFromTable(this.nCard, this.nickname);
    }

    @Override
    public ControllerOfGameInterface execute(GameObserver obs, ControllerOfMatchesInterface operation) throws RemoteException {
        return null;
    }
}
