package it.polimi.ingsw.model.card;

/** this enum helps to identify the type of particular card.
 * By changing the hierarchy, for example by adding a PlayableCard interface
 * we could redefine this enum.
 * @author Lodetti Alessandro
 */
public enum TypeOfCard {
    ANIMAL,
    VEGETABLE,
    MUSHROOM,
    INSECT,
    STARTING,
    GOAL
}
