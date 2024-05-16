package it.polimi.ingsw.networking.socket.server.serverToClientMessage;

import it.polimi.ingsw.observer.GameObserver;

import java.io.IOException;
import java.io.InterruptedIOException;

public class GameIdNotExists extends ServerNotification{
    private final String gameId;
    public GameIdNotExists(String gameId){
        super(true);
        this.gameId = gameId;
    }

    @Override
    public void execute(GameObserver obs) throws IOException, InterruptedException{
        obs.gameIdNotExists(gameId);
    }
}
