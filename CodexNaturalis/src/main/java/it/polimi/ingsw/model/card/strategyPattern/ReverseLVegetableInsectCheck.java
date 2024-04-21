package it.polimi.ingsw.model.card.strategyPattern;

import it.polimi.ingsw.model.card.*;

import java.awt.*;
import java.util.*;
/**@author Luca Lamperti
 * this is the card number 92 so the sixth disposition
 *
 */
public class ReverseLVegetableInsectCheck implements CheckInterface{
    @Override
    public int check(HashMap<Point, PlayableCard> PlayedCard, HashMap<Item, Integer> AvailableItems, HashMap<Item, Integer> requirements) {
        Set<Set<Point>> validPlacements = new HashSet<>();
        Set<Point> groupCards = new HashSet<>();

        for (Point a : PlayedCard.keySet()){
            if (PlayedCard.get(a).getType().equals(TypeOfCard.INSECT)){
                //groupCards.add(a);
                Point nextPoint = new Point(a.x +1, a.y + 1);
                if (PlayedCard.containsKey(nextPoint) && PlayedCard.get(nextPoint).getType().equals(TypeOfCard.VEGETABLE)){
                    groupCards.add(nextPoint);
                    nextPoint = new Point(a.x +1, a.y + 3);
                    if(PlayedCard.containsKey(nextPoint) && PlayedCard.get(nextPoint).getType().equals(TypeOfCard.VEGETABLE)){
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
