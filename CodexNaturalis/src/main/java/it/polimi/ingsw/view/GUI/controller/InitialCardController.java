package it.polimi.ingsw.view.GUI.controller;
import it.polimi.ingsw.model.gameDataManager.Color;
import it.polimi.ingsw.view.GUI.Gui;
import it.polimi.ingsw.view.input.InputGui;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.event.*;
import javafx.scene.text.Text;

import static it.polimi.ingsw.view.GUI.ImageAssociator.associatorPng2Card;
import static it.polimi.ingsw.view.GUI.ImageAssociator.makerAssociator;
public class InitialCardController extends InputGui {

    @FXML
    ImageView imageFront;

    @FXML
    ImageView imageBack;

    public void setUp(int id){
        imageBack.setImage(new Image(associatorPng2Card(String.valueOf(id),false)));
        imageFront.setImage(new Image(associatorPng2Card(String.valueOf(id),true)));
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
