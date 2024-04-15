package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.gameDataManager.GameStation;
import it.polimi.ingsw.model.card.strategyPattern.CheckInterface;

import java.awt.*;
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
    private final GoldType goldType;


    /**
     * it is the only constructor for this class
     * @param front this map links the position of an angle with its contents (for the front).
     * @param back same but for the back.
     * @param points these are the points that a player can earn from this card.
     * @param backResource defines the permanent resource in the back.
     * @param goldType indicates one of the type that a gold card can assume.
     * @param resources indicates the resource that a player need to place this card.
     * @param type refer to the super class attribute
     * @param isFront refer to the super class attribute
     */
    public GoldCard(Map<Angle, Item> front, Map<Angle, Item> back, int points, List<Item> backResource,
                    GoldType goldType, HashMap<Item, Integer> resources,Item box, TypeOfCard type,
                    boolean isFront)
    {
        super(type, isFront, front, back, backResource);
        this.numOfPoints = points;
        this.box = box;
        this.goldType = goldType;
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
    public GoldType getGoldType() {
        return goldType;
    }

    /**
     * this method is to use when the type of the gold card is ITEM otherwise the item return doesn't make any sense.
     * @return the item that gives points.
     */
    public Item getBox(){
        return this.box;
    }

    /**
     * Indicates the resources that the player should have in order to place this card
     * @return a map of item and their cardinality.
     */
    public HashMap<Item, Integer> getNeededResources(){
         return new HashMap<>(resources);
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
