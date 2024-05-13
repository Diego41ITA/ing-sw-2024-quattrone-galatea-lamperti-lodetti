package it.polimi.ingsw.networking.socket.server.serverToClientMessage;

import it.polimi.ingsw.model.GameView;
import it.polimi.ingsw.model.card.GoalCard;
import it.polimi.ingsw.observer.GameObserver;

import java.io.IOException;

public class UpdateInitialCard extends ServerNotification{
    private final GameView game;

    public UpdateInitialCard(GameView g){
        this.game = g;
    }

    @Override
    public void execute(GameObserver obs) throws IOException, InterruptedException{
        obs.updateInitialCardsDrawn(game);
    }
}
