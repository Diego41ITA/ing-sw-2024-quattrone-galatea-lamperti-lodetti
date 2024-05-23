package it.polimi.ingsw.networking.socket.server.serverToClientMessage;

import it.polimi.ingsw.GameView.GameView;
import it.polimi.ingsw.model.gameDataManager.Color;
import it.polimi.ingsw.observer.GameObserver;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class UpdateNewColors extends ServerNotification {

    private final GameView game;
    private final ArrayList<Color> freeColors;

    public UpdateNewColors(GameView g, ArrayList<Color> colors){
        super(true);
        this.game = g;
        this.freeColors = colors;
    }

    @Override
    public void execute(GameObserver obs) throws RemoteException{
        obs.updateSetAvailableColors(game, freeColors);
    }
}
