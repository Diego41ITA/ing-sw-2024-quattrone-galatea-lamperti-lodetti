package it.polimi.ingsw.view.statusWaiting;

import it.polimi.ingsw.view.FsmGame;
import it.polimi.ingsw.view.input.InputParser;

public class DoesNothing extends StateWaiting{

    public DoesNothing(FsmGame flow, InputParser input){
        super(flow, input);
    }

    @Override
    public void execute() {
        //this method does nothing
    }

    @Override
    public void nextState() {
        //it stays in this state
    }
}
