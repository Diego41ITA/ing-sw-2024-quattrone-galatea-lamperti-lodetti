package it.polimi.ingsw.networking.socket.server.serverToClientMessage;

import it.polimi.ingsw.GameView.GameView;
import it.polimi.ingsw.observer.GameObserver;

import java.io.IOException;

//notifica inviata come conferma della carta iniziale posizionata.
public class UpdateGameStations extends ServerNotification{
    private final GameView game;
    public UpdateGameStations(GameView g){
        super(false);
        this.game = g;
    }

    @Override
    public void execute(GameObserver obs) throws IOException, InterruptedException{
        obs.updateGameStations(game);
    }
}
