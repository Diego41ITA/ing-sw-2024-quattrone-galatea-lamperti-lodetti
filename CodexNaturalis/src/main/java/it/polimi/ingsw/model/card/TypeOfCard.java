package it.polimi.ingsw.model.card;

/**@author Lodetti Alessandro
 * this enum helps to identify the type of particular card.
 * By changing the hierarchy, for example by adding a PlayableCard interface
 * we could redefine this enum.
 */
public enum TypeOfCard {
    ANIMAL,
    VEGETABLE,
    MUSHROOM,
    INSECT,
    STARTING,
    GOAL,
    GOLD
}
