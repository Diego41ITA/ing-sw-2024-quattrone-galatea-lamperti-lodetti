package it.polimi.ingsw.model.gameDataManager;

import it.polimi.ingsw.model.card.*;
import it.polimi.ingsw.model.exceptions.illegalOperationException;
import it.polimi.ingsw.parse.DeckReader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * this class contains the decks and cards that
 * players can draw. It is shared by all players in the same game
 * @author Lorenzo Galatea
 */
public class TableOfDecks implements Serializable {
    private ArrayList<Card> cards;

    private ArrayList<GoalCard> goals;
    private Deck<GoldCard> deckGold;

    private Deck<GoalCard> deckGoal;

    private Deck<ResourceCard> deckResource;

    private Deck<InitialCard> deckStart;

    /**
     * default constructor
     */
    public TableOfDecks(){
        this.cards = new ArrayList<Card>(); //this one should be set later
        this.goals = new ArrayList<GoalCard>();
    }

    /**
     * this method is the constructor of the class
     * @author Lorenzo Galatea
     * @param deckGold: represents the gold deck
     * @param deckGoal: represents the goal deck
     * @param deckResource: represents the resource deck
     * @param cards: the 4 cards face up
     * @param goals:the 2 shared goals
     */
    public TableOfDecks(Deck<GoldCard> deckGold,Deck<GoalCard> deckGoal, Deck<ResourceCard> deckResource,
                        Deck<InitialCard> deckStart, ArrayList<Card> cards, ArrayList<GoalCard> goals) {
        this.cards = new ArrayList<Card>(cards); //this one should be set later
        this.deckGold =  new Deck<>(deckGold);
        this.deckGoal = new Deck<>(deckGoal);
        this.deckResource = new Deck<>(deckResource);
        this.deckStart = new Deck<>(deckStart);
        this.goals = new ArrayList<GoalCard>(goals); //this one should be set later too
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

    /**
     * is the setter of the Goals
     * @author Lorenzo Galatea
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

    /**
     * is the getter of the 2 goals
     * @author Lorenzo Galatea
     * @return ArrayList which contains the 2 goals
     */
    public List<GoalCard> getGoals() {
        if (goals != null) {
            return new ArrayList<GoalCard>(goals);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * it becomes null if there are no face-up cards that can be drawn by the players
     * @author Lorenzo Galatea
     * @param newCard: the card I want to replace
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
            }else{
                throw new IllegalArgumentException("newcard is not available on the tablea");
            }
        }
    }

    /**
     * the getter of the cards
     * @author Lorenzo Galatea
     * @return ArrayList which contains the 4 showed cards
     */
    public ArrayList<Card> getCards(){
        if (this.cards == null) {
            this.cards = new ArrayList<>();
        }
        return new ArrayList<Card>(this.cards);

    }

    /**
     * is the setter of deckResource
     * @author Lorenzo Galatea
     * @param deckResource: the object that represents the deck
     */
    public void setDeckResource(Deck<ResourceCard> deckResource){
        this.deckResource = new Deck<>(deckResource);
    }

    /**
     * is the setter of deckGoal
     * @author Lorenzo Galatea
     * @param deckGoal: the object that represents the deck
     */
    public void setDeckGoal(Deck<GoalCard> deckGoal){
        this.deckGoal = new Deck<>(deckGoal);

    }

    /**
     * is the setter method for deckStart
     * @author Lodetti Alessandro
     * @param deckStart: the object that represents the starting deck
     */
    public void setDeckStart(Deck<InitialCard> deckStart){
        this.deckStart = new Deck<>(deckStart);
    }

    /**
     * is the getter of deckResource
     * @author Lorenzo Galatea
     * @return deckResource
     */
    public Deck<ResourceCard> getDeckResource(){
        if (deckResource != null) {
            return new Deck<>(deckResource);
        } else {
            return new Deck<>(new ArrayList<>());
        }
    }

    /**
     * is the getter of deckGoal
     * @author Lorenzo Galatea
     * @return deckGoal
     */
    public Deck<GoalCard> getDeckGoal() {
        if (deckGoal != null) {
            return new Deck<>(deckGoal);
        } else {
            return new Deck<>(new ArrayList<>());
        }
    }
    /**
     * is the getter of deckGold
     * @author Lorenzo Galatea
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
     * is the getter method of deckStart
     * @author Lodetti Alessandro
     * @return returns a Deck of starting cards.
     */
    public Deck<InitialCard> getDeckStart(){
        if (deckStart != null) {
            return new Deck<>(deckStart);
        } else {
            return new Deck<>(new ArrayList<>());
        }
    }

    /**
     * is the setter of deckGold
     * @author Lorenzo Galatea
     * @param deckGold: the object that represents the deck
     */
    public void setDeckGold(Deck<GoldCard> deckGold){
        this.deckGold = new Deck<>(deckGold);

    }

    /**
     * deletes all cards in goals
     * @author Lorenzo Galatea
     */
    public void resetGoals(){
        goals.clear();
    }

    /**
     * deletes all cards in cards
     * @author Lorenzo Galatea
     */
    public void resetCards(){
        cards.clear();
    }

    /**
     * this method shuffles all the decks.
     * @author Lodetti Alessandro
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
     * @author Diego Quattrone
     */
    public void initializeTable(){
        DeckReader<ResourceCard> resourceCardReader = new DeckReader<>(ResourceCard.class);
        DeckReader<GoldCard> goldCardReader = new DeckReader<>(GoldCard.class);
        DeckReader<InitialCard> initialCardReader = new DeckReader<>(InitialCard.class);
        DeckReader<GoalCard> goalCardReader = new DeckReader<>(GoalCard.class);

        this.deckResource = resourceCardReader.readDeckFromJSON("C:\\Users\\Utente\\Desktop\\scuola\\Università\\Lezioni\\Anno 3\\ingegneria del software\\Programmi\\ing-sw-2024-quattrone-galatea-lamperti-lodetti\\CodexNaturalis\\src\\main\\resources\\JsonCards\\resourceCard.json");
        this.deckStart = initialCardReader.readDeckFromJSON("C:\\Users\\Utente\\Desktop\\scuola\\Università\\Lezioni\\Anno 3\\ingegneria del software\\Programmi\\ing-sw-2024-quattrone-galatea-lamperti-lodetti\\CodexNaturalis\\src\\main\\resources\\JsonCards\\initialCard.json");
        this.deckGold = goldCardReader.readDeckFromJSON("C:\\Users\\Utente\\Desktop\\scuola\\Università\\Lezioni\\Anno 3\\ingegneria del software\\Programmi\\ing-sw-2024-quattrone-galatea-lamperti-lodetti\\CodexNaturalis\\src\\main\\resources\\JsonCards\\goldCard.json");
        this.deckGoal = goalCardReader.readDeckFromJSON("C:\\Users\\Utente\\Desktop\\scuola\\Università\\Lezioni\\Anno 3\\ingegneria del software\\Programmi\\ing-sw-2024-quattrone-galatea-lamperti-lodetti\\CodexNaturalis\\src\\main\\resources\\JsonCards\\goalCard.json");


//        this.deckResource = resourceCardReader.readDeckFromJSON("src/main/resources/JsonCards/resourceCard.json");
//        this.deckStart = initialCardReader.readDeckFromJSON("src/main/resources/JsonCards/initialCard.json");
//        this.deckGold = goldCardReader.readDeckFromJSON("src/main/resources/JsonCards/goldCard.json");
//        this.deckGoal = goalCardReader.readDeckFromJSON("src/main/resources/JsonCards/goalCard.json");

        shuffle();

        this.goals = new ArrayList<>();
        this.cards = new ArrayList<>();
        this.setCards(null);
        this.setGoals(this.deckGoal);
    }
}
