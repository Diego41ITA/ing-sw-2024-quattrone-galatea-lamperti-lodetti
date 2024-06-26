package it.polimi.ingsw.view.statusActive;

import it.polimi.ingsw.GameView.GameView;
import it.polimi.ingsw.view.FsmGame;
import it.polimi.ingsw.view.input.*;

/**
 * this is the abstract class for the active state (draw and play a card). It defines 3 method: execute, nextState and
 * setView.
 * It implements a sort of State Pattern.
 */
public abstract class StateActive {
    protected static FsmGame flow;
    protected static InputParser inputGetter;

    protected StateActive(FsmGame flow, InputParser input){
        StateActive.flow = flow;
        StateActive.inputGetter = input;
    }

    /**
     * this method will execute the state
     */
    public abstract void execute();

    /**
     * this method will actually execute the next state
     */
    public abstract void nextState();

    /**
     * this method set a new game View
     * @param view the view that's needed to be set.
     */
    public abstract void setView(GameView view);
}
