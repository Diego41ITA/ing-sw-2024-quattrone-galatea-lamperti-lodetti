package it.polimi.ingsw.model.card;

import java.util.ArrayList;
import java.util.*;
import it.polimi.ingsw.model.gameDataManager.GameStation;
import java.awt.Point;

/**
* @author Lodetti Alessandro
* This class defines a new abstraction level in the Card hierarchy. This particular class is
* implemented by the three playable card which this game has. The PlayableCard attribute are the Angles linked to
* their contents, the back resourceS and exposes all the methods needed to make the game playable.
 */
public abstract class PlayableCard extends Card{

    private final Map<Angle, Item> front;
    private final Map<Angle, Item> back;
    private final List<Item> backResource;

    /**
     * this is the constructor for PlayableCard, at the first line it calls the super constructor with two parameters.
     * Be careful that the parameters have to be correctly defined.
     * @param cardId refers to the super class attribute
     * @param type refers to the super class attribute
     * @param isFront refers to the super class attribute
     * @param front is the map used to initialize "PlayableCard.front"
      * @param back is the map used to initialize "PlayableCard.back"
     * @param backResource is the list used to initialize "PlayableCard.backResource"
     */
    PlayableCard(int cardId, TypeOfCard type, boolean isFront, Map<Angle, Item> front, Map<Angle,
            Item> back, List<Item> backResource)
    {
        super(cardId, type, isFront);
        this.front = new HashMap<>(front);
        this.back = new HashMap<>(back);
        this.backResource = new ArrayList<>(backResource);
    }

    /**
     * this method return all the free angles and their contents.
     * it could throw a NoFreeAngleException.
     * @return only a map.
     */
    public Map<Angle, Item> getFreeAngleAndContents() {
        Map<Angle, Item> newMap = new HashMap<>();
        if(isFront())
            newMap.putAll(this.front);
        else
            newMap.putAll(this.back);

        for (Angle a : newMap.keySet())
            if (newMap.get(a) == Item.HIDDEN)
                newMap.remove(a);
        return newMap;
    }

    /**
     * this method check if the item in the specified angle is empty
     * @param a is the angle position to search
     * @return true if the item is Item.Empty
     */
    public boolean checkIfEmpty(Angle a){
        if(!isFront())
            return false;
        else return front.get(a) == Item.EMPTY;
    }

    /**
     * this method check if the item in the specified angle is hidden
     * @param a is the angle position to search
     * @return true if the item is Item.Empty
     */
    public boolean checkIfHidden(Angle a){
        if(!isFront())
            return false;
        else return front.get(a) == Item.HIDDEN;
    }

    /**
     * using getFreeAngleAndContents() methods, this one provides e list of free angles.
     * @return an ArrayList of all the free Angles.
     */
    public List<Angle> getFreeAngle(){
        return new ArrayList<>(this.getFreeAngleAndContents().keySet());
    }

    /**
     * this method provides the user the free item (the ones not hidden by other card for example).
     * @return the list of available items in the map.
     */
    public List<Item> getFreeItem(){
        Map<Angle, Item> m = new HashMap<>(this.getFreeAngleAndContents());
        List<Item> l = new ArrayList<>();
        for(Angle a: m.keySet())
            l.add(m.get(a));
        return l;
    }

    /**
     * hide the specified angle: useful when you place a card.
     * @return true if the corner is successfully hidden.
     */
    public boolean hideAngle(Angle angle){
        if(isFront()){
            if (!front.containsKey(angle) || front.get(angle) == Item.HIDDEN)
                return false;
            front.replace(angle, Item.HIDDEN);
        } else {
            if (!back.containsKey(angle) || back.get(angle) == Item.HIDDEN)
                return false;
            back.replace(angle, Item.HIDDEN);
        }
        return true;
    }

    /**
     * this method provides to the caller a List of the hidden corner
     * @return a list of angles and the user should know that the associated item is HIDDEN.
     */
    public List<Angle> getHiddenCorner(){
        List<Angle> l = new ArrayList<>();
        if(isFront()) {
            for (Angle a : front.keySet()) {
                if (front.get(a) == Item.HIDDEN)
                    l.add(a);
            }
        }
        else {
            for (Angle a : back.keySet()) {
                if (back.get(a) == Item.HIDDEN)
                    l.add(a);
            }
        }
        return l;
    }

    /**
     * This method return the frontward disposition
     * @return a copy of "PlayableCard.front"
     */
    public Map<Angle, Item> getFront(){
        return new HashMap<>(this.front);
    }

    /**
     * This method return the backward disposition.
     * @return a copy of "PlayableCard.back"
     */
    public Map<Angle, Item> getBack(){
        return new HashMap<>(this.back);
    }

    /**
     * it returns the backResource as a list.
     * @return a list of Item, which is a copy of "PlayableCard.backResource"
     */
    public List<Item> getAListOfBackResource(){
        return new ArrayList<>(backResource);
    }

    public boolean verifyResources(GameStation gamestation){
     return true;
    }
}
