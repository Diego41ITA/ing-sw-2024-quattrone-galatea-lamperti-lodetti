package it.polimi.ingsw.view.GUI.controller;
import it.polimi.ingsw.view.FsmGame;
import it.polimi.ingsw.view.GUI.MultipleResponses;
import it.polimi.ingsw.view.GUI.controller.abstractControllers.AbstractController;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * Controller of the NickNameSetUp scene.
 */
public class NickNameController extends AbstractController {
    @FXML
    private TextField nickName;
    @FXML
    private Button enter;
    /**
     * An attribute that stores all the input corresponding to the mouse events.
     */
    private MultipleResponses multipleResponses;
    /**
     * Write in multipleResponses the correct output for the mouse event.
     */
    public void OkButton(javafx.event.ActionEvent actionEvent) {
        multipleResponses.add(nickName.getText());
    }

    /**
     * this method set up additional attributes for the controller class if this is needed.
     *
     * @param updatedGame       is a new version of the game
     * @param multipleResponses
     */
    @Override
    public void setUpController(FsmGame updatedGame, MultipleResponses multipleResponses) {
        setGame(updatedGame);
        this.multipleResponses=multipleResponses;
    }
}
