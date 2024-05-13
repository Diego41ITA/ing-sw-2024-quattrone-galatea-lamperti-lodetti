package it.polimi.ingsw.view.statusActive;

import it.polimi.ingsw.model.GameView;
import it.polimi.ingsw.view.GameFlow;

public abstract class StateActive {
    protected static GameFlow flow;

    protected StateActive(GameFlow flow){
        StateActive.flow = flow;
    }
    public abstract void execute();
    public abstract void nextState();
    public abstract void setView(GameView view);
}
