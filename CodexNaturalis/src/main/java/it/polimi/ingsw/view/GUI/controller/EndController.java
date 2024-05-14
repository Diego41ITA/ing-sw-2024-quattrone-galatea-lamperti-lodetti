package it.polimi.ingsw.view.GUI.controller;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.event.ActionEvent;
import javafx.scene.text.Text;
public class EndController {
    @FXML
    private Text winner;
    @FXML
    private Button exit;

    //mi fa vedere il label e il bottone dell'interfaccia utente
    public void show(String name) {
        winner.setText(name);
        winner.setVisible(true);
    }

    //Quando clicco il mouse eseguo questo metodo che mi esce dall'app
    public void exit(ActionEvent e){
        System.exit(-1);;
    }
}
