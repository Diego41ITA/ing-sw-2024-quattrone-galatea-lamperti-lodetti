package it.polimi.ingsw.view.statusWaiting;

import it.polimi.ingsw.view.GameFlow;

public abstract class StateWaiting {

    public static GameFlow flow;
    protected StateWaiting(GameFlow flow){
        StateWaiting.flow = flow;
    }

    public abstract void execute();

    public abstract void nextState();
}
