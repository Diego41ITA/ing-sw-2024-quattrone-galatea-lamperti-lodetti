package it.polimi.ingsw.model.card;

import java.util.*;

/**
 * @author Luca Lamperti
 * define a subClass InitialCard of superClass PlayableCard
 */
public class InitialCard extends PlayableCard {
    /**
     * @author Luca Lamperti
     * the constructor of the InitialCard class
     * @param type is the type of the card(from enum TypeOfCard)
     * @param isFront shows how the card is displayed
     * @param front is a HashMap with the angles(from enum Angle) as keys and items(from enum item) as values for the front face
     * @param back is a HashMap with the angles(from enum Angle) as keys and items(from enum item) as values for the back face
     * @param backResource is a ArrayList with the items in the box of the back face of the InitialCard
     */
    public InitialCard(boolean isFront, HashMap<Angle,Item> front,
                       HashMap<Angle,Item> back, List<Item> backResource)
    {
        super(TypeOfCard.STARTING, isFront, front, back, backResource);
    }

    /**
     * @author Lodetti Alessandro
     * this constructor is useful to initialize the object by reading from a json file
     * @param card it's the object read from the file
     */
    public InitialCard(InitialCard card){
        super(card.getType(), card.isFront(), card.getFront(), card.getBack(), card.getAListOfBackResource());
    }

    /**
     * @author Lodetti Alessandro
     * return a list of the resources on the back, initial card could have more than one resource.
     * @return a list of Item
     */
    public List<Item> getBackResources(){
        return this.getAListOfBackResource();
    }
}