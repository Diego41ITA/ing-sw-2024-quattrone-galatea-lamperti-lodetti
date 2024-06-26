package it.polimi.ingsw.networking.socket.server.serverToClientMessage;

import it.polimi.ingsw.GameView.GameView;
import it.polimi.ingsw.observer.GameObserver;

import java.io.IOException;
import java.util.List;

public class Winner extends ServerNotification{
    private GameView view;
    private List<String> winner;
    public Winner(GameView view, List<String> nick){
        super(false);
        winner = nick;
        this.view = view;
    }

    @Override
    public void execute(GameObserver obs) throws IOException, InterruptedException {
        obs.winner(view, winner);
    }
}
