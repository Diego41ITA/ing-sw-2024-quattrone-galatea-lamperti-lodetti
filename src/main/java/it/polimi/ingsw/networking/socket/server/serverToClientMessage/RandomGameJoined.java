package it.polimi.ingsw.networking.socket.server.serverToClientMessage;

import it.polimi.ingsw.observer.GameObserver;

import java.io.IOException;

public class RandomGameJoined extends ServerNotification{
    private final String gameId;
    public RandomGameJoined(String gameId){
        super(true);
        this.gameId = gameId;
    }
    @Override
    public void execute(GameObserver obs) throws IOException, InterruptedException{
        obs.randomGameJoined(gameId);
    }
}
