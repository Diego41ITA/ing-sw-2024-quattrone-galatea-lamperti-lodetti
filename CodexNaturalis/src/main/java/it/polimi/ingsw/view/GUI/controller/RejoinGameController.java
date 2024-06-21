package it.polimi.ingsw.view.GUI.controller;

import it.polimi.ingsw.view.FsmGame;
import it.polimi.ingsw.view.GUI.controller.abstractControllers.AbstractController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class RejoinGameController extends AbstractController {
    @FXML
    private TextField gameId;
    @FXML
    private Button enter;

    public void OkButton(javafx.event.ActionEvent actionEvent) {
        //nella guiinput si può mettere un ciclo while che fin quando in multipleResponses non abbiamo
        //la stringa adeguata il gameflow/guiInput aspetta
        multipleResponses.add(gameId.getText());
    }

    /**
     * this method set up additional attributes for the controller class if this is needed.
     *
     * @param updatedGame is a new version of the game
     */
    @Override
    public void setUpController(FsmGame updatedGame) {
        setGame(updatedGame);
    }
}
