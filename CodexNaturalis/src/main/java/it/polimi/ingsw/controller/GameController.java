package it.polimi.ingsw.controller;


import it.polimi.ingsw.controller.FSM.*;
import it.polimi.ingsw.model.gameDataManager.*;

public class GameController {
    final Game game;
    private State currentState;

    public GameController() {
        this.game = new Game("123");
        currentState = new StartingState();
    }

    public Game getGame() {
        return game;
    }

    public State getCurrentState() {
        return currentState;
    }

    public String start(){
        return currentState.start(game);
    }

    public String handleInput(String input){
        return currentState.handleInput(game, input);
    }

    public void setCurrentState(State state){
        this.currentState = state;
    }

}