package it.polimi.ingsw.model.card;

import java.io.Serializable;
import java.util.*;

/**
 * this class defines the deck structure as a collection of cards
 * @author Lodetti Alessandro
 */
public class Deck <T extends Card> implements Serializable {
    private final List<T> cards;

    /**
     * this is the basic constructor
     * @param deck is simply the card collection.
     */
    public Deck(List<T> deck){
        this.cards = new ArrayList<T>(deck);
    }

    /**
     * this is the advanced constructor: it is useful to initialize gameDataManager objects
     * @param d is a deck well-formed.
     */
    public Deck(Deck<T> d){
        this.cards = new ArrayList<T>(d.getStatus());
    }

    /**
     * in this implementation the methods removes the last card, in this way it is faster.
     * @return and REMOVES the first card (which is the last in the list)
     */
    public T getFirst() throws IllegalStateException{
        int index = this.getDimension();
        if(index > 0) {
            return cards.remove(index - 1); //return the last card
        }
        else
            throw new IllegalStateException ("the deck is empty");
    }

    /**
     * this method is used to shuffle the deck randomly
     */
    public void shuffle(){
        Collections.shuffle(cards);
    }

    /**
     * this method return the size of the current state of attribute deck
     * @return a primitive type which correspond to the deck size.
     */
    public int getDimension(){
        return cards.size();
    }

    /**
     * it checks the deck emptiness
     * @return true only if the deck is actually empty, false otherwise.
     */
    public boolean isEmpty(){
            return cards.isEmpty();
    }

    /**
     * this method is useful when you want to save the match because it returns the update deck.
     * @return a copy of the deck.
     */
    public List<T> getStatus(){
        return new ArrayList<T>(cards);
    }
}
