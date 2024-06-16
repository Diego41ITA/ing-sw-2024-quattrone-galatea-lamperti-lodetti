package it.polimi.ingsw.view.GUI.controller;

import it.polimi.ingsw.view.FsmGame;
import it.polimi.ingsw.view.GUI.MultipleResponses;

/**
 * this is a class that generalize the controller concept
 */
public abstract class AbstractController {
    private FsmGame game;

    protected static MultipleResponses multipleResponses = MultipleResponses.getInstance();

    /**
     * a subclass can use this method to get the private FsmGame attribute
     * @return the attribute
     */
    protected FsmGame getGameInfo(){
        return game;
    }

    /**
     * this method sets a new fresh updated game.
     * @param updatedGame is the new version of the game
     */
    protected void setGame(FsmGame updatedGame){
        this.game = updatedGame;
    }

    /**
     * this method set up additional attributes for the controller class if this is needed.
     */
    public abstract void setUpController(FsmGame updatedGame);
}
