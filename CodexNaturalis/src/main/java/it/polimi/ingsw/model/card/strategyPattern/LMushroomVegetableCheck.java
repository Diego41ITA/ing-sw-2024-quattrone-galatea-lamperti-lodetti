package it.polimi.ingsw.model.card.strategyPattern;

import it.polimi.ingsw.model.card.*;

import java.awt.*;
import java.util.*;
/**
 * @author Luca Lamperti
 * Implementation of CheckInterface that checks for the presence of a specific pattern involving mushroom and vegetable cards
 */
public class LMushroomVegetableCheck implements CheckInterface{
    /**
     * Checks if the requirements a specific pattern of mushroom and vegetable cards are satisfied
     *
     * @param playedCard A map showing each card that was played (value) at a specific point (key)
     * @param availableItems A map showing the available items
     * @param requirements A map showing the requirements to achieve the points of the card
     * @return The number of valid placements, each with different cards, that satisfy the requirement
     */
    @Override
    public int check(HashMap<Point, PlayableCard> playedCard, HashMap<Item, Integer> availableItems, HashMap<Item, Integer> requirements) {

        Set<Set<Point>> validPlacements = new HashSet<>();
        Set<Point> groupCards = new HashSet<>();

        for (Point a : playedCard.keySet()){
            if (playedCard.get(a).getType().equals(TypeOfCard.VEGETABLE)){
                Point nextPoint = new Point(a.x -1, a.y + 1);
                if (playedCard.containsKey(nextPoint) && playedCard.get(nextPoint).getType().equals(TypeOfCard.MUSHROOM)){
                    groupCards.add(nextPoint);
                    nextPoint = new Point(a.x -1, a.y + 3);
                    if(playedCard.containsKey(nextPoint) && playedCard.get(nextPoint).getType().equals(TypeOfCard.MUSHROOM)){
                        groupCards.add(nextPoint);
                        validPlacements.add(new HashSet<>(groupCards));
                    }
                }
            }
            groupCards.clear();
        }
        return PlacementOptimizer.optimize(validPlacements);
    }
}
