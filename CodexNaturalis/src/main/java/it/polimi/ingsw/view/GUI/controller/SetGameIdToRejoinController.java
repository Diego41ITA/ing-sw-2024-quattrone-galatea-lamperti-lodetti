package it.polimi.ingsw.view.GUI.controller;

import it.polimi.ingsw.view.FsmGame;
import it.polimi.ingsw.view.GUI.controller.abstractControllers.AbstractController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class SetGameIdToRejoinController extends AbstractController {
    @FXML
    private Text game;
    @FXML
    private TextField textField;
    @FXML
    private Button button;

    /**
     * this method set up additional attributes for the controller class if this is needed.
     *
     * @param game is a new version of the game
     */
    public void setUpController(FsmGame game){
        setGame(game);
    }

    /**
     * Write in multipleResponses the correct output for the mouse event.
     */
    public void getGameId(ActionEvent actionEvent) {
        multipleResponses.add(textField.getText());
    }
}
