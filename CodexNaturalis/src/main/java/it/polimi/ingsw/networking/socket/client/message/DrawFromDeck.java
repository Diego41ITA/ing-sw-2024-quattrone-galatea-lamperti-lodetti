package it.polimi.ingsw.networking.socket.client.message;

import it.polimi.ingsw.controller.GameControllerInterface;
import it.polimi.ingsw.controller.MainControllerInterface;
import it.polimi.ingsw.model.exceptions.GameEndedException;
import it.polimi.ingsw.observer.GameObserver;
import jdk.swing.interop.DragSourceContextWrapper;

import java.rmi.RemoteException;

public class DrawFromDeck extends Message{
    private final String deck;
    public DrawFromDeck(String nick, String deck){
        this.nickname = nick;
        this.forMainController = false;
        this.deck = deck;
    }
    @Override
    public void execute(GameControllerInterface game) throws RemoteException, GameEndedException {
        game.drawPlayableCardFromTableOfDecks(this.deck, this.nickname);
    }

    @Override
    public GameControllerInterface execute(GameObserver obs, MainControllerInterface operation) throws RemoteException {
        return null;
    }
}
