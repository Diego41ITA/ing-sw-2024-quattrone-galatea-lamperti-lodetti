package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.model.card.TypeOfCard;

/**
 * defines a record for holding information about the card
 * @param type this shows the type of the card
 * @param cardId this shows the id of the card
 */
public record CardRecord(TypeOfCard type, Integer cardId) {
}
