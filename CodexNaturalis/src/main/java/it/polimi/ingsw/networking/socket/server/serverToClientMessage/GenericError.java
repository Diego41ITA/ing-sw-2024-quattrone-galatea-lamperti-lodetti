package it.polimi.ingsw.networking.socket.server.serverToClientMessage;

import it.polimi.ingsw.observer.GameObserver;

import java.io.IOException;

public class GenericError extends ServerNotification{
    private final String message;
    private final String gameId;
    public GenericError(String message, String gameId){
        this.message = message;
        this.gameId = gameId;
    }
    @Override
    public void execute(GameObserver obs) throws IOException, InterruptedException{
        obs.genericErrorWhenEnteringGame(message, gameId);
    }
}
