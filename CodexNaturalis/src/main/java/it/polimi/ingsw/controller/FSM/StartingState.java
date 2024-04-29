package it.polimi.ingsw.controller.FSM;

import it.polimi.ingsw.model.gameDataManager.Game;
import it.polimi.ingsw.model.gameDataManager.GameStation;
import it.polimi.ingsw.model.gameDataManager.Player;

public class StartingState extends State {
    static int players = 0;
    @Override
    public String handleInput(Game game,  String input) {
        if (players != 3) { //game.getMaxNumberPlayer()-1
            players++;
            return input + " joined, waiting for player";
        } else {
            players++;
            return "next-state";
        }
    }

        @Override
    public String start(Game game){
        return "Nickname: ";
    }
}
