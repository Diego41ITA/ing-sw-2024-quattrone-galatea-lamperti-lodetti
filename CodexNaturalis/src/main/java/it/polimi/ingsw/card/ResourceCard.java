/**
 * In this class I define how ResourceCard is structured.
 * The class has only one constructor which defines a well-formed card. It has also other methods which allow to
 * implement other classes easily; like getFreeCorner() for GameStation.
 * @author Lodetti Alessandro
 */
package it.polimi.ingsw.card;

import java.util.*;
public class ResourceCard extends Card {
    private Map<Angle, Item> front;
    private Map<Angle, Item> back;
    private final Item backResource;
    private final int numOfPoints;

    /**
     * this is the only constructor
     * @param front this map links the position of an angle and its item (for the front of the card)
     * @param back  same as front but for the back of the card
     * @param backResource  defines the resource stored in the back of the card
     * @param type refer to the super class attribute.
     */
    public ResourceCard(Map<Angle, Item> front, Map<Angle, Item> back, Item backResource, TypeOfCard type){
        super(type);
        this.front = new HashMap<>(front);
        this.back = new HashMap<>(back);
        this.numOfPoints = 1;
        this.backResource = backResource;
    }

    public int getNumberOfPoints(){
        return numOfPoints;
    }
    public Item getBackResource(){
        return backResource;
    }

}
