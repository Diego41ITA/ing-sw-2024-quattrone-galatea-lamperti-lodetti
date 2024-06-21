package it.polimi.ingsw.view.GUI.controller;

import it.polimi.ingsw.view.FsmGame;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class SetNumberOfPlayersController extends AbstractController {
    @FXML
    private Text game;
    @FXML
    private TextField textField;
    @FXML
    private Button button;

    public void setUpController(FsmGame game){
        setGame(game);
        this.game.setText(game.getView().getId());

    }
    //fin quando in multiplesResponses non abbiamo un numero valido allora il gameflow/GUiinput rimane nello stesso punto
    //nella GuiInput dobbiamo avere un ciclo while che controlla l'ultimo valore di multipleResponses
    public void getNumber(ActionEvent actionEvent) {
        multipleResponses.add(textField.getText());
    }
}
