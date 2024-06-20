package it.polimi.ingsw.view.GUI.controller;

import it.polimi.ingsw.view.FsmGame;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;


//visualizza l'interfaccia di error dove viene stampato il messaggio di errore e viene mostrato
//un bottone per uscire dal gioco
public class AbortedGameController extends AbstractController{

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
