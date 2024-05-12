package it.polimi.ingsw.networking.socket.client.message;

import it.polimi.ingsw.controller.GameControllerInterface;
import it.polimi.ingsw.controller.MainControllerInterface;
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
    public void execute(GameControllerInterface game) throws RemoteException, GameEndedException {
        //if first card, I maybe need to set the GameStation
        game.playCard(playedCard, nickname, isFront, point); //illegal operation exception needs to be transformed in
        //RemoteException
    }

    @Override
    public GameControllerInterface execute(GameObserver obs, MainControllerInterface operation) throws RemoteException {
        return null;
    }
}
