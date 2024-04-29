package it.polimi.ingsw.controller.FSM;

import it.polimi.ingsw.controller.GameController;

public class SetupGame extends State {
    public SetupGame(GameController gameController){
        super(gameController);
    }
    @Override
    public String handleInput() {
        return input;
    }

    @Override
    public void start() {
        gameController.getGame().getTableOfDecks().initializeTable();
        return "The Table has been initialized";
    }

    @Override
    public void goNextState() {
        gameController.setCurrentState(new PlayerTurn(gameController));
    }

    @Override
    public void goToSpecifiedState(State state) {

    }
}
