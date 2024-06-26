package it.polimi.ingsw.networking.socket.server.serverToClientMessage;

import it.polimi.ingsw.GameView.GameView;
import it.polimi.ingsw.observer.GameObserver;

import java.io.IOException;

public class UpdatePlayerAndMaxPlayer extends ServerNotification{
    private final GameView game;
    public UpdatePlayerAndMaxPlayer(GameView g){
        super(false);       //viene messo in create game il max number of player.
        this.game = g;
    }

    @Override
    public void execute(GameObserver obs) throws IOException, InterruptedException{
        obs.updatePlayerAndMaxNumberPlayer(game);
    }
}
