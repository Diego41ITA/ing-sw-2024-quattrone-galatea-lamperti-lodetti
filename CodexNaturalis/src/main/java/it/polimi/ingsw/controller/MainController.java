package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.gameDataManager.Game;

import java.util.List;

public class MainController {
    private List<GameController> GameControllers;

    public MainController(Game game) {
        this.model = game;
    }
}
