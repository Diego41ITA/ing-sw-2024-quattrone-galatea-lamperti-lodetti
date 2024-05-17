package it.polimi.ingsw.view.GUI.controller;

import it.polimi.ingsw.view.GUI.Gui;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import javax.swing.*;

public class SetNumberOfPlayersController extends Gui {
    @FXML
    private Text game;
    @FXML
    private TextField textField;
    @FXML
    private Button button;

    //quando premo il bottone esso viene cancellato così sembra che non si può scegliere un nuovo numero
    public void setGameid(String id){
        game.setText(id);

    }
    //fin quando in multiplesResponses non abbiamo "setPlayers" allora il gameflow rimane nello stesso punto
    //nel gameflow dobbiamo avere un ciclo while che controlla l'ultimo valore di multipleResponses nella ui
    public void getNumber(ActionEvent actionEvent) {
        multipleResponses.add("setPlayers");
    }

}
