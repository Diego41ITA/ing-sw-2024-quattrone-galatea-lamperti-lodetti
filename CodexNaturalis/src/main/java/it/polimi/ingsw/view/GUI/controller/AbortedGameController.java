package it.polimi.ingsw.view.GUI.controller;

import it.polimi.ingsw.view.FsmGame;
import it.polimi.ingsw.view.GUI.controller.abstractControllers.AbstractController;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.util.Duration;


/**
 *Controller for the AbortedGame scene.
 */
public class AbortedGameController extends AbstractController {

    /**
     * this method set up additional attributes and properties
     * for the controller class if this is needed.
     *
     * @param updatedGame is the new version of the game
     */
    @Override
    public void setUpController(FsmGame updatedGame) {
        PauseTransition delay = new PauseTransition(Duration.seconds(10));
        delay.setOnFinished(e -> {
            // Close the application after 10 seconds
            Platform.exit();
        });
    }
}
