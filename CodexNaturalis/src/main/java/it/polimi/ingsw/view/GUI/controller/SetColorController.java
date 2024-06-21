package it.polimi.ingsw.view.GUI.controller;

import it.polimi.ingsw.model.gameDataManager.Color;
import it.polimi.ingsw.view.FsmGame;
import it.polimi.ingsw.view.GUI.controller.abstractControllers.AbstractController;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;

import java.util.ArrayList;

import static it.polimi.ingsw.view.UI.freeColors;

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

    public void clickGreen(){
        multipleResponses.add("green");
    }
    public void clickYellow(){
        multipleResponses.add("yellow");
    }
    public void clickRed(){
        multipleResponses.add("red");
    }
    public void clickBlue(){
        multipleResponses.add("blue");
    }
    //mette la visibilità dei bottoni false di dafault

    public void initializeColorButton(){
        red.setOpacity(0.7);
        red.setCursor(Cursor.DEFAULT);
        green.setOpacity(0.7);
        green.setCursor(Cursor.DEFAULT);
        blue.setOpacity(0.7);
        blue.setCursor(Cursor.DEFAULT);
        yellow.setOpacity(0.7);
        yellow.setCursor(Cursor.DEFAULT);
    }

    /**
     * this method set visible only the available button.
     * @param game it's a new version of game
     */
    public void setUpController(FsmGame game) {
        setGame(game);
        ArrayList<Color> colors = freeColors(game.getView());
        initializeColorButton();
        for (Color color:colors) {
            if (color == Color.RED) {
                red.setOpacity(1);
                red.setOnMouseClicked(mouseEvent -> clickRed());
                red.setCursor(Cursor.HAND);
            } else if (color == Color.GREEN) {
                green.setOpacity(1);
                green.setOnMouseClicked(mouseEvent -> clickGreen());
                green.setCursor(Cursor.HAND);
            } else if (color == Color.BLUE) {
                blue.setOpacity(1);
                blue.setOnMouseClicked(mouseEvent -> clickBlue());
                blue.setCursor(Cursor.HAND);
            } else {
                yellow.setOpacity(1);
                yellow.setOnMouseClicked(mouseEvent -> clickYellow());
                yellow.setCursor(Cursor.HAND);
            }
        }
    }
}
