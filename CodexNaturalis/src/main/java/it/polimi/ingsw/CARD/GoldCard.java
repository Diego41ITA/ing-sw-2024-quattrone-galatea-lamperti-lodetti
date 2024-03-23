package it.polimi.ingsw.CARD;

import java.util.*;
public class GoldCard extends Card{
    private Map<Angle, Item> front;
    private Map<Angle, Item> back;
    private final List<Item> neededResources;
    private final int numOfPoints;
    private final Item;
    private final TypeOfGoldCard type;
    private final Item backResource;

    public GoldCard(Map<Angle, Item> front, Map<Angle, Item> back, int points, Item item, TypeOfGoldCard type, List<Item> resources)
    {
        super();
        this.front = new HashMap<>(front);
        this.back = new HashMap<>(back);
        this.numOfPoints = points;
        this.type = type;
        this.backResource = item;
        this.neededResources = new ArrayList<>(resources);
    }

    public int getNumOfPoints() {
        return numOfPoints;
    }
    public Item getBackResource() {
        return backResource;
    }
    public TypeOfGoldCard getType() {
        return type;
    }

    public ArrayList<Item> getFreeItem(){
        //returns free item in the corner.
    }



}
