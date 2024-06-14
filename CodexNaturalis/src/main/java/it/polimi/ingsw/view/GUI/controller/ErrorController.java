package it.polimi.ingsw.view.GUI.controller;

import it.polimi.ingsw.view.FsmGame;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


    //visualizza l'interfaccia di error dove viene stampato il messaggio di errore e viene mostrato
//un bottone per uscire dal gioco
    public class ErrorController extends AbstractController{
        @FXML
        private Label error;
        @FXML
        private Button button;
        //fa vedere i nodi dell'interfaccia
        public void showError(String msg){
            error.setText(msg);
        }

        public void actionMenu(ActionEvent e) {
            System.exit(-1);
        }

        /**
         * this method set up additional attributes for the controller class if this is needed.
         *
         * @param updatedGame is the new version of the game
         */
        @Override
        public void setUpController(FsmGame updatedGame) {
            setGame(updatedGame);
        }
    }
