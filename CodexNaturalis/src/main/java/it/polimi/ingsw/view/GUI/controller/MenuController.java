package it.polimi.ingsw.view.GUI.controller;

import it.polimi.ingsw.view.FsmGame;
import it.polimi.ingsw.view.GUI.controller.abstractControllers.AbstractController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the Menu scene.
 */
public class MenuController extends AbstractController {
    /**
     * Write in multipleResponses the correct output for the mouse event.
     */
    public void actionJoinRandomGame(ActionEvent e){
        multipleResponses.add("A");
    }

    /**
     * Write in multipleResponses the correct output for the mouse event.
     */
    public void actionReconnect(ActionEvent e){
        multipleResponses.add("B");
    }

    /**
     * this method set up additional attributes for the controller class if this is needed.
     *
     * @param updatedGame a new version of the game
     */
    @Override
    public void setUpController(FsmGame updatedGame) {
        setGame(updatedGame);
    }

}
