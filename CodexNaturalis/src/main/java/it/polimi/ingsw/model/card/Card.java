package it.polimi.ingsw.model.card;

import static java.lang.Math.floor;

/**
 * @author Luca Lamperti
 * define a class hierarchy with 'Card' as the superclass of 'InitialCard', 'GoalCard', 'GoldCard', and 'ResourceCard
 * isFront indicates the placement of the card (front face or back face)
 * type indicates the type of the card (using the enum TypeOfCard)
 */
public abstract class Card {
    private final int cardId;
    private boolean isFront;
    private final TypeOfCard type;

    /**
     * @author Luca Lamperti
     * the constructor of the Card class
     * @param cardId specifies the id of the card
     * @param type specifies the type of the card
     * @param isFront specifies which side of the card you can see
     */
    Card(int cardId, TypeOfCard type, boolean isFront){
        this.cardId = cardId;
        this.isFront = isFront;
        this.type = type;
    }

    /**
     * @author Luca Lamperti
     * invert the attribute isFront
     */
    public void flip(){
        this.isFront = !isFront;
    }

    /**
     * @author Luca Lamperti
     * getter for the attribute isFront
     * @return Boolean to indicate the placement of the card (front face or back face)
     */
    public boolean isFront(){ return this.isFront;}

    /**
     * @author Luca Lamperti
     * getter for the attribute TypeOfCard
     * @return TypeOfCard to indicate the type of the card
     */
    public TypeOfCard getType() {
        return type;
    }

    /**
     * this method return the key-value of the card
     * @return a copy of the attribute cardId
     */
    public int getCardId(){
        return this.cardId;
    }

    @Override
    public int hashCode(){
        return (this.getCardId() * 17) / 31;
    }

    @Override
    public boolean equals(Object card){
        try{
            Card newCard = (Card) card;
            if(this.getCardId() == newCard.getCardId())
                return true;
            else
                return false;

        }catch(ClassCastException e){
            return false;
        }
    }
}
