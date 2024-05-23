package it.polimi.ingsw.networking.socket.client.message;

import it.polimi.ingsw.controller.ControllerOfGameInterface;
import it.polimi.ingsw.controller.ControllerOfMatchesInterface;
import it.polimi.ingsw.model.exceptions.GameEndedException;
import it.polimi.ingsw.observer.GameObserver;
//import jdk.swing.interop.DragSourceContextWrapper;

import java.rmi.RemoteException;

public class DrawFromDeck extends Message{
    private final String deck;
    public DrawFromDeck(String nick, String deck){
        this.nickname = nick;
        this.forMainController = false;
        this.deck = deck;
    }
    @Override
    public void execute(ControllerOfGameInterface game) throws RemoteException, GameEndedException {
        game.drawPlayableCardFromTableOfDecks(this.deck, this.nickname);
    }

    @Override
    public ControllerOfGameInterface execute(GameObserver obs, ControllerOfMatchesInterface operation) throws RemoteException {
        return null;
    }
}
