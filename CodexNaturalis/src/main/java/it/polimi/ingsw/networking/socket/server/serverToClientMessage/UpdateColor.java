package it.polimi.ingsw.networking.socket.server.serverToClientMessage;

import it.polimi.ingsw.model.GameView;
import it.polimi.ingsw.observer.GameObserver;

import java.io.IOException;

public class UpdateColor extends ServerNotification{
    private final GameView game;
    public UpdateColor(GameView g){
        super(true);
        this.game = g;
    }

    @Override
    public void execute(GameObserver obs) throws IOException, InterruptedException{
        obs.updateColor(game);
    }
}
