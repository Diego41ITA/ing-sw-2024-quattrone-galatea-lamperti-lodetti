package it.polimi.ingsw.model.card.strategyPattern;

import it.polimi.ingsw.model.card.*;

import java.awt.*;
import java.util.*;

/**
 * Implementation of CheckInterface that checks for diagonal placement of cards with insect type
 * It checks if there are three cards with insect type placed diagonally
 * @author Luca Lamperti
 */
public class DiagonalInsectCheck implements CheckInterface{
    /**
     * Checks if the requirements for diagonal placement of insect cards are satisfied
     *
     * @param playedCard A map showing each card that was played (value) at a specific point (key)
     * @param availableItems A map showing the available items
     * @param requirements A map showing the requirements to achieve the points of the card
     * @return The number of valid placements, each with different cards, that satisfy the diagonal requirement
     */
    @Override
    public int check(HashMap<Point, PlayableCard> playedCard, HashMap<Item, Integer> availableItems, HashMap<Item, Integer> requirements) {

        boolean isValid = false;
        Set<Set<Point>> validPlacements = new HashSet<>();
        Set<Point> groupCards = new HashSet<>();

        for (Point a : playedCard.keySet()){
            if (playedCard.get(a).getType().equals(TypeOfCard.INSECT)){
                isValid = true;
                groupCards.add(a);
                for (int i=1; i<3; i++){
                    Point nextPoint = new Point(a.x+i,a.y-i);
                    groupCards.add(nextPoint);
                    if (!playedCard.containsKey(nextPoint) || !playedCard.get(nextPoint).getType().equals(TypeOfCard.INSECT)){
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

    /**
     * useful to know the type of this object.
     * @return a textual description
     */
    @Override
    public String toString(){
        return this.getClass().getName();
    }
}
