package it.polimi.ingsw.networking.socket.server.serverToClientMessage;

import it.polimi.ingsw.model.GameView;
import it.polimi.ingsw.model.card.GoalCard;
import it.polimi.ingsw.model.card.InitialCard;
import it.polimi.ingsw.observer.GameObserver;

import java.io.IOException;

public class UpdateInitialCard extends ServerNotification{
    private final InitialCard card;

    public UpdateInitialCard(InitialCard card){
        this.card = card;
    }

    @Override
    public void execute(GameObserver obs) throws IOException, InterruptedException{
        obs.updateInitialCardsDrawn(card);
    }
}
