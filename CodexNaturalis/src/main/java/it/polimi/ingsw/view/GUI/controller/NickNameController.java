package it.polimi.ingsw.view.GUI.controller;
import it.polimi.ingsw.view.GUI.Gui;
import it.polimi.ingsw.view.input.InputGui;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import java.awt.event.ActionEvent;

public class NickNameController extends InputGui {
    @FXML
    private TextField nickName;
    @FXML
    private Button enter;
    public void OkButton(javafx.event.ActionEvent actionEvent) {
        //nella guiinput si può mettere un ciclo while che fin quando in multipleResponses non abbiamo
        //la stringa ok il gameflow/guiInput aspetta
        multipleResponses.add("ok");
    }
}
