package it.polimi.ingsw.networking.socket.server.serverToClientMessage;

import it.polimi.ingsw.observer.GameObserver;

import java.io.IOException;

public class ReconnectedToGame extends ServerNotification{
    private final String gameId;
    public ReconnectedToGame(String gameId){
        super(true);
        this.gameId = gameId;
    }
    @Override
    public void execute(GameObserver obs) throws IOException, InterruptedException{
        obs.reconnectedToGame(gameId);
    }
}
