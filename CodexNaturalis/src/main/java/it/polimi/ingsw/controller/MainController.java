package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.gameDataManager.Matches;

import java.util.ArrayList;

public class MainController {
    //controller principale
    private Matches onGoingMatches;
    private ArrayList<GameController> runningGames;

    public MainController(){
        runningGames = new ArrayList<>();
    }
}
