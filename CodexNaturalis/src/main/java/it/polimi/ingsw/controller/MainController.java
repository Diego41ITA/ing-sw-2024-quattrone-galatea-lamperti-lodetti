package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.FSM.StartingState;
import it.polimi.ingsw.controller.FSM.State;
import it.polimi.ingsw.model.ImmutableGame;
import it.polimi.ingsw.model.gameDataManager.Game;
import it.polimi.ingsw.model.gameDataManager.Matches;
import it.polimi.ingsw.view.TextualUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainController {
    private List<GameController> GameControllers;

    public MainController(Game game) {
        this.model = game;
    }
}
