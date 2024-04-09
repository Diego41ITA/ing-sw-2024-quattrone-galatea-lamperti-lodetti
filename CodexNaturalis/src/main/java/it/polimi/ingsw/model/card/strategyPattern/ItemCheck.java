package it.polimi.ingsw.model.card.strategyPattern;

import it.polimi.ingsw.model.card.Item;
import it.polimi.ingsw.model.card.PlayableCard;
import it.polimi.ingsw.model.card.strategyPattern.CheckInterface;

import java.awt.*;
import java.util.HashMap;

public class ItemCheck implements CheckInterface {
    @Override
    public boolean check(HashMap<Point, PlayableCard> PlayedCard, HashMap<Item, Integer> AvailableItems, HashMap<Item, Integer> requirements) {
        for(Item i : requirements.keySet()){
            if(requirements.get(i) > AvailableItems.getOrDefault(i,0)) return false;
        }
        return true;
    }
}
