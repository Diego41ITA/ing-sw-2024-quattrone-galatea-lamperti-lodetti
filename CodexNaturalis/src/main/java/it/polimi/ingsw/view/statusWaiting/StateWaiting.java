package it.polimi.ingsw.view.statusWaiting;

import it.polimi.ingsw.view.GameFlow;
import it.polimi.ingsw.view.input.GetInput;

public abstract class StateWaiting {

    public static GameFlow flow;
    public static GetInput inputGetter;
    protected StateWaiting(GameFlow flow, GetInput input){
        StateWaiting.flow = flow;
        StateWaiting.inputGetter = input;
    }

    public abstract void execute();

    public abstract void nextState();
}
