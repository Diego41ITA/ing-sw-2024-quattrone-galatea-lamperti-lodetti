package it.polimi.ingsw.controller.FSM;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.gameDataManager.Game;

abstract public class State {
    static State SetupGame, PlayerTurn, CheckPoint, CalculateWinner,  UpdateGame;
    protected GameController gameController;
    public State (GameController gameController){
        this.gameController = gameController;
    }
    public State() {
    }
    public abstract String handleInput(Game game, String input);
    //now we should define all the methods.
    public abstract String start(Game game);
    public abstract void goNextState();
    public abstract void goToSpecifiedState(State state);

}
