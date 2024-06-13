package it.polimi.ingsw.view.GUI.controller;

import it.polimi.ingsw.model.card.Card;
import it.polimi.ingsw.view.input.InputGui;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

import static it.polimi.ingsw.view.GUI.ImageAssociator.associatorPng2Card;

public class TableOfDecksController extends InputGui {
    //ho un metodo che ha un arraylist di id che mi indicano le carte e gli obiettivi
    //attraverso gli id piazzerò le carte nelle imageView
    //le goals non sono cliccabili tutte le altre carte si, se la carta ha id == 0 si rende invisibile
    @FXML
    private ImageView deckResource;
    @FXML
    private ImageView deckGold;
    @FXML
    private ImageView card1;
    @FXML
    private ImageView card2;
    @FXML
    private ImageView card3;
    @FXML
    private ImageView card4;
    @FXML
    private ImageView goal1;
    @FXML
    private ImageView goal2;
    //come argomenti deve ricevere gli id delle carte sul tavolo, gli id delle carte in cima ai mazzi e gli id delle carte goal
    public void setUp(ArrayList<Integer> cards, ArrayList<Integer> decks, ArrayList<Integer> goals){
        if(cards.get(0)==0){
            card1.setVisible(false);
        }else{
            card1.setImage(new Image(associatorPng2Card(String.valueOf(cards.get(0)),true)));
        }
        if(cards.get(1)==0){
            card2.setVisible(false);
        }else{
            card2.setImage(new Image(associatorPng2Card(String.valueOf(cards.get(1)),true)));
        }
        if(cards.get(2)==0){
            card3.setVisible(false);
        }else{
            card3.setImage(new Image(associatorPng2Card(String.valueOf(cards.get(2)),true)));
        }
        if(cards.get(3)==0){
            card4.setVisible(false);
        }else{
            card4.setImage(new Image(associatorPng2Card(String.valueOf(cards.get(3)),true)));
        }
        if(decks.get(0)==0){
            deckResource.setVisible(false);
        }else{
            deckGold.setImage(new Image(associatorPng2Card(String.valueOf(decks.get(0)),false)));
        }
        if(decks.get(1)==0){
            deckGold.setVisible(false);
        }else{
            deckGold.setImage(new Image(associatorPng2Card(String.valueOf(decks.get(1)),false)));
        }
        goal1.setImage(new Image(associatorPng2Card(String.valueOf(goals.get(0)),true)));
        goal2.setImage(new Image(associatorPng2Card(String.valueOf(goals.get(1)),true)));
    }


    public void drawDeckGold(MouseEvent e){
        multipleResponses.add("A");
        multipleResponses.add("gold");
    }
    public void drawDeckResource(MouseEvent e){
        multipleResponses.add("A");
        multipleResponses.add("resource");
    }
    public void drawFirstCard(MouseEvent e){
        multipleResponses.add("B");
        multipleResponses.add("1");
    }
    public void drawSecondCard(MouseEvent e){
        multipleResponses.add("B");
        multipleResponses.add("2");
    }
    public void drawThirdCard(MouseEvent e){
        multipleResponses.add("B");
        multipleResponses.add("3");
    }
    public void drawFourthCard(MouseEvent e){
        multipleResponses.add("B");
        multipleResponses.add("4");
    }


}
