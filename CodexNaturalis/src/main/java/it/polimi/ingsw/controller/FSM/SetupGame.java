package it.polimi.ingsw.controller.FSM;

import it.polimi.ingsw.model.gameDataManager.Game;

public class SetupGame extends State {

    @Override
    public String handleInput(Game game, String input) {
        return input;
    }

    @Override
    public String start(Game game) {
        return "stato di setup";
    }
}
