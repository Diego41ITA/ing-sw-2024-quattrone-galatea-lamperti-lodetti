/**this enum helps to identify the type of particular card.
 * By changing the hierarchy, for example by adding a PlayableCard interface
 * we could redefine this enum.
 * @author Lodetti Alessandro
 * */
package it.polimi.ingsw.model.card;

public enum TypeOfCard {
    ANIMAL,
    VEGETABLE,
    MUSHROOM,
    INSECT,
    STARTING;   //this one might be useless
}
