package it.polimi.ingsw.networking.socket.server.serverToClientMessage;

import it.polimi.ingsw.observer.GameObserver;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.Serializable;

public abstract class ServerNotification implements Serializable {
    public abstract void execute(GameObserver obs) throws IOException, InterruptedException;
}
