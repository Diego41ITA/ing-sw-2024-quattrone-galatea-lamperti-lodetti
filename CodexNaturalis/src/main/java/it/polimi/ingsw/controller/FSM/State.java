package it.polimi.ingsw.controller.FSM;

abstract public class State {
    static State SetupGame, PlayerTurn, CheckPoint, CalculateWinner,  UpdateGame;

    public abstract String handleInput(String input);
    //now we should define all the methods.
    public abstract void start();
}
