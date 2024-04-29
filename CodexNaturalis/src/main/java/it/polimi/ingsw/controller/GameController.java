package it.polimi.ingsw.controller;


import it.polimi.ingsw.controller.FSM.StartingState;
import it.polimi.ingsw.controller.FSM.State;
import it.polimi.ingsw.model.gameDataManager.*;

public class GameController {
    final Game game;
    private State currentState;

    public GameController() {

        this.game = new Game("123");
        currentState = new StartingState();

    }

    public State getCurrentState() {
        return currentState;
    }

    public Boolean start(){
        currentState.start();
        return true; // da sistemare, ritorna true se successo false altrimenti
    }


}