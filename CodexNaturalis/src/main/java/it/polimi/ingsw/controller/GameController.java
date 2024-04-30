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
        currentState.start();
        return null;
    }

    public String handleInput(String input){
        return currentState.handleInput();
    }

    public void setCurrentState(State state){
        this.currentState = state;
    }

}