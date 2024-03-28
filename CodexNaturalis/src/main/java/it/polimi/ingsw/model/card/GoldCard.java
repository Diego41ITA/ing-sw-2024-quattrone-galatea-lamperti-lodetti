/**
 * It defines how GoldCards are implemented
 * @author Lodetti Alessandro
 */
package it.polimi.ingsw.model.card;

import java.util.*;

/**
 * @author Lodetti Alessandro
 * It defines how GoldCards are implemented
 */
public class GoldCard extends PlayableCard{

    private final List<Item> neededResources;
    private final int numOfPoints;
    private final Item box;
    private final TypeOfGoldCard typeOfGoldCard;


    /**
     * it is the only constructor for this class
     * @param front this map links the position of an angle with its contents (for the front).
     * @param back same but for the back.
     * @param points these are the points that a player can earn from this card.
     * @param backResource defines the permanent resource in the back.
     * @param box  it is the item that a player needs to earn points.
     * @param typeGold  indicates one of the type that a gold card can assume.
     * @param resources indicates the resource that a player need to place this card.
     * @param typeCard  refer to the super class.
     */
    public GoldCard(Map<Angle, Item> front, Map<Angle, Item> back, int points, List<Item> backResource, Item box,
                    TypeOfGoldCard typeGold, List<Item> resources, TypeOfCard typeCard, boolean isFront)
    {
        super(typeCard, isFront, front, back, backResource);
        this.numOfPoints = points;
        this.typeOfGoldCard = typeGold;
        this.box = box;
        this.neededResources = new ArrayList<>(resources);
    }

    /**
     * the method return the number of point that this card gives
     * @return the value of "GoldCard.numOfPoints"
     */
    public int getNumOfPoints() {
        return numOfPoints;
    }

    /**
     * it's for knowing how to make points with this card.
     * @return the type of this particular GoldCard
     */
    public TypeOfGoldCard getTypeOfGoldCard() {
        return typeOfGoldCard;
    }

    /**
     * Indicates the resources that the player should have in order to place this card
     * @return a list of item.
     */
    public List<Item> getNeededResources(){
        return new ArrayList<>(neededResources);
    }

    /**
     * Return the item in the box
     * @return an item
     */
    public Item getBox(){
        return box;
    }

    /**
     * "redefine" the super method in order to return only an item
     * @return an item not a list
     */
    public Item getBackResource(){
        return this.getAListOfBackResource().getFirst();
    }

}
