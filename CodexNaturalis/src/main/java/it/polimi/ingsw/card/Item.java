/**
 * @author Luca Lamperti
 * An enumeration to implement the objects contained in the corners of the cards. The objects can be resources, items needed for GoalCards, or
 * represent cases of an empty corner (a corner without objects) or a hidden corner (a corner covered)
 */

package it.polimi.ingsw.card;
public enum Item {
    VEGETABLE, ANIMAL, INSECT, MUSHROOM, RESOURCE, HIDDEN, EMPTY, POTION, FEATHER, PARCHMENT
}
