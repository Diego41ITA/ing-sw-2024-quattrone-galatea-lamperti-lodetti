package it.polimi.ingsw.model.gameDataManager;


import it.polimi.ingsw.model.card.Card;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/** @author Lorenzo Galatea
 *
 */
public class TableOfDecks {
    private ArrayList<Card> cards;

    private ArrayList<Card> goals;
    private Deck deckGold;

    private Deck deckGoal;

    private Deck deckResource;

    public TableOfDecks(Deck deckGold,Deck deckGoal, Deck deckResource, ArrayList<Card> cards, ArrayList<Card> goals) {
        this.cards = new ArrayList<Card>(cards);
        this.deckGold = deckGold;
        this.deckGoal = deckGoal;
        this.deckResource = deckResource;
        this.goals = new ArrayList<Card>(goals);
    }

    public setgoals(Deck Deckgoal){

            goals.add(deckGoal.getFirst());
            goals.add(deckGoal.getFirst());

    }

    public ArrayList<Card> getgoals(){
        return new ArrayList<Card>(goals);

    }

    public setcards(Deck deckResource, Deck deckGold, Card drawedCard){//lasciare vedere regole
        if (cards.isEmpty()) {
            cards.add(deckResource.getFirst());
            cards.add(deckResource.getFirst());
            cards.add(deckGold.getFirst());
            cards.add(deckGold.getFirst());

        } else if (newCard != null) {
            if (drawedCard.getType().equals(CardType.RESOURCE)) {
                cards.add(deckResource.getFirst());
            } else if (newCard.getType().equals(CardType.GOLD)) {
                cards.add(deckGold.getFirst());
            }
        }
    }


    public ArrayList<Card> getcards(){
        return new ArrayList<Card>(this.cards)

    }

    public setDeckResource(Deck deckResource){
        this.deckResource = deckResource;
    }

    public setDeckGoal(Deck deckGoal){
        this.deckGoal = deckGoal;

    }

    public Deck getDeckResource(){
        return this.deckResource;
    }
    public Deck getDeckgoal(){
        return this.deckGoal;
    }

    public Deck getDeckGold(){
            return this.deckGold;

    }
    public void setDeckGold(Deck deckGold){
        this.deckGold = deckGold;

    }

    public void restGoals(){
        goals.clear();
    }
    public void resetCards(){
        cards.clear();
    }
}
