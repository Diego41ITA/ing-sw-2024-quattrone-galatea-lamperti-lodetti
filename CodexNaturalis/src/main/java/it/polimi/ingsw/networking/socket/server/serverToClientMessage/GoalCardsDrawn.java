package it.polimi.ingsw.networking.socket.server.serverToClientMessage;

import it.polimi.ingsw.model.card.GoalCard;
import it.polimi.ingsw.observer.GameObserver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GoalCardsDrawn extends ServerNotification{
    private final ArrayList<GoalCard> goals;
    public GoalCardsDrawn(ArrayList<GoalCard> goals){
        super(true);
        this.goals = goals;
    }

    @Override
    public void execute(GameObserver obs) throws IOException, InterruptedException{
        obs.goalCardsDrawed(goals);
    }

}
