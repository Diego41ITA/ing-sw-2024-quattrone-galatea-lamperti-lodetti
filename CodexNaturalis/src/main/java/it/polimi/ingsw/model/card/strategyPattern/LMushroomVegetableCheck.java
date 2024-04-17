package it.polimi.ingsw.model.card.strategyPattern;

import it.polimi.ingsw.model.card.*;

import java.awt.*;
import java.util.*;
/**@author Luca Lamperti
 * this is card number 91 the fifth disposition
 *
 */
public class LMushroomVegetableCheck implements CheckInterface{
    Set<Set<Point>> validPlacements = new HashSet<>();
    Set<Point> groupCards = new HashSet<>();
    @Override
    public int check(HashMap<Point, PlayableCard> PlayedCard, HashMap<Item, Integer> AvailableItems, HashMap<Item, Integer> requirements) {
        for (Point a : PlayedCard.keySet()){
            if (PlayedCard.get(a).getType().equals(TypeOfCard.VEGETABLE)){
                groupCards.add(a);
                Point nextPoint = new Point(a.x -1, a.y + 1);
                if (PlayedCard.containsKey(nextPoint) && PlayedCard.get(nextPoint).getType().equals(TypeOfCard.MUSHROOM)){
                    groupCards.add(nextPoint);
                    nextPoint = new Point(a.x -1, a.y + 3);
                    if(PlayedCard.containsKey(nextPoint) && PlayedCard.get(nextPoint).getType().equals(TypeOfCard.MUSHROOM)){
                        groupCards.add(nextPoint);
                        validPlacements.add(groupCards);
                    }
                }
            }
            groupCards.clear();
        }
        return PlacementOptimizer.optimize(validPlacements);
    }
}
