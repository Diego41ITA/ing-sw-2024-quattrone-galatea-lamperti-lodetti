package it.polimi.ingsw.networking.socket.server.serverToClientMessage;

import it.polimi.ingsw.GameView.GameView;
import it.polimi.ingsw.observer.GameObserver;

import java.io.IOException;

public class Winner extends ServerNotification{
    private GameView view;
    private String winner;
    public Winner(GameView view, String nick){
        super(false);
        winner = nick;
        this.view = view;
    }

    @Override
    public void execute(GameObserver obs) throws IOException, InterruptedException {
        obs.winner(view, winner);
    }
}
