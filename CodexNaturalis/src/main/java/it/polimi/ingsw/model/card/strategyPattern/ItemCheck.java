package it.polimi.ingsw.model.card.strategyPattern;

import it.polimi.ingsw.model.card.*;

import java.awt.*;
import java.util.HashMap;
/**
 * @author Luca Lamperti
 * Implementation of CheckInterface that checks if the number of available items is enough to satisfy the requirement
 */
public class ItemCheck implements CheckInterface {
    /**
     * Checks if the requirements for available items are satisfied.
     *
     * @param playedCard A map showing each card that was played (value) at a specific point (key)
     * @param availableItems A map showing the available items
     * @param requirements A map showing the requirements to achieve the points of the card
     * @return The number of sets of required items that are available
     */
    @Override
    public int check(HashMap<Point, PlayableCard> playedCard, HashMap<Item, Integer> availableItems, HashMap<Item, Integer> requirements) {
        int maxGoal = 100;
        for(Item i : requirements.keySet()){
            if(requirements.get(i) > availableItems.getOrDefault(i,0)){
                return 0;
            }else{
               maxGoal = Math.min(maxGoal, availableItems.get(i)/requirements.get(i));
            }
        }
        return maxGoal;
    }
}