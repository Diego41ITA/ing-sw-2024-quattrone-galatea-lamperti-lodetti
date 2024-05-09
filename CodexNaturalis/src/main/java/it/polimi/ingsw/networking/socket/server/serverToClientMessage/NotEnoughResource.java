package it.polimi.ingsw.networking.socket.server.serverToClientMessage;

import it.polimi.ingsw.observer.GameObserver;

import java.io.IOException;

public class NotEnoughResource extends ServerNotification{
    @Override
    public void execute(GameObserver obs) throws IOException, InterruptedException{
        obs.notEnoughResource();
    }
}
