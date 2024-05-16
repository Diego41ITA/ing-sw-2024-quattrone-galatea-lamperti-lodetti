package it.polimi.ingsw.networking.socket.server.serverToClientMessage;

import it.polimi.ingsw.model.GameView;
import it.polimi.ingsw.observer.GameObserver;

import java.io.IOException;

public class UpdateHandAndTable extends ServerNotification{
    private final GameView game;
    private final String nick;
    public UpdateHandAndTable(GameView g, String nick){
        super(false);
        this.game = g;
        this.nick = nick;
    }

    @Override
    public void execute(GameObserver obs) throws IOException, InterruptedException{
        obs.updateHandAndTable(game, nick);
    }
}
