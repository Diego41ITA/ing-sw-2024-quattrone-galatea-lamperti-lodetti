package it.polimi.ingsw.view.GUI.controller;
import it.polimi.ingsw.view.GUI.Gui;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import java.awt.event.ActionEvent;

public class NickNameController extends Gui {
    @FXML
    private TextField nickName;
    @FXML
    private Button enter;
    public void OkButton(javafx.event.ActionEvent actionEvent) {
        //fa in modo che mi lanci l'interfaccia menù di gioco
        show_createNewGame();
    }
}
