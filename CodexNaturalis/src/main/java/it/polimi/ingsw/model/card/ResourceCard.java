package it.polimi.ingsw.model.card;

import java.util.*;

/**
 * @author Lodetti Alessandro
 * In this class I define how ResourceCard is structured.
 * The class has only one constructor which defines a well-formed card. It has also other methods which allow to
 * implement other classes easily; like getFreeCorner() for GameStation.
 */
public class ResourceCard extends PlayableCard {
    private final int numOfPoints;

    /**
     * this is the only constructor
     *
     * @param front        this map links the position of an angle and its item (for the front of the card)
     * @param back         same as front but for the back of the card
     * @param backResource defines the resource stored in the back of the card. IT IS A LIST.
     * @param type         refer to the super class attribute.
     * @param isFront      refer to the super class attribute.
     * @param id           refer to the super class attribute.
     */
    public ResourceCard(Map<Angle, Item> front, Map<Angle, Item> back,
                        List<Item> backResource, TypeOfCard type, boolean isFront, int id)
    {
        super(id, type, isFront, front, back, backResource);
        this.numOfPoints = 1;
    }


    /**
     * this constructor is useful to initialize the game by reading from a json file or other file.
     * @param card it is the object read from the file
     */
    public ResourceCard(ResourceCard card){
        super(card.getCardId(), card.getType(), card.isFront(), card.getFront(), card.getBack(), card.getAListOfBackResource());
        this.numOfPoints = card.getNumberOfPoints();
    }

    /**
     * return the number of point
     * @return (1) returns a variable but it always values 1.
     */
    public int getNumberOfPoints() {
        return numOfPoints;
    }

    /**
     * "Redefine" how the backResource is returned.
     * @return a single Item not a list
     */
    public Item getBackResource(){
        return this.getAListOfBackResource().getFirst();
    }
}