package it.polimi.ingsw.controller.FSM;

import it.polimi.ingsw.controller.GameController;

public class UntilFinish extends State {
    public UntilFinish(GameController gameController){
        super(gameController);
    }
    @Override
    public String handleInput() {
        return null;
    }

    @Override
    public void start() {

    }

    @Override
    public void goNextState() {

    }

    @Override
    public void goToSpecifiedState(State state) {

    }
}


