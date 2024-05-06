package it.polimi.ingsw.view.statusWaiting;

import it.polimi.ingsw.model.GameView;
import it.polimi.ingsw.view.GameFlow;

public class StateWaiting {
    private GameFlow flow;
    private GameView view;

    public StateWaiting(GameView view, GameFlow flow) {
        this.flow = flow;
        this.view = view;
    }

    public execute(Command command){

    }
}
