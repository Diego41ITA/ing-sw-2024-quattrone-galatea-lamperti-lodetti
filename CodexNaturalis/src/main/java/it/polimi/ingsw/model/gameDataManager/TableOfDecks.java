package it.polimi.ingsw.model.gameDataManager;

import it.polimi.ingsw.model.card.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/** @author Lorenzo Galatea
 * this class contains the decks and cards that
 * players can draw. It is shared by all players in the same game
 */
public class TableOfDecks {
    private ArrayList<Card> cards;

    private ArrayList<Card> goals;
    private Deck<GoldCard> deckGold;

    private Deck<GoalCard> deckGoal;

    private Deck<ResourceCard> deckResource;

    private Deck<InitialCard> deckStart;

    /**
     * default constructor
     */
    public TableOfDecks(){
        //crea semplicemente gli attributi
        //questi verranno settati in una fase iniziale.
    }



    /**@author Lorenzo Galatea
     * this method is the constructor of the class
      * @param deckGold: represents the gold deck
     * @param deckGoal: represents the goal deck
     * @param deckResource: represents the resource deck
     * @param cards: the 4 cards face up
     * @param goals:the 2 shared goals
     */
    public TableOfDecks(Deck<GoldCard> deckGold,Deck<GoalCard> deckGoal, Deck<ResourceCard> deckResource,
                        Deck<InitialCard> deckStart, ArrayList<Card> cards, ArrayList<Card> goals) {
        this.cards = new ArrayList<Card>(cards); //this one should be set later
        this.deckGold =  new Deck<>(deckGold);
        this.deckGoal = new Deck<>(deckGoal);
        this.deckResource = new Deck<>(deckResource);
        this.deckStart = new Deck<>(deckStart);
        this.goals = new ArrayList<Card>(goals); //this one should be set later too
    }

    /**
     * this second constructor is useful to upload a saved game
     * @param table it the status of the tableOfDecks when it was saved.
     */
    public TableOfDecks(TableOfDecks table){
        this.cards = new ArrayList<>(table.getCards());
        this.deckGoal = new Deck<>(table.getDeckGoal());
        this.deckGold = new Deck<>(table.getDeckGold());
        this.deckResource = new Deck<>(table.getDeckResource());
        this.deckStart = new Deck<>(table.getDeckStart());
        this.goals = new ArrayList<>(table.getGoals());
    }

    /** @author Lorenzo Galatea
     * is the setter of the Goals
     * @param deckGoal: represents the goal deck
     */
    public void setGoals(Deck<GoalCard> deckGoal){
            try {
                goals.add(deckGoal.getFirst());
                goals.add(deckGoal.getFirst());
            }catch(IllegalStateException e){
                goals.clear();
                System.out.println("there are not enough cards to set goals");
            }

    }

    /** @author Lorenzo Galatea
     * is the getter of the 2 goals
     * @return ArrayList which contains the 2 goals
     */
    public ArrayList<Card> getGoals(){
        return new ArrayList<Card>(goals);

    }

    /** @author Lorenzo Galatea
     *
     * @param newCard: the card I want to replace
     * it become null if there are no face-up cards that can be drawn by the players
     */
    public void setCards(Card newCard) {
        if (cards.isEmpty()) {
            try {
                cards.add(deckResource.getFirst());
                cards.add(deckResource.getFirst());
                cards.add(deckGold.getFirst());
                cards.add(deckGold.getFirst());
            } catch (IllegalStateException e) {
                cards.clear();
                System.out.println("some decks are empty");
            }
        } else {
            if (newCard != null) {
                if (cards.get(0) != null) {
                    if (newCard.equals(cards.getFirst())) {
                        try {
                            cards.set(0, deckResource.getFirst());
                        } catch (IllegalStateException e) {
                            cards.set(0, null);
                            return;
                        }
                    }
                }
                if (cards.get(1) != null) {
                    if (newCard.equals(cards.get(1))) {
                        try {
                            cards.set(1, deckResource.getFirst());
                        } catch (IllegalStateException e) {
                            cards.set(1, null);
                            return;
                        }
                    }
                }
                if (cards.get(2) != null) {
                    if (newCard.equals(cards.get(2))) {
                        try {
                            cards.set(2, deckGold.getFirst());
                        } catch (IllegalStateException e) {
                            cards.set(2, null);
                            return;
                        }
                    }
                }
                if (cards.get(3) != null) {
                    if (newCard.equals(cards.get(3))) {
                        try {
                            cards.set(3, deckGold.getFirst());
                        } catch (IllegalStateException e) {
                            cards.set(3, null);

                        }
                    }
                }
            }

        }
    }

    /**@author Lorenzo Galatea
     * the getter of the cards
     * @return ArrayList which contains the 4 showed cards
     */
    public ArrayList<Card> getCards(){
        return new ArrayList<Card>(this.cards);

    }

    /** @author Lorenzo Galatea
     * is the setter of deckResource
     * @param deckResource: the object that represents the deck
     */
    public void setDeckResource(Deck<ResourceCard> deckResource){
        this.deckResource = new Deck<>(deckResource);
    }

    /**@author Lorenzo Galatea
     * is the setter of deckGoal
     * @param deckGoal: the object that represents the deck
     */
    public void setDeckGoal(Deck<GoalCard> deckGoal){
        this.deckGoal = new Deck<>(deckGoal);

    }

    /**
     * @author Lodetti Alessandro
     * is the setter method for deckStart
     * @param deckStart: the object that represents the starting deck
     */
    public void setDeckStart(Deck<InitialCard> deckStart){
        this.deckStart = new Deck<>(deckStart);
    }

    /**@author Lorenzo Galatea
     * is the getter of deckResource
     * @return deckResource
     */
    public Deck<ResourceCard> getDeckResource(){
        return new Deck<>(deckResource);
    }

    /**@author Lorenzo Galatea
     * is the getter of deckGoal
     * @return deckGoal
     */
    public Deck<GoalCard> getDeckGoal(){
        return new Deck<>(deckGoal);
    }

    /**@author Lorenzo Galatea
     * is the getter of deckGold
     * @return deckGold
     */
    public Deck<GoldCard> getDeckGold(){
            return new Deck<>(deckGold);

    }

    /**
     * @author Lodetti Alessandro
     * is the getter method of deckStart
     * @return returns a Deck of starting cards.
     */
    public Deck<InitialCard> getDeckStart(){
        return new Deck<>(this.deckStart);
    }

    /**@author Lorenzo Galatea
     *  is the setter of deckGold
     * @param deckGold: the object that represents the deck
     */
    public void setDeckGold(Deck<GoldCard> deckGold){
        this.deckGold = new Deck<>(deckGold);

    }

    /**@author Lorenzo Galatea
     * deletes all cards in goals
     */
    public void resetGoals(){
        goals.clear();
    }

    /**@author Lorenzo Galatea
     * deletes all cards in cards
     */
    public void resetCards(){
        cards.clear();
    }

    /**
     * @author Lodetti Alessandro
     * this method shuffles all the decks.
     */
    public void shuffle(){
        this.deckGold.shuffle();
        this.deckGoal.shuffle();
        this.deckResource.shuffle();
        this.deckStart.shuffle();
    }

    public void initializeTable(){
        //allCards = loadCardFromJSON(fileName)
        //Collections.shuffle(allCards)
        //for (int i = 0; i < allCards.size(); i++) {
        //    if (i <= 51) {
        //         this.deckResource.add(new Card(allCards.get(i))); // idk if we need a deep copy here
        //    } else if (i <= 103) {
        //         this.deckGold.add(new Card(allCards.get(i))); // same
        //            }
        //      else if (i <= 109) {
        //         this.deckStart.add((new Card(allCards.get(i))); // same
        //            }
        //      else {
        //          this.deckGoal.add((new Card(allCards.get(i))); // same
        //            }
        //}
    }
}
