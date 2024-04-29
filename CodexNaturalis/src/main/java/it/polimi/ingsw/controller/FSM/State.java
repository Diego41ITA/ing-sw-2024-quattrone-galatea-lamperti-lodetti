package it.polimi.ingsw.controller.FSM;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.MainController;

abstract public class State {
    static State SetupGame, PlayerTurn, CheckPoint, CalculateWinner,  UpdateGame;
    protected GameController gameController;
    protected MainController mainController;
    public State (GameController gameController){
        this.gameController = gameController;
    }
    public State (MainController mainController){
        this.mainController = mainController;
    }
    public State() {
    }
    public abstract String handleInput();
    //now we should define all the methods.
    public abstract void start();
    public abstract void goNextState();
    public abstract void goToSpecifiedState(State state);

}
