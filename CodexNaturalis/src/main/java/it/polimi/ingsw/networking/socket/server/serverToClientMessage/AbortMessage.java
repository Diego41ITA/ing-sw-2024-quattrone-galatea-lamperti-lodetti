package it.polimi.ingsw.networking.socket.server.serverToClientMessage;

import it.polimi.ingsw.observer.GameObserver;

import java.io.IOException;

public class AbortMessage extends ServerNotification{
    public AbortMessage(){
        super(false);
    }

    @Override
    public void execute(GameObserver obs) throws IOException{
        obs.abortGame();
    }
}
