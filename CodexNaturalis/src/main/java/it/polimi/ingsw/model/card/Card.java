package it.polimi.ingsw.model.card;

/**
 * @author Luca Lamperti
 * define a class hierarchy with 'Card' as the superclass of 'InitialCard', 'GoalCard', 'GoldCard', and 'ResourceCard
 * isFront indicates the placement of the card (front face or back face)
 * type indicates the type of the card (using the enum TypeOfCard)
 */
public abstract class Card {
    private boolean isFront;
    private final TypeOfCard type;

    /**
     * @author Luca Lamperti
     * the constructor of the Card class
     * @param type specifies the type of the card
     */
    public Card(TypeOfCard type, boolean isFront){
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
}
