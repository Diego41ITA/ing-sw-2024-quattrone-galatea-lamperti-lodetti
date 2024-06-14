package it.polimi.ingsw.view.GUI.controller;

import it.polimi.ingsw.view.FsmGame;
import it.polimi.ingsw.view.input.InputGui;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import static it.polimi.ingsw.view.GUI.ImageAssociator.associatorPng2Card;

public class PlayerGoalController extends AbstractController {

    @FXML
    ImageView firstImage;

    @FXML
    ImageView secondImage;

    /**
     * this method initializes the controller with other attribute.
     * @param game is a new version of game
     */
    public void setUpController(FsmGame game){

        setGame(game);

        int id1 = game.availableGoalCard.getFirst();
        int id2 = game.availableGoalCard.getLast();

        firstImage.setImage(new Image(associatorPng2Card(String.valueOf(id1),true)));
        secondImage.setImage(new Image(associatorPng2Card(String.valueOf(id2),true)));
    }
    // se con il mouse clicco sulla immagine allora mette 1 in multipleResponses
    public void selectFirst(MouseEvent e){
        multipleResponses.add("1");

    }
    // se con il mouse clicco sulla immagine allora mette 2 in multipleResponses
    public void selectSecond(MouseEvent e){
        multipleResponses.add("2");
    }
}
