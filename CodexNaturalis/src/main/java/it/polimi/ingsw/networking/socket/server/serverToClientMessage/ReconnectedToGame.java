package it.polimi.ingsw.networking.socket.server.serverToClientMessage;

import it.polimi.ingsw.GameView.GameView;
import it.polimi.ingsw.observer.GameObserver;

import java.io.IOException;

public class ReconnectedToGame extends ServerNotification{
    private final GameView view;
    public ReconnectedToGame(GameView game){
        super(true);
        this.view = game;
    }
    @Override
    public void execute(GameObserver obs) throws IOException, InterruptedException{
        obs.reconnectedToGame(view);
    }
}
