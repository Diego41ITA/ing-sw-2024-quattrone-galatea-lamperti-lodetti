package it.polimi.ingsw.model.card.strategyPattern;

import it.polimi.ingsw.model.card.*;

import java.awt.*;
import java.util.*;
/**@author Luca Lamperti
 * this is the card number 94 so this is the eight disposition
 *
 */
public class UpsideDownReverseLInsectAnimalCheck implements CheckInterface{
    Set<Set<Point>> validPlacements = new HashSet<>();
    Set<Point> groupCards = new HashSet<>();
    @Override
    public int check(HashMap<Point, PlayableCard> PlayedCard, HashMap<Item, Integer> AvailableItems, HashMap<Item, Integer> requirements) {
        for (Point a : PlayedCard.keySet()){
            if (PlayedCard.get(a).getType().equals(TypeOfCard.ANIMAL)){
//                groupCards.add(a);
                Point nextPoint = new Point(a.x +1, a.y - 1);
                if (PlayedCard.containsKey(nextPoint) && PlayedCard.get(nextPoint).getType().equals(TypeOfCard.INSECT)){
                    groupCards.add(nextPoint);
                    nextPoint = new Point(a.x +1, a.y - 3);
                    if(PlayedCard.containsKey(nextPoint) && PlayedCard.get(nextPoint).getType().equals(TypeOfCard.INSECT)){
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
