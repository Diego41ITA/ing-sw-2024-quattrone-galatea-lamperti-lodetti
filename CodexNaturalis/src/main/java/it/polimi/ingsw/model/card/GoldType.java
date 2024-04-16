package it.polimi.ingsw.model.card;

/**
 * this enum class defines the three different type that a gold card could assume:
 * - ANGLE: the card gives some point for every angle that covers
 * - CLASSIC: the card gives a regular amount of point
 * - ITEM: the card gives point for the presence of specific items
 * @author Lodetti Alessandro
 */
public enum GoldType {
    ANGLE,
    CLASSIC,
    ITEM
}
