package it.polimi.ingsw.controller.FSM;

abstract public class State {
    static State SetupGame, PlayerTurn, CheckPoint, CalculateWinner,  UpdateGame;

    public abstract String HandleInput(String input);
    //now we should define all the methods.
    public abstract String start();
}
