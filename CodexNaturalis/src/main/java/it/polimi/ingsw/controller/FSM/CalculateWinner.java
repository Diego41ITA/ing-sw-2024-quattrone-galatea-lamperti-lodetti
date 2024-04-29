package it.polimi.ingsw.controller.FSM;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.gameDataManager.Game;

public class CalculateWinner extends State {
    public CalculateWinner(GameController gameController){
        super(gameController);
    }
    @Override
    public String handleInput(Game game, String input) {
        return null;
    }

    @Override
    public String start(Game game) {
        return null;
    }

    @Override
    public void goNextState() {

    }

    @Override
    public void goToSpecifiedState(State state) {

    }
}


