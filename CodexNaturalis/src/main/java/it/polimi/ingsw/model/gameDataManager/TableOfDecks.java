package it.polimi.ingsw.model.gameDataManager;

import it.polimi.ingsw.model.card.Card;
import it.polimi.ingsw.model.card.Deck;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/** @author Lorenzo Galatea
 * this class contains the decks and cards that
 * players can draw. It is shared by all players in the same game
 */
public class TableOfDecks {
    private ArrayList<Card> cards;

    private ArrayList<Card> goals;
    private Deck deckGold;

    private Deck deckGoal;

    private Deck deckResource;

    /**@author Lorenzo Galatea
     * this method is the constructor of the class
      * @param deckGold: represents the gold deck
     * @param deckGoal: represents the goal deck
     * @param deckResource: represents the resource deck
     * @param cards: the 4 cards face up
     * @param goals:the 2 shared goals
     */
    public TableOfDecks(Deck deckGold,Deck deckGoal, Deck deckResource, ArrayList<Card> cards, ArrayList<Card> goals) {
        this.cards = new ArrayList<Card>(cards);
        this.deckGold =  new Deck((List<Card>) deckGold);
        this.deckGoal = new Deck((List<Card>) deckGoal);
        this.deckResource = new Deck((List<Card>) deckResource);
        this.goals = new ArrayList<Card>(goals);
    }

    /** @author Lorenzo Galatea
     * is the setter of the Goals
     * @param deckGoal: represents the goal deck
     */
    public void setGoals(Deck deckGoal){
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
    public void setDeckResource(Deck deckResource){
        this.deckResource = deckResource;
    }

    /**@author Lorenzo Galatea
     * is the setter of deckGoal
     * @param deckGoal: the object that represents the deck
     */
    public void setDeckGoal(Deck deckGoal){
        this.deckGoal = deckGoal;

    }

    /**@author Lorenzo Galatea
     * is the getter of deckResource
     * @return deckResource
     */
    public Deck getDeckResource(){
        return new Deck((List<Card>) deckResource);
    }

    /**@author Lorenzo Galatea
     * is the getter of deckGoal
     * @return deckGoal
     */
    public Deck getDeckGoal(){
        return new Deck((List<Card>) deckGoal);
    }

    /**@author Lorenzo Galatea
     * is the getter of deckGold
     * @return deckGold
     */
    public Deck getDeckGold(){
            return new Deck((List<Card>) deckGold);

    }

    /**@author Lorenzo Galatea
     *  is the setter of deckGold
     * @param deckGold: the object that represents the deck
     */
    public void setDeckGold(Deck deckGold){
        this.deckGold = deckGold;

    }

    /**@author Lorenzo Galatea
     * deletes all cards in goals
     */
    public void restGoals(){
        goals.clear();
    }

    /**@author Lorenzo Galatea
     * deletes all cards in cards
     */
    public void resetCards(){
        cards.clear();
    }
}
