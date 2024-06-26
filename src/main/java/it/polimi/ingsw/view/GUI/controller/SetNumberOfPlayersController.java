package it.polimi.ingsw.view.GUI.controller;

import it.polimi.ingsw.view.FsmGame;
import it.polimi.ingsw.view.GUI.MultipleResponses;
import it.polimi.ingsw.view.GUI.controller.abstractControllers.AbstractController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SetNumberOfPlayersController extends AbstractController {
    @FXML
    private TextField textField;
    @FXML
    private Button button;
    /**
     * An attribute that stores all the input corresponding to the mouse events.
     */
    private MultipleResponses multipleResponses;

    public void setUpController(FsmGame game, MultipleResponses multipleResponses){
        setGame(game);
        this.multipleResponses=multipleResponses;
    }

    /**
     * Write in multipleResponses the correct output for the mouse event.
     */
    public void getNumber(ActionEvent actionEvent) {
        multipleResponses.add(textField.getText());
    }
}
