package it.polimi.ingsw.model.card.strategyPattern;

import it.polimi.ingsw.model.card.Item;
import it.polimi.ingsw.model.card.PlayableCard;
import it.polimi.ingsw.model.card.TypeOfCard;

import java.awt.*;
import java.util.HashMap;

public class PositionCheck2 implements CheckInterface{
    boolean flag;
    int i;
    @Override
    public boolean check(HashMap<Point, PlayableCard> PlayedCard, HashMap<Item, Integer> AvailableItems, HashMap<Item, Integer> requirements) {
        for (Point a : PlayedCard.keySet()){
            if (PlayedCard.get(a).getType().equals(TypeOfCard.VEGETABLE)){
                flag = true;
                for (i=1; i<3; i++){
                    Point nextPoint = new Point(a.x+i,a.y-i);
                    if (!PlayedCard.containsKey(nextPoint) || !PlayedCard.get(nextPoint).getType().equals(TypeOfCard.VEGETABLE)){
                        flag = false;
                        break;
                    }
                }
                if (flag) return true;
            }
        }
        return false;
    }
}
