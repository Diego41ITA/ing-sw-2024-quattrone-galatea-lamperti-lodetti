/**
 * @author Luca Lamperti
 * define a subClass InitialCard of superClass Card
 * frontAngles is a HashMap consisting of keys to indicate the position of the corner (using the Angle enum) and values to indicate the item in the corner (using the Item enum)
 * backAngles is a HashMap consisting of keys to indicate the position of the corner(using enum Angle) and values to indicate the item in the corner(using enum Item)
 * resourcesBack is an ArrayList used to display the resources in the back box of the initialCard
 */

package it.polimi.ingsw.model.card;

import java.util.ArrayList;
import java.util.HashMap;

public class InitialCard extends Card {
    private HashMap<Angle, Item> frontAngles;
    private HashMap<Angle, Item> backAngles;

    private final ArrayList<Item> resourcesBack;

    /**
     * @author Luca Lamperti
     * the constructor of the InitialCard class
     * @param type is the type of the card(from enum TypeOfCard)
     * @param frontAngles is a HashMap with the angles(from enum Angle) as keys and items(from enum item) as values for the front face
     * @param backAngles is a HashMap with the angles(from enum Angle) as keys and items(from enum item) as values for the back face
     * @param back is a ArrayList with the items in the box of the back face of the InitialCard
     */
    public InitialCard(TypeOfCard type, HashMap<Angle,Item> frontAngles, HashMap<Angle,Item> backAngles, ArrayList<Item> back){
        super(type);
        this.frontAngles = new HashMap<>(frontAngles);
        this.backAngles = new HashMap<>(backAngles);
        resourcesBack = new ArrayList<>(back);
    }

    /**
     * @author Luca Lamperti
     * Getter for the attribute ResourcesBack
     * @return an ArrayList<Item> containing the items placed in the box on the back of the InitialCard
     */
    public ArrayList<Item> getResourcesBack(){
        return new ArrayList<>(resourcesBack);
    }

    /**
     * @author Luca Lamperti
     * Getter for the attributes frontAngles/backAngles
     * the method returns frontAngles if isFront is true, otherwise it returns backAngles
     * @return a HashMap<Angle, Item> where each corner maps to the Item it contains
     */
    public HashMap<Angle, Item> getAngles(){
        if(isFront) {
            return new HashMap<>(frontAngles);
        }else{
            return new HashMap<>(backAngles);
        }
    }
}