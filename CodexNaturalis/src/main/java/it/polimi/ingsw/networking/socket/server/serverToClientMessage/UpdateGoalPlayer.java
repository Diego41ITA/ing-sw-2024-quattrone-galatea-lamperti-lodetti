package it.polimi.ingsw.networking.socket.server.serverToClientMessage;

import it.polimi.ingsw.model.GameView;
import it.polimi.ingsw.model.card.GoalCard;
import it.polimi.ingsw.observer.GameObserver;

import java.io.IOException;

public class UpdateGoalPlayer extends ServerNotification{
    private final GameView game;
    private GoalCard card = null;

    public UpdateGoalPlayer(GameView g, GoalCard card){
        super(true);
        this.game = g;
        this.card = card;
    }

    @Override
    public void execute(GameObserver obs) throws IOException, InterruptedException{
        obs.updateGoalPlayer(game, card);
    }
}
