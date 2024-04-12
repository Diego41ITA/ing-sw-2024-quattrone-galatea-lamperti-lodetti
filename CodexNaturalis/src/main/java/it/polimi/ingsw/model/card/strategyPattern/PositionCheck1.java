package it.polimi.ingsw.model.card.strategyPattern;

import it.polimi.ingsw.model.card.*;

import java.awt.*;
import java.util.*;
import java.util.stream.Collectors;

public class PositionCheck1 implements CheckInterface {

    boolean isValid = false;
    boolean isContained = false;
    int i;
    Set<Set<Point>> validPlacements = new HashSet<>();
    Set<Point> groupCards = new HashSet<>();
    @Override
    public int check(HashMap<Point, PlayableCard> PlayedCard, HashMap<Item, Integer> AvailableItems, HashMap<Item, Integer> requirements) {
        for (Point a : PlayedCard.keySet()){
            if (PlayedCard.get(a).getType().equals(TypeOfCard.MUSHROOM)){
                isValid = true;
                groupCards.add(a);
                for (i=1; i<3; i++){
                    Point nextPoint = new Point(a.x+i,a.y+i);
                    groupCards.add(nextPoint);
                    if (!PlayedCard.containsKey(nextPoint) || !PlayedCard.get(nextPoint).getType().equals(TypeOfCard.MUSHROOM)){
                        isValid = false;
                        break;
                    }
                }
                if (isValid) validPlacements.add(new HashSet<>(groupCards));
            }
            groupCards.clear();
        }
        for(Set<Point> CoordGroup : validPlacements) {
            isContained = true;
            outsideLoop:
            for (Point p : CoordGroup) {
                for (Set<Point> coordGroupCheck : validPlacements) {
                    if (!coordGroupCheck.equals(CoordGroup) && !coordGroupCheck.contains(p)) {
                        isContained = false;
                        break outsideLoop;
                    }
                }
            }
            if(isContained){
                validPlacements.remove(CoordGroup);
            }else {
                Set<Set<Point>> validPlacementsFiltered;
                for (Point p : CoordGroup) {
                    validPlacementsFiltered = validPlacements.stream()
                            .filter(x -> x.equals(CoordGroup) || !x.contains(p))
                            .collect(Collectors.toSet());
                    validPlacements = validPlacementsFiltered;
                    }
                }
            }
        return validPlacements.size();
    }
}
