package it.polimi.ingsw.view.GUI.controller;
import it.polimi.ingsw.view.FsmGame;
import javafx.fxml.FXML;
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
    }

    @Override
    public void setCardDetails(int[] id){
        imageBack.setImage(new Image(associatorPng2Card(String.valueOf(id[0]),false)));
        imageFront.setImage(new Image(associatorPng2Card(String.valueOf(id[0]),true)));
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
