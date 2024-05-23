package it.polimi.ingsw.networking.socket.client.message;

import it.polimi.ingsw.controller.ControllerOfGameInterface;
import it.polimi.ingsw.controller.ControllerOfMatchesInterface;
import it.polimi.ingsw.model.card.PlayableCard;
import it.polimi.ingsw.model.exceptions.GameEndedException;
import it.polimi.ingsw.model.exceptions.illegalOperationException;
import it.polimi.ingsw.observer.GameObserver;

import java.awt.*;
import java.rmi.RemoteException;

public class PlayCard extends Message{
    private final PlayableCard playedCard;
    private final Point point;
    private final boolean isFront;
    public PlayCard(String nick, PlayableCard playedCard, Point point, boolean front){
        this.playedCard = playedCard;
        this.isFront = front;
        this.nickname = nick;
        this.point = point;
        this.forMainController = false;
    }

    @Override
    public void execute(ControllerOfGameInterface game) throws RemoteException, GameEndedException {
        //if first card, I maybe need to set the GameStation
        try {
            game.playCard(playedCard, nickname, isFront, point); //illegal operation exception needs to be transformed in
        } catch (illegalOperationException e) {
            throw new RuntimeException(e);
        }
        //RemoteException
    }

    @Override
    public ControllerOfGameInterface execute(GameObserver obs, ControllerOfMatchesInterface operation) throws RemoteException {
        return null;
    }
}
