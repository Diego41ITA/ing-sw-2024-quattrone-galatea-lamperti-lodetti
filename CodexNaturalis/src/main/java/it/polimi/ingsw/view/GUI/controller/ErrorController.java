package it.polimi.ingsw.view.GUI.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


    //visualizza l'interfaccia di error dove viene stampato il messaggio di errore e viene mostrato
//un bottone per uscire dal gioco
    public class ErrorController{
        @FXML
        private Label error;
        @FXML
        private Button button;
        private boolean needToExitApp;
        //fa vedere i nodi dell'interfaccia
        public void showError(String msg){
            error.setText(msg);
            error.setVisible(true);
            button.setVisible(true);// forse per i button show visible non serve
        }

        public void actionMenu(ActionEvent e) {
            System.exit(-1);
        }

    }
