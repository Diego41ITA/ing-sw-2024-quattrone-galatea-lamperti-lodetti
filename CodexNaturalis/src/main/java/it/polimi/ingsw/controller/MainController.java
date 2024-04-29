package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.FSM.StartingState;
import it.polimi.ingsw.controller.FSM.State;
import it.polimi.ingsw.model.gameDataManager.Matches;

import java.util.ArrayList;

public class MainController {
    //controller principale
    private Matches onGoingMatches;
    private ArrayList<GameController> runningGames;
    private State currentState;

    public MainController(){
        runningGames = new ArrayList<>();
    }

    public Matches getOnGoingMatches() {
        return onGoingMatches;
    }

    public void start(){
        this.currentState= new StartingState();
        this.currentState.start();
    }

    public State getCurrentState(){
        return this.currentState;
    }
}
