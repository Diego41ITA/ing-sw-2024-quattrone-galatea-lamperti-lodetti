package it.polimi.ingsw.view.GUI.controller;
import it.polimi.ingsw.view.FsmGame;
import it.polimi.ingsw.view.GUI.DbCardInfo;
import it.polimi.ingsw.view.GUI.controller.abstractControllers.AbstractController;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import static it.polimi.ingsw.view.GUI.ImageAssociator.associatorPng2Card;

public class InitialCardController extends AbstractController {

    @FXML
    ImageView imageFront;

    @FXML
    ImageView imageBack;

    @Override
    public void setUpController(FsmGame game){

        setGame(game);

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

    // se con il mouse clicco sulla immagine allora mette true in multipleResponses
    public void selectFront(MouseEvent e){
        multipleResponses.add("true");
    }
    // se con il mouse clicco sulla immagine allora mette false in multipleResponses
    public void selectBack(MouseEvent e){
        multipleResponses.add("false");
    }


}
