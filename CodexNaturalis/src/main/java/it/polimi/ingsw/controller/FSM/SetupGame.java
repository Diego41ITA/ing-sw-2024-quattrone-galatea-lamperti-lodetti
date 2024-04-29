package it.polimi.ingsw.controller.FSM;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.gameDataManager.Game;

public class SetupGame extends State {
    public SetupGame(GameController gameController){
        super(gameController);
    }
    @Override
    public String handleInput(Game game, String input) {
        return input;
    }

    @Override
    public String start(Game game) {
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
