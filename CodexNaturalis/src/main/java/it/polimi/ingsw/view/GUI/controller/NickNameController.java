package it.polimi.ingsw.view.GUI.controller;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import java.awt.event.ActionEvent;

public class NickNameController {
    @FXML
    private TextField nickName;
    @FXML
    private Button enter;
    public void OkButton(javafx.event.ActionEvent actionEvent) {
        //bisogna utilizzare una classe astratta che viene estesa da questo controller
        //questo classe astratta si occuperà di leggere gli input da tastiera
    }
}
