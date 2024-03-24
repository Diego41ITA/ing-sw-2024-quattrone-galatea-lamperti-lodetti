package it.polimi.ingsw.card;

import java.util.*;

public class Deck {
    private List<Card> cards;

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
}
