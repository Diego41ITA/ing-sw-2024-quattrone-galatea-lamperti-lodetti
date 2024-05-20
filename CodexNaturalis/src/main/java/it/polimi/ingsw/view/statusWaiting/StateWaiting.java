package it.polimi.ingsw.view.statusWaiting;

import it.polimi.ingsw.view.GameFlow;
import it.polimi.ingsw.view.input.InputParser;

public abstract class StateWaiting {

    public static GameFlow flow;
    public static InputParser inputGetter;
    protected StateWaiting(GameFlow flow, InputParser input){
        StateWaiting.flow = flow;
        StateWaiting.inputGetter = input;
    }

    public abstract void execute();

    public abstract void nextState();
}
