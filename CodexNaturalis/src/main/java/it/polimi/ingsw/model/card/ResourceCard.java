/**
 * In this class I define how ResourceCard is structured.
 * The class has only one constructor which defines a well-formed card. It has also other methods which allow to
 * implement other classes easily; like getFreeCorner() for GameStation.
 * @author Lodetti Alessandro
 */
package it.polimi.ingsw.model.card;

import java.util.*;

public class ResourceCard extends Card {
    private final Map<Angle, Item> front;
    private final Map<Angle, Item> back;
    private final Item backResource;
    private final int numOfPoints;

    /**
     * this is the only constructor
     *
     * @param front        this map links the position of an angle and its item (for the front of the card)
     * @param back         same as front but for the back of the card
     * @param backResource defines the resource stored in the back of the card
     * @param type         refer to the super class attribute.
     */
    public ResourceCard(Map<Angle, Item> front, Map<Angle, Item> back, Item backResource, TypeOfCard type) {
        super(type);
        this.front = new HashMap<>(front);
        this.back = new HashMap<>(back);
        this.numOfPoints = 1;
        this.backResource = backResource;
    }

    public int getNumberOfPoints() {
        return numOfPoints;
    }

    public Item getBackResource() {
        return backResource;
    }

    /**
     * hide the specified angle: useful when you place a card.
     * @return true if the corner is successfully hidden.
     */
    public boolean hideAngle(Angle angle) {
        if (isFront) {
            if (!front.containsKey(angle) || front.get(angle) == Item.HIDDEN)
                return false;
            front.replace(angle, Item.HIDDEN);
            return true;
        } else {
            if (!back.containsKey(angle) || back.get(angle) == Item.HIDDEN)
                return false;
            back.replace(angle, Item.HIDDEN);
            return true;
        }
    }

    /**
     * this method return all the free angles and their contents.
     * it could throw a NoFreeAngleException.
     * @return only a map.
     */
    public Map<Angle, Item> getFreeAngleAndContents() {
        if(isFront) {
            Map<Angle, Item> newMap = new HashMap<>(front);
            for (Angle a : newMap.keySet())
                if (newMap.get(a) == Item.HIDDEN)
                    newMap.remove(a);
            return newMap;
        }
        else {
            Map<Angle, Item> newMap = new HashMap<>(back);
            for (Angle a : newMap.keySet())
                if (newMap.get(a) == Item.HIDDEN)
                    newMap.remove(a);
            return newMap;
        }
    }

    /**
     * using getFreeAngleAndContents() methods, this one provides e list of free angles.
     * @return an ArrayList of all the free Angles.
     */
    public List<Angle> getFreeAngle(){
        return new ArrayList<>(this.getFreeAngleAndContents().keySet());
    }

    /**
     * @return the list of available items in the map.
     */
    public List<Item> getFreeItem(){
        Map<Angle, Item> m = new HashMap<>(this.getFreeAngleAndContents());
        List<Item> l = new ArrayList<>();
        //it could be done with functional programming.
        for(Angle a: m.keySet())
            l.add(m.get(a));
        return l;
    }
}