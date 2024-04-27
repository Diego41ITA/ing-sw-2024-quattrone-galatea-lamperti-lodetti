package it.polimi.ingsw.controller;

abstract public class State {
    Static State SetupGame, PlayerTurn, CheckPoint, CalculateWinner,  UpdateGame;

    public abstract void HandleInput(String input);
    //now we should define all the methods.
    public abstract String start();
}
