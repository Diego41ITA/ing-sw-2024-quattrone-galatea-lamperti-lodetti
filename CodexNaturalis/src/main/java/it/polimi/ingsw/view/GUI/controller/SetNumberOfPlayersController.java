package it.polimi.ingsw.view.GUI.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.text.Text;

import javax.swing.*;
import java.awt.*;

public class SetNumberOfPlayersController  {
    @FXML
    private Text game;
    @FXML
    private Text player1;
    @FXML
    private Text player2;
    @FXML
    private Text player3;
    @FXML
    private Text player4;
    @FXML
    private TextField text;
    @FXML
    private CheckBox ready1;
    @FXML
    private CheckBox ready2;
    @FXML
    private CheckBox ready3;
    @FXML
    private CheckBox ready4;

    @FXML
    private Button enter;

    //quando premo il bottone esso viene cancellato così sembra che non si può scegliere un nuovo numero
    public void setGameid(String id){
        game.setText(id);

    }
    //serve a settare a check i buttonbox quando entrano i giocatori
    public void setCheckBoxSelected(int num, String name){
        if(num == 1) {
            ready1.setSelected(true);
            player1.setText(name);
        }else if(num == 2){
            ready2.setSelected(true);
            player2.setText(name);
        }else if(num ==3){
            ready3.setSelected(true);
            player3.setText(name);
        }else{
            ready4.setSelected(true);
            player4.setText(name);
        }
    }

    public void getNumber(ActionEvent actionEvent) {
        enter.setVisible(false);
    }
}
