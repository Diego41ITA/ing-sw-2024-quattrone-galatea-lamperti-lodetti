package it.polimi.ingsw.view.GUI.controller;

import it.polimi.ingsw.view.GUI.Gui;
import it.polimi.ingsw.view.input.InputGui;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import javax.swing.*;

public class SetNumberOfPlayersController extends InputGui {
    @FXML
    private Text game;
    @FXML
    private TextField textField;
    @FXML
    private Button button;
    public void setGameid(String id){
        game.setText(id);

    }
    //fin quando in multiplesResponses non abbiamo "setPlayers" allora il gameflow/GUiinput rimane nello stesso punto
    //nella GuiInput dobbiamo avere un ciclo while che controlla l'ultimo valore di multipleResponses
    public void getNumber(ActionEvent actionEvent) {
        multipleResponses.add("setPlayers");
    }

}
