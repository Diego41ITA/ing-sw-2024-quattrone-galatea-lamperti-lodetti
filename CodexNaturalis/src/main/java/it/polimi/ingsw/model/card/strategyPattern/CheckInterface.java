package it.polimi.ingsw.model.card.strategyPattern;

import it.polimi.ingsw.model.card.Item;
import it.polimi.ingsw.model.card.PlayableCard;

import java.util.*;
import java.awt.*;

/**
 * @author Luca Lamperti
 * Define an interface to implement strategy pattern for GoalCards
 * It includes a method to check if the requirements of the GoalCard are satisfied
 */
public interface CheckInterface {
    /**
     * check if the requirements of the GoalCard are satisfied
     *
     * @param playedCard A map showing each card that was played (value) at a specific point (key)
     * @param availableItems A map showing the available items
     * @param requirements A map showing the requirements to achieve the points of the card
     * @return the result of checking requirements. It can be 0 if the requirements aren't satisfied, or a positive number representing
     *         how many times, following the rules, the requirements are satisfied
     */
    public int check(HashMap<Point, PlayableCard> playedCard, HashMap<Item, Integer> availableItems, HashMap<Item, Integer> requirements);
}
