package it.polimi.ingsw.controller.FSM;

import it.polimi.ingsw.model.gameDataManager.Game;

abstract public class State {
    static State SetupGame, PlayerTurn, CheckPoint, CalculateWinner,  UpdateGame;

    public abstract String handleInput(Game game, String input);
    //now we should define all the methods.
    public abstract String start(Game game);
}
