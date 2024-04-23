package it.polimi.ingsw.model.gameDataManager;

import it.polimi.ingsw.model.card.*;
import it.polimi.ingsw.model.exceptions.illegalOperationException;
import it.polimi.ingsw.parse.DeckReader;

import java.util.ArrayList;
import java.util.List;

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
        //just create the attributes
        //these will be set at an early stage.
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
    public List<Card> getGoals() {
        if (goals != null) {
            return new ArrayList<>(goals);
        } else {
            return new ArrayList<>();
        }
    }

    /** @author Lorenzo Galatea
     *
     * @param newCard: the card I want to replace
     * it become null if there are no face-up cards that can be drawn by the players
     */
    public void setCards(Card newCard) throws IllegalStateException{
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
        }else {
            if (newCard != null) {
                if (cards.get(0) != null) {
                    if (newCard.equals(cards.get(0))) {
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
            }else{
                throw new IllegalArgumentException("newcard is not available on the tablea");
            }
        }
    }

    /**@author Lorenzo Galatea
     * the getter of the cards
     * @return ArrayList which contains the 4 showed cards
     */
    public ArrayList<Card> getCards(){
        if (this.cards == null) {
            this.cards = new ArrayList<>();
        }
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
        if (deckResource != null) {
            return new Deck<>(deckResource);
        } else {
            return new Deck<>(new ArrayList<>());
        }
    }

    /**@author Lorenzo Galatea
     * is the getter of deckGoal
     * @return deckGoal
     */
    public Deck<GoalCard> getDeckGoal() {
        if (deckGoal != null) {
            return new Deck<>(deckGoal);
        } else {
            return new Deck<>(new ArrayList<>());
        }
    }
    /**@author Lorenzo Galatea
     * is the getter of deckGold
     * @return deckGold
     */
    public Deck<GoldCard> getDeckGold(){
        if (deckGold != null) {
            return new Deck<>(deckGold);
        } else {
            return new Deck<>(new ArrayList<>());
        }
    }

    /**
     * @author Lodetti Alessandro
     * is the getter method of deckStart
     * @return returns a Deck of starting cards.
     */
    public Deck<InitialCard> getDeckStart(){
        if (deckStart != null) {
            return new Deck<>(deckStart);
        } else {
            return new Deck<>(new ArrayList<>());
        }
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

    /**
     * Initialize the table, needs to be called at the beginning of a new game of CodexNaturalis.
     * It reads the cards from JSON files, populating the decks and the cards initially displayed on the field.
     */
    public void initializeTable(){
        DeckReader<ResourceCard> resourceCardReader = new DeckReader<>(ResourceCard.class);
        DeckReader<GoldCard> goldCardReader = new DeckReader<>(GoldCard.class);
        DeckReader<InitialCard> initialCardReader = new DeckReader<>(InitialCard.class);
        //DeckReader<GoalCard> goalCardReader = new DeckReader<>(GoalCard.class);

        this.deckResource = resourceCardReader.readDeckFromJSON("src/main/resources/JsonCards/resourceCard.json");
        this.deckStart = initialCardReader.readDeckFromJSON("src/main/resources/JsonCards/initialCard.json");
        this.deckGold = goldCardReader.readDeckFromJSON("src/main/resources/JsonCards/goldCard.json");
        //this.deckGoal = goalCardReader.readDeckFromJSON("/goalCard.json");

        shuffle();

        this.goals = new ArrayList<>();
        this.cards = new ArrayList<>();
        this.setCards(null);
        //this.setGoals(this.deckGoal);
    }
}
