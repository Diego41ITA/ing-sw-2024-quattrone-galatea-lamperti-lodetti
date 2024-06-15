package it.polimi.ingsw.view.GUI.controller;

import it.polimi.ingsw.model.card.Card;
import it.polimi.ingsw.model.card.Deck;
import it.polimi.ingsw.model.card.GoldCard;
import it.polimi.ingsw.model.card.ResourceCard;
import it.polimi.ingsw.view.FsmGame;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static it.polimi.ingsw.view.GUI.ImageAssociator.associatorPng2Card;

public class TableOfDecksController extends AbstractController {
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
    private Map<ImageView, Integer> mapping;

    /*come argomenti deve ricevere gli id delle carte sul tavolo, gli id delle carte in cima ai mazzi e gli id delle carte goal
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
            deckResource.setImage(new Image(associatorPng2Card(String.valueOf(decks.get(0)),false)));
        }
        if(decks.get(1)==0){
            deckGold.setVisible(false);
        }else{
            deckGold.setImage(new Image(associatorPng2Card(String.valueOf(decks.get(1)),false)));
        }
        goal1.setImage(new Image(associatorPng2Card(String.valueOf(goals.get(0)),true)));
        goal2.setImage(new Image(associatorPng2Card(String.valueOf(goals.get(1)),true)));
    }
    */

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
        multipleResponses.add(String.valueOf(mapping.get(card1)));
    }
    public void drawSecondCard(MouseEvent e){
        multipleResponses.add("B");
        multipleResponses.add(String.valueOf(mapping.get(card2)));
    }
    public void drawThirdCard(MouseEvent e){
        multipleResponses.add("B");
        multipleResponses.add(String.valueOf(mapping.get(card3)));
    }
    public void drawFourthCard(MouseEvent e){
        multipleResponses.add("B");
        multipleResponses.add(String.valueOf(mapping.get(card3)));
    }


    @Override
    public void setCardDetails(int[] id) {

    }

    /**
     * this method set up additional attributes for the controller class if this is needed.
     *
     * @param updatedGame a new version of game.
     */
    @Override
    public void setUpController(FsmGame updatedGame) {

        setGame(updatedGame);

        Map<Integer, Integer> pos_key = new HashMap<>();

        //goals are always present on the board
        ArrayList<Integer> goals = (ArrayList<Integer>) updatedGame.getView().getTableOfDecks().getGoals()
                .stream().map(Card::getCardId)
                .toList();

        //some of the cards could be absent, so I need to remember the position of them. In this way if a card is absent
        //the program will assign to it "cardId" = 0.
        ArrayList<Integer> cards = (ArrayList<Integer>) updatedGame.getView().getTableOfDecks().getCards()
                .stream().map(c -> {
                    if(c != null){
                        return c.getCardId();
                    }
                    else
                        return 0;
                })
                .toList();


        //for the decks I need only the first card
        Deck<ResourceCard> resource = updatedGame.getView().getTableOfDecks().getDeckResource();
        Deck<GoldCard> gold = updatedGame.getView().getTableOfDecks().getDeckGold();
        ArrayList<Integer> decks = new ArrayList<>();
        if(resource.getDimension() != 0)
            decks.add(resource.getFirst().getCardId());
        else
            decks.add(resource.getDimension());

        if(gold.getDimension() != 0)
            decks.add(gold.getFirst().getCardId());
        else
            decks.add(gold.getDimension());

        //bisogna settare un'immagine di default nel caso in cui la carta non sia presente (quindi get(i)==0).
        if(cards.get(0)==0){
            card1.setImage(new Image(getClass().getResourceAsStream("/images/menu.png")));
            card1.setVisible(false);
        }else{
            card1.setImage(new Image(associatorPng2Card(String.valueOf(cards.getFirst()),true)));
        }
        if(cards.get(1)==0){
            card2.setImage(new Image(getClass().getResourceAsStream("/images/menu.png")));
            card2.setVisible(false);
        }else{
            card2.setImage(new Image(associatorPng2Card(String.valueOf(cards.get(1)),true)));
        }
        if(cards.get(2)==0){
            card3.setImage(new Image(getClass().getResourceAsStream("/images/menu.png")));
            card3.setVisible(false);
        }else{
            card3.setImage(new Image(associatorPng2Card(String.valueOf(cards.get(2)),true)));
        }
        if(cards.get(3)==0){
            card4.setImage(new Image(getClass().getResourceAsStream("/images/menu.png")));
            card4.setVisible(false);
        }else{
            card4.setImage(new Image(associatorPng2Card(String.valueOf(cards.get(3)),true)));
        }
        if(decks.get(0)==0){
            deckResource.setImage(new Image(getClass().getResourceAsStream("/images/menu.png")));
            deckResource.setVisible(false);
        }else{
            deckGold.setImage(new Image(associatorPng2Card(String.valueOf(decks.get(0)),false)));
        }
        if(decks.get(1)==0){
            deckGold.setImage(new Image(getClass().getResourceAsStream("/images/menu.png")));
            deckGold.setVisible(false);
        }else{
            deckGold.setImage(new Image(associatorPng2Card(String.valueOf(decks.get(1)),false)));
        }
        goal1.setImage(new Image(associatorPng2Card(String.valueOf(goals.get(0)),true)));
        goal2.setImage(new Image(associatorPng2Card(String.valueOf(goals.get(1)),true)));

        //now i can link an ImageView to its cardId (only for the card)
        mapping.put(card1, cards.getFirst());
        mapping.put(card2, cards.get(1));
        mapping.put(card3, cards.get(2));
        mapping.put(card4, cards.get(3));
    }
}
