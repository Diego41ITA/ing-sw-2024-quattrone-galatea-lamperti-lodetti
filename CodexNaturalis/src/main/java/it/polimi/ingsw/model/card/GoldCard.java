/**
 * It defines how GoldCards are implemented
 * @author Lodetti Alessandro
 */
package it.polimi.ingsw.model.card;

import java.util.*;
public class GoldCard extends Card{
    private Map<Angle, Item> front;
    private Map<Angle, Item> back;
    private final List<Item> neededResources;
    private final int numOfPoints;
    private final Item item;
    private final TypeOfGoldCard typeOfGoldCard;
    private final Item backResource;

    /**
     * it is the only constructor for this class
     * @param front this map links the position of an angle with its contents (for the front).
     * @param back same but for the back.
     * @param points these are the points that a player can earn from this card.
     * @param backResource defines the permanent resource in the back.
     * @param item  it is the item that a player needs to earn points.
     * @param typeGold  indicates one of the type that a gold card can assume.
     * @param resources indicates the resource that a player need to place this card.
     * @param typeCard  refer to the super class.
     */
    public GoldCard(Map<Angle, Item> front, Map<Angle, Item> back, int points,Item backResource, Item item, TypeOfGoldCard typeGold, List<Item> resources, TypeOfCard typeCard)
    {
        super(typeCard);
        this.front = new HashMap<>(front);
        this.back = new HashMap<>(back);
        this.numOfPoints = points;
        this.typeOfGoldCard = typeGold;
        this.backResource = backResource;
        this.item = item;
        this.neededResources = new ArrayList<>(resources);
    }

    public int getNumOfPoints() {
        return numOfPoints;
    }
    public Item getBackResource() {
        return backResource;
    }
    public TypeOfGoldCard getTypeOfGoldCard() {
        return typeOfGoldCard;
    }
    public List<Item> getNeededResources(){
        return new ArrayList<>(neededResources);
    }
    public Item getItem(){
        return item;
    }

    public List<Item> getFreeItem(){
        //returns free item in the corner.
    }


}
