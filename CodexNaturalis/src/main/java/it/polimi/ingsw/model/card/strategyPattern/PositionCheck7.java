package it.polimi.ingsw.model.card.strategyPattern;

import it.polimi.ingsw.model.card.Item;
import it.polimi.ingsw.model.card.PlayableCard;
import it.polimi.ingsw.model.card.TypeOfCard;

import java.awt.*;
import java.util.HashMap;

public class PositionCheck7 implements CheckInterface{
    @Override
    public boolean check(HashMap<Point, PlayableCard> PlayedCard, HashMap<Item, Integer> AvailableItems, HashMap<Item, Integer> requirements) {
        for (Point a : PlayedCard.keySet()){
            if (PlayedCard.get(a).getType().equals(TypeOfCard.MUSHROOM)){
                if (PlayedCard.containsKey(new Point(a.x - 1, a.y - 1)) && PlayedCard.get(new Point(a.x -1, a.y - 1)).getType().equals(TypeOfCard.ANIMAL)){
                    if (PlayedCard.containsKey(new Point(a.x -1, a.y - 3)) && PlayedCard.get(new Point(a.x -1, a.y - 3)).getType().equals(TypeOfCard.ANIMAL)){
                        return true;
                    }
                }
            }
        }
        return false;
    }
}