package it.polimi.ingsw.card;

import java.util.*;
public class GoldCard extends Card{
    private Map<Angle, Item> front;
    private Map<Angle, Item> back;
    private final List<Item> neededResources;
    private final int numOfPoints;
    private final Item;
    private final TypeOfGoldCard typeOfGoldCard;
    private final Item backResource;

    public GoldCard(Map<Angle, Item> front, Map<Angle, Item> back, int points, Item item, TypeOfGoldCard typeGold, List<Item> resources, TypeOfCard typeCard)
    {
        super(typeCard);
        this.front = new HashMap<>(front);
        this.back = new HashMap<>(back);
        this.numOfPoints = points;
        this.typeOfGoldCard = typeGold;
        this.backResource = item;
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

    public List<Item> getFreeItem(){
        //returns free item in the corner.
    }


}
