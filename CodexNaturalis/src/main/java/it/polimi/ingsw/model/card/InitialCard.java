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
     * @param isFront indicates how the card is displayed
     * @param front is a HashMap with the angles(from enum Angle) as keys and items(from enum item) as values for the front face
     * @param back is a HashMap with the angles(from enum Angle) as keys and items(from enum item) as values for the back face
     * @param backResource is a ArrayList with the items in the box of the back face of the InitialCard
     */
    public InitialCard(TypeOfCard type, boolean isFront, HashMap<Angle,Item> front,
                       HashMap<Angle,Item> back, List<Item> backResource)
    {
        super(type, isFront, front, back, backResource);
    }

    public List<Item> getBackResources(){
        return this.getAListOfBackResource();
    }
}