/**
 * @author Luca Lamperti
 * define a class hierarchy with 'Card' as the superclass of 'InitialCard', 'GoalCard', 'GoldCard', and 'ResourceCard
 * isFront indicates the placement of the card (front face or back face)
 * type indicates the type of the card (using the enum TypeOfCard)
 */

package it.polimi.ingsw.card;

public class Card {
    protected Boolean isFront;
    protected TypeOfCard type;

    /**
     * @author Luca Lamperti
     * the constructor of the Card class
     * @param type specifies the type of the card
     */
    public Card(TypeOfCard type){
        isFront = true;
        this.type = type;
    }

    /**
     * @author Luca Lamperti
     * invert the attribute isFront
     */
    public void flip(){
        isFront = !isFront;
    }

    /**
     * @author Luca Lamperti
     * getter for the attribute isFront
     * @return Boolean to indicate the placement of the card (front face or back face)
     */
    public Boolean getIsFront(){ return isFront;}

    /**
     * @author Luca Lamperti
     * getter for the attribute TypeOfCard
     * @return TypeOfCard to indicate the type of the card
     */
    public TypeOfCard getType() {
        return type;
    }
}
