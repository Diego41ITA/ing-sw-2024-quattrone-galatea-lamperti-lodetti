package it.polimi.ingsw.model.card.strategyPattern;

import it.polimi.ingsw.model.card.*;

import java.awt.*;
import java.util.*;

public class PositionCheck1 implements CheckInterface {

    boolean flag;
    int i;
    Set<Set<Point>> ValidPlacements = new HashSet<>();
    Set<Point> GroupCards = new HashSet<>();
    @Override
    public int check(HashMap<Point, PlayableCard> PlayedCard, HashMap<Item, Integer> AvailableItems, HashMap<Item, Integer> requirements) {
        for (Point a : PlayedCard.keySet()){
            if (PlayedCard.get(a).getType().equals(TypeOfCard.MUSHROOM)){
                flag = true;
                GroupCards.add(a);
                for (i=1; i<3; i++){
                    Point nextPoint = new Point(a.x+i,a.y+i);
                    GroupCards.add(nextPoint);
                    if (!PlayedCard.containsKey(nextPoint) || !PlayedCard.get(nextPoint).getType().equals(TypeOfCard.MUSHROOM)){
                        flag = false;
                        break;
                    }
                }
                if (flag) ValidPlacements.add(new HashSet<>(GroupCards));
            }
            GroupCards.clear();
        }
        for(Set<Point> g : ValidPlacements) {
            flag = false;
            for (Point p : g) {
                for (Set<Point> h : ValidPlacements) {
                    if (!h.equals(g) && !h.contains(p)) {
                        flag = true;
                    }
                }
            }
            if(flag){
                ValidPlacements.remove(g);
            }else {
                for (Point p : g) {
                    for (Set<Point> h : ValidPlacements) {
                        if (!h.equals(g) && h.contains(p)) {
                            ValidPlacements.remove(h);
                        }
                    }
                }
            }
        }
        return ValidPlacements.size();
    }
}
