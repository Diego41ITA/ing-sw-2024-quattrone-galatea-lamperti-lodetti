package it.polimi.ingsw.view.GUI.controller;
import it.polimi.ingsw.view.FsmGame;
import it.polimi.ingsw.view.GUI.DbCardInfo;
import it.polimi.ingsw.view.GUI.MultipleResponses;
import it.polimi.ingsw.view.GUI.controller.abstractControllers.AbstractController;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import static it.polimi.ingsw.view.GUI.ImageAssociator.associatorPng2Card;

/**
 * Controller of the InitialCard scene.
 */
public class InitialCardController extends AbstractController {

    @FXML
    ImageView imageFront;

    @FXML
    ImageView imageBack;

    /**
     * An attribute that stores all the input corresponding to the mouse events.
     */
    private MultipleResponses multipleResponses;

    /**
     * this method set up additional attributes for the controller class if this is needed.
     *
     * @param updatedGame       is the new version of the game
     * @param multipleResponses
     */
    @Override
    public void setUpController(FsmGame updatedGame, MultipleResponses multipleResponses){

        setGame(updatedGame);

        this.multipleResponses=multipleResponses;

        int cardId = DbCardInfo.getInstance().readCardRecord().cardId();

        imageBack.setImage(new Image(associatorPng2Card(String.valueOf(cardId),false)));
        imageFront.setImage(new Image(associatorPng2Card(String.valueOf(cardId),true)));

        imageFront.setCursor(Cursor.HAND);
        imageBack.setCursor(Cursor.HAND);

        imageFront.setOnMouseEntered(mouseEvent -> {
            imageFront.setScaleX(1.2);
            imageFront.setScaleY(1.2);
        });

        imageFront.setOnMouseExited(mouseEvent -> {
            imageFront.setScaleX(1);
            imageFront.setScaleY(1);
        });

        imageBack.setOnMouseEntered(mouseEvent -> {
            imageBack.setScaleX(1.2);
            imageBack.setScaleY(1.2);
        });

        imageBack.setOnMouseExited(mouseEvent -> {
            imageBack.setScaleX(1);
            imageBack.setScaleY(1);
        });
    }

    /**
     * Write in multipleResponses the correct output for the mouse event.
     */
    public void selectFront(MouseEvent e){
        multipleResponses.add("true");
    }

    /**
     * Write in multipleResponses the correct output for the mouse event.
     */
    public void selectBack(MouseEvent e){
        multipleResponses.add("false");
    }


}
