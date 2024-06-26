package it.polimi.ingsw.view.GUI.controller;

import it.polimi.ingsw.view.FsmGame;
import it.polimi.ingsw.view.GUI.MultipleResponses;
import it.polimi.ingsw.view.GUI.controller.abstractControllers.AbstractController;
import javafx.application.Platform;
import javafx.event.ActionEvent;

/**
 * Controller for the CreateGame scene.
 */
public class CreateGameController extends AbstractController {

    /**
     * An attribute that stores all the input corresponding to the mouse events.
     */
    private MultipleResponses multipleResponses;

    /**
     * Action corresponding to the "close app" button
     */
    public void exit(ActionEvent e){
        Platform.exit();
    }

    /**
     * Write in multipleResponses the correct output for the mouse event.
     */
    public void createGame(ActionEvent e){
        multipleResponses.add("A");
    }

    /**
     * this method set up additional attributes for the controller class if this is needed.
     *
     * @param updatedGame       is the new version of the game
     * @param multipleResponses
     */
    @Override
    public void setUpController(FsmGame updatedGame, MultipleResponses multipleResponses) {
        setGame(updatedGame);
        this.multipleResponses = multipleResponses;
    }
}
