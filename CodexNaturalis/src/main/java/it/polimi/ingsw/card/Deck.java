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

    public Card getFirst(){
        int index = this.getDimension() - 1;
        if(index > 0)
            return cards.remove(index); //return the last card
        else
            //---------
            //throws an exception
            return null;
            //ATTENTION
            //---------
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
    //this method is useful when you want to save the match
    public List<Card> getStatus(){
        return new ArrayList<>(cards);
    }
}
