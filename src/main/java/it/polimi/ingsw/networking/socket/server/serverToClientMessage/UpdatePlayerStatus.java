package it.polimi.ingsw.networking.socket.server.serverToClientMessage;

import it.polimi.ingsw.GameView.GameView;
import it.polimi.ingsw.observer.GameObserver;

import java.io.IOException;

public class UpdatePlayerStatus extends ServerNotification{
    private final GameView game;
    public UpdatePlayerStatus(GameView g){
        super(false);
        this.game = g;
    }

    @Override
    public void execute(GameObserver obs) throws IOException, InterruptedException{
        obs.updatePlayerStatus(game);
    }
}
