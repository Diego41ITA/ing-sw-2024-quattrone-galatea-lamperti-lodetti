package it.polimi.ingsw.view.statusWaiting;

import it.polimi.ingsw.view.FsmGame;
import it.polimi.ingsw.view.input.InputParser;

/**
 * it's the abstract class for the set-up states: menu and color, it implements a sort of State Pattern, so it exposes
 * an execute method and a nextState method
 */
public abstract class StateWaiting {

    public static FsmGame flow;
    public static InputParser inputGetter;
    protected StateWaiting(FsmGame flow, InputParser input){
        StateWaiting.flow = flow;
        StateWaiting.inputGetter = input;
    }

    /**
     * it allows to execute the state
     */
    public abstract void execute();

    /**
     * it allows to go on a next state
     */
    public abstract void nextState();
}
