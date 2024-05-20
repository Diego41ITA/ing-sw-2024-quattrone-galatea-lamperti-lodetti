package it.polimi.ingsw.networking.socket.server.serverToClientMessage;

import it.polimi.ingsw.model.GameView;
import it.polimi.ingsw.observer.GameObserver;

import java.io.IOException;

public class Update20Points extends ServerNotification{

    GameView view;

    public Update20Points(GameView game){
        super(false);
        this.view = game;
    }

    @Override
    public void execute(GameObserver obs) throws IOException, InterruptedException {
        obs.update20PointsReached(view);
    }
}
