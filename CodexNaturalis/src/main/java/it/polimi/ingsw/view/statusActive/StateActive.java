package it.polimi.ingsw.view.statusActive;

import it.polimi.ingsw.view.GameFlow;

public abstract class StateActive {
    protected static GameFlow game;
    protected StateActive(GameFlow flow){
        StateActive.game = flow;
    }
    public abstract void execute();
    public abstract void nextState();
}
