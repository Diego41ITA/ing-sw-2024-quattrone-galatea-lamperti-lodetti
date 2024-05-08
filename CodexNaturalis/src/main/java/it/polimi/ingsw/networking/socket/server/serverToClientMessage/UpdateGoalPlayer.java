package it.polimi.ingsw.networking.socket.server.serverToClientMessage;

import it.polimi.ingsw.model.GameView;
import it.polimi.ingsw.observer.GameObserver;

import java.io.IOException;

public class UpdateGoalPlayer extends ServerNotification{
    private final GameView game;
    public UpdateGoalPlayer(GameView g){
        this.game = g;
    }

    @Override
    public void execute(GameObserver obs) throws IOException, InterruptedException{
        obs.updateGoalPlayer(game);
    }
}
