package it.polimi.ingsw.view.GUI.controller;

import it.polimi.ingsw.view.FsmGame;
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

    private int[] id;

    @Override
    public void setCardDetails(int[] id) {
        firstImage.setImage(new Image(associatorPng2Card(String.valueOf(id[0]),true)));
        secondImage.setImage(new Image(associatorPng2Card(String.valueOf(id[1]),true)));
        this.id = id;
    }

    /**
     * this method initializes the controller with other attribute.
     * @param game is a new version of game
     */
    @Override
    public void setUpController(FsmGame game){
        setGame(game);
    }


    // se con il mouse clicco sulla immagine allora mette 1 in multipleResponses
    public void selectFirst(MouseEvent e){
        multipleResponses.add(String.valueOf(id[0]));

    }
    // se con il mouse clicco sulla immagine allora mette 2 in multipleResponses
    public void selectSecond(MouseEvent e){
        multipleResponses.add(String.valueOf(id[1]));
    }
}
