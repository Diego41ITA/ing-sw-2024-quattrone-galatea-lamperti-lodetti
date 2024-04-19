package it.polimi.ingsw.model.card;

/**
 * @author Luca Lamperti
 * An enumeration to implement the objects contained in the corners of the cards. The objects can be resources, items needed for GoalCards, or
 * represent cases of an empty corner (a corner without objects) or a hidden corner (a corner covered)
 */
public enum Item {
    VEGETABLE, ANIMAL, INSECT, MUSHROOM, HIDDEN, EMPTY, POTION, FEATHER, PARCHMENT
}
