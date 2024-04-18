package it.polimi.ingsw.model.card.strategyPattern;

import it.polimi.ingsw.model.card.*;

import java.awt.*;
import java.util.*;

/**@author Luca Lamperti
 * this is the card number 90, so this is the fourth disposition
 *
 */
public class DiagonalInsectCheck implements CheckInterface{
    @Override
    public int check(HashMap<Point, PlayableCard> PlayedCard, HashMap<Item, Integer> AvailableItems, HashMap<Item, Integer> requirements) {

        boolean isValid = false;
        Set<Set<Point>> validPlacements = new HashSet<>();
        Set<Point> groupCards = new HashSet<>();

        for (Point a : PlayedCard.keySet()){
            if (PlayedCard.get(a).getType().equals(TypeOfCard.INSECT)){
                isValid = true;
                groupCards.add(a);
                for (int i=1; i<3; i++){
                    Point nextPoint = new Point(a.x+i,a.y+i);
                    groupCards.add(nextPoint);
                    if (!PlayedCard.containsKey(nextPoint) || !PlayedCard.get(nextPoint).getType().equals(TypeOfCard.INSECT)){
                        isValid = false;
                        break;
                    }
                }
                if (isValid) validPlacements.add(new HashSet<>(groupCards));
            }
            groupCards.clear();
        }
        return PlacementOptimizer.optimize(validPlacements);
    }
}
