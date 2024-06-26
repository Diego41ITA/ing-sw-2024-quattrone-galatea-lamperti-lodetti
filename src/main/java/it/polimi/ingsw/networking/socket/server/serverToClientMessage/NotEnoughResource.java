package it.polimi.ingsw.networking.socket.server.serverToClientMessage;

import it.polimi.ingsw.observer.GameObserver;

import java.io.IOException;

public class NotEnoughResource extends ServerNotification{

    public NotEnoughResource(){
        super(true);
    }
    @Override
    public void execute(GameObserver obs) throws IOException, InterruptedException{
        obs.invalidCardPlacement();
    }
}
