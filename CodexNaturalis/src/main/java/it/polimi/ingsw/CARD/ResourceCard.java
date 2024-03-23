package it.polimi.ingsw.CARD;
import java.util.*;
public class ResourceCard extends Card {
    private Map<Angle, Item> front;
    private Map<Angle, Item> back;
    private final Item backResource;
    private final int numOfPoints;

    public ResourceCard(Map<Angle, Item> front, Map<Angle, Item> back, Item backResource){
        super();
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
