/**
 * this class defines the deck structure as a collection of cards
 * @author Lodetti Alessandro
 */
package it.polimi.ingsw.card;

import java.util.*;

public class Deck {
    private List<Card> cards;

    /**
     * this is the only constructor
     * @param deck it is simply the card collection.
     */
    public Deck(List<Card> deck){
        this.cards = new ArrayList<>(deck);
    }

    /**
     * in this implementation the methods removes the last card, in this way it is faster.
     * @return and REMOVES the first card
     */
    public Card getFirst() throws NoMoreCardEcxeption{
        int index = this.getDimension() - 1;
        if(index > 0)
            return cards.remove(index); //return the last card
        else
            throw new NoMoreCardEcxeption ("the deck is empty");
    }

    public void shuffle(){
        Collections.shuffle(cards);
    }

    public int getDimension(){
        return cards.size();
    }

    public boolean isEmpty(){
            return cards.isEmpty();
    }

    /**
     * this method is useful when you want to save the match because it returns the update deck.
     * @return a copy of the deck.
     */
    public List<Card> getStatus(){
        return new ArrayList<>(cards);
    }
}
