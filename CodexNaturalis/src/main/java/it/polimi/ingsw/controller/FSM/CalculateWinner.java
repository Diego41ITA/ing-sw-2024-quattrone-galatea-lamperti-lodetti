package it.polimi.ingsw.controller.FSM;

import it.polimi.ingsw.controller.GameController;

public class CalculateWinner extends State {
    public CalculateWinner(GameController gameController){
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


