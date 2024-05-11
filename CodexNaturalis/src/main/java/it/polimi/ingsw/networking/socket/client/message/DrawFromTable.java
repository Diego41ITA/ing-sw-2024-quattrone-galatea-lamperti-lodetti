package it.polimi.ingsw.networking.socket.client.message;

import it.polimi.ingsw.controller.GameControllerInterface;
import it.polimi.ingsw.controller.MainControllerInterface;
import it.polimi.ingsw.model.card.Card;
import it.polimi.ingsw.model.card.PlayableCard;
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
    public void execute(GameControllerInterface game) throws RemoteException, GameEndedException {
        game.drawFromTable(this.nCard, this.nickname);
    }

    @Override
    public GameControllerInterface execute(GameObserver obs, MainControllerInterface operation) throws RemoteException {
        return null;
    }
}
