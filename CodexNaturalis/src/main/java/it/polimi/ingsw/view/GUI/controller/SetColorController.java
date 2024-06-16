package it.polimi.ingsw.view.GUI.controller;

import it.polimi.ingsw.model.gameDataManager.Color;
import it.polimi.ingsw.view.FsmGame;
import it.polimi.ingsw.view.TUI.Cli;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;

import java.util.ArrayList;

public class SetColorController extends AbstractController {
    @FXML
    private Button yellow;
    @FXML
    private Button red;
    @FXML
    private Button blue;
    @FXML
    private Button green;
    //quando clicco un bottone aggiungo il colore corrispettivo all'interno di multipleResponses

    public void clickGreen(ActionEvent actionEvent){
        multipleResponses.add("green");
    }
    public void clickYellow(ActionEvent actionEvent){
        multipleResponses.add("yellow");
    }
    public void clickRed(ActionEvent actionEvent){
        multipleResponses.add("red");
    }
    public void clickBlue(ActionEvent actionEvent){
        multipleResponses.add("blue");
    }
    //mette la visibilità dei bottoni false di dafault
    @FXML
    public void initializeColorButton(){
        red.setVisible(false);
        green.setVisible(false);
        blue.setVisible(false);
        yellow.setVisible(false);
    }

    /**
     * this method set visible only the available button.
     * @param game it's a new version of game
     */
    public void setUpController(FsmGame game) {
        setGame(game);
        ArrayList<Color> colors = Cli.freeColors(game.getView());

        for (Color color:colors) {
            if (color == Color.RED) {
                red.setVisible(true);
            } else if (color == Color.GREEN) {
                green.setVisible(true);
            } else if (color == Color.BLUE) {
                blue.setVisible(true);
            } else {
                yellow.setVisible(true);
            }
        }
    }
}
