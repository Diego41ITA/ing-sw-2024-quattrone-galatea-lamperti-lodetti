/**
 * It defines how GoldCards are implemented
 * @author Lodetti Alessandro
 */
package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.gameDataManager.GameStation;
import it.polimi.ingsw.model.card.strategyPattern.CheckInterface;
import java.util.*;
import java.util.List;

/**
 * @author Lodetti Alessandro
 * It defines how GoldCards are implemented
 */
public class GoldCard extends PlayableCard{

    private final HashMap<Item, Integer> resources;;
    private final int numOfPoints;
    private final Item box;
    private final CheckInterface goldType;


    /**
     * it is the only constructor for this class
     * @param front this map links the position of an angle with its contents (for the front).
     * @param back same but for the back.
     * @param points these are the points that a player can earn from this card.
     * @param backResource defines the permanent resource in the back.
     * @param box  it is the item that a player needs to earn points.
     * @param goldType indicates one of the type that a gold card can assume.
     * @param resources indicates the resource that a player need to place this card.
     * @param typeCard  refer to the super class attribute
     * @param isFront refer to the super class attribute
     */
    public GoldCard(Map<Angle, Item> front, Map<Angle, Item> back, int points, List<Item> backResource, Item box,
                    CheckInterface goldType, HashMap<Item, Integer> resources, TypeOfCard typeCard, boolean isFront)
    {
        super(TypeOfCard.GOLD, isFront, front, back, backResource);
        this.numOfPoints = points;
        this.goldType = goldType;
        this.box = box;
        this.resources = new HashMap<>(resources);
    }

    /**
     * this constructor is useful to initialize the object by reading from a json file
     * @param card it's the object read from the file
     */
    public GoldCard(GoldCard card){
        super(card.getType(), card.isFront(), card.getFront(), card.getBack(), card.getAListOfBackResource());
        this.numOfPoints = card.getNumberOfPoints();
        this.resources = new HashMap<>(card.getNeededResources());
        this.box = card.getBox();
        this.goldType= card.getGoldType();
    }


    /**
     * the method return the number of point that this card gives
     * @return the value of "GoldCard.numOfPoints"
     */
    public int getNumberOfPoints() {
        return numOfPoints;
    }

    /**
     * it's for knowing how to make points with this card.
     *
     * @return the type of this particular GoldCard
     */
    public CheckInterface getGoldType() {
        return goldType;
    }

    /**
     * Indicates the resources that the player should have in order to place this card
     * @return a list of item.
     */
    public HashMap<Item, Integer> getNeededResources(){
         return new HashMap<>(resources);
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

    @Override
    public boolean verifyResources(GameStation gamestation){
        if(this.isFront()) {return gamestation.verifyResourcesNeeded(this);} else {return true;}
    }

}
