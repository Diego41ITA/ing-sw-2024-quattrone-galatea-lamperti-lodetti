package it.polimi.ingsw.networking.socket.server.serverToClientMessage;

import it.polimi.ingsw.GameView.GameView;
import it.polimi.ingsw.observer.GameObserver;

import java.io.IOException;

public class PingMessage extends ServerNotification{
    private final GameView view;
    public PingMessage(GameView game) {
        super(false);
        this.view = game;
    }
    @Override
    public void execute(GameObserver obs) throws IOException{
        obs.pingTheClient(view);
    }
}
