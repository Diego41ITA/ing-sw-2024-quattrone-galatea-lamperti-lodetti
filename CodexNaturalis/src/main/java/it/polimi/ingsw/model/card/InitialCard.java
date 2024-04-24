package it.polimi.ingsw.model.card;

import java.util.*;

/**
 * define a subClass InitialCard of superClass PlayableCard
 * @author Luca Lamperti
 */
public class InitialCard extends PlayableCard {
    /**
     * the constructor of the InitialCard class
     * @author Luca Lamperti
     * @param id defines an identification for the card
     * @param isFront shows how the card is displayed
     * @param front is a HashMap with the angles(from enum Angle) as keys and items(from enum item) as values for the front face
     * @param back is a HashMap with the angles(from enum Angle) as keys and items(from enum item) as values for the back face
     * @param backResource is a ArrayList with the items in the box of the back face of the InitialCard
     */
    public InitialCard(int id, boolean isFront, HashMap<Angle,Item> front,
                       HashMap<Angle,Item> back, List<Item> backResource)
    {
        super(id, TypeOfCard.STARTING, isFront, front, back, backResource);
    }

    /**
     * this constructor is useful to initialize the object by reading from a json file
     * @author Luca Lamperti
     * @param card it's the object read from the file
     */
    public InitialCard(InitialCard card){
        super(card.getCardId(), card.getType(), card.isFront(), card.getFront(), card.getBack(), card.getAListOfBackResource());
    }

    /**
     * return a list of the resources on the back, initial card could have more than one resource.
     * @author Luca Lamperti
     * @return a list of Item
     */
    public List<Item> getBackResources(){
        return this.getAListOfBackResource();
    }
}