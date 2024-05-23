package it.polimi.ingsw.view.statusActive;

import it.polimi.ingsw.GameView.GameView;
import it.polimi.ingsw.view.FsmGame;
import it.polimi.ingsw.view.input.*;

public abstract class StateActive {
    protected static FsmGame flow;
    protected static InputParser inputGetter;

    protected StateActive(FsmGame flow, InputParser input){
        StateActive.flow = flow;
        StateActive.inputGetter = input;
    }
    public abstract void execute();
    public abstract void nextState();
    public abstract void setView(GameView view);
}
