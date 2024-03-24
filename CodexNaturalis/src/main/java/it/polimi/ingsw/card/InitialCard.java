/**
 * @author Luca Lamperti
 * define a subClass InitialCard of superclass Card
 * frontAngles is a HashMap consisting of keys to indicate the position of the corner (using the Angle enum) and values to indicate the item in the corner (using the Item enum)
 * backAngles is a HashMap consisting of keys to indicate the position of the corner(using enum Angle) and values to indicate the item in the corner(using enum Item)
 * resourcesBack is an ArrayList used to display the resources in the back box of the initialCard
 */

package it.polimi.ingsw.card;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class InitialCard extends Card {
    private final HashMap<Angle, Item> frontAngles = new HashMap<>();
    private final HashMap<Angle, Item> backAngles = new HashMap<>();

    private final ArrayList<Item> resourcesBack = new ArrayList<>();

    /**
     * @author Luca Lamperti
     * the constructor of the InitialCard class
     * @param type is the type of the card(from enum TypeOfCard)
     * @param frontAngles is a Stack where the items(from enum Item) in the corners of the front face of the InitialCard must be pushed in the following order: DOWNRIGHT, DOWNLEFT, HIGHRIGHT, HIGHLEFT
     * @param backAngles is a Stack where the items(from enum Item) in the corners of the back face of the InitialCard must be pushed in the following order: DOWNRIGHT, DOWNLEFT, HIGHRIGHT, HIGHLEFT
     * @param back is a Stack where the items on the back face of the InitialCard are pushed
     */
    public InitialCard(TypeOfCard type, Stack<Item> frontAngles, Stack<Item> backAngles, Stack<Item> back){
        super(type);
        this.frontAngles.put(Angle.HIGHLEFT, frontAngles.pop());
        this.frontAngles.put(Angle.HIGHRIGHT, frontAngles.pop());
        this.frontAngles.put(Angle.DOWNLEFT, frontAngles.pop());
        this.frontAngles.put(Angle.DOWNRIGHT, frontAngles.pop());
        this.backAngles.put(Angle.HIGHLEFT, backAngles.pop());
        this.backAngles.put(Angle.HIGHRIGHT, backAngles.pop());
        this.backAngles.put(Angle.DOWNLEFT, backAngles.pop());
        this.backAngles.put(Angle.DOWNRIGHT, backAngles.pop());
        while(!back.empty()){
            resourcesBack.add(back.pop());
        }
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