package it.polimi.ingsw.view.GUI.controller;

import it.polimi.ingsw.view.FsmGame;
import it.polimi.ingsw.view.GUI.DbCardInfo;
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

    @FXML
    private int firstGoal;
    @FXML
    private int secondGoal;

    /**
     * this method initializes the controller with other attribute.
     * @param game is a new version of game
     */
    @Override
    public void setUpController(FsmGame game){

        setGame(game);

        this.firstGoal = DbCardInfo.getInstance().readCardRecord().cardId();
        this.secondGoal = DbCardInfo.getInstance().readCardRecord().cardId();

        firstImage.setImage(new Image(associatorPng2Card(String.valueOf(firstGoal),true)));
        secondImage.setImage(new Image(associatorPng2Card(String.valueOf(secondGoal),true)));
    }


    // se con il mouse clicco sulla immagine allora mette 1 in multipleResponses
    public void selectFirst(MouseEvent e){
        multipleResponses.add(String.valueOf(firstGoal));

    }
    // se con il mouse clicco sulla immagine allora mette 2 in multipleResponses
    public void selectSecond(MouseEvent e){
        multipleResponses.add(String.valueOf(secondGoal));
    }
}
