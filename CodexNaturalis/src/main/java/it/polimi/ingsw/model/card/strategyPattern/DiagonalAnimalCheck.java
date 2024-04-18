package it.polimi.ingsw.model.card.strategyPattern;

import it.polimi.ingsw.model.card.*;

import java.awt.*;
import java.util.*;

/**@author Luca Lamperti
 * this is the card number 89, so this is the third disposition.
 */
public class DiagonalAnimalCheck implements CheckInterface{
    @Override
    public int check(HashMap<Point, PlayableCard> PlayedCard, HashMap<Item, Integer> AvailableItems, HashMap<Item, Integer> requirements) {

        boolean isValid = false;
        Set<Set<Point>> validPlacements = new HashSet<>();
        Set<Point> groupCards = new HashSet<>();

        for (Point a : PlayedCard.keySet()){
            if (PlayedCard.get(a).getType().equals(TypeOfCard.ANIMAL)){
                isValid = true;
                groupCards.add(a);
                for (int i=1; i<3; i++){
                    Point nextPoint = new Point(a.x+i,a.y+i);
                    groupCards.add(nextPoint);
                    if (!PlayedCard.containsKey(nextPoint) || !PlayedCard.get(nextPoint).getType().equals(TypeOfCard.ANIMAL)){
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
