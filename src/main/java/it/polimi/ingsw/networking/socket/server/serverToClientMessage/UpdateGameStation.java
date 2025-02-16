package it.polimi.ingsw.networking.socket.server.serverToClientMessage;

import it.polimi.ingsw.GameView.GameView;
import it.polimi.ingsw.model.gameDataManager.GameStation;
import it.polimi.ingsw.observer.GameObserver;

import java.io.IOException;

/**
 * questa viene creata da notify_playCard
 *
 */
public class UpdateGameStation extends ServerNotification{
    private final GameStation gs;
    private final GameView game;
    public UpdateGameStation(GameView g, GameStation gs){
        super(true);
        this.game = g;
        this.gs = gs;
    }

    @Override
    public void execute(GameObserver obs) throws IOException, InterruptedException{
        obs.updateGamestation(game,gs);
    }
}
