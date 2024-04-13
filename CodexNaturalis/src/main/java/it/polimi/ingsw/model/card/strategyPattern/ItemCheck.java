package it.polimi.ingsw.model.card.strategyPattern;

import it.polimi.ingsw.model.card.*;

import java.awt.*;
import java.util.HashMap;

public class ItemCheck implements CheckInterface {

    int maxGoal=100;
    @Override
    public int check(HashMap<Point, PlayableCard> playedCard, HashMap<Item, Integer> availableItems, HashMap<Item, Integer> requirements) {
        for(Item i : requirements.keySet()){
            if(requirements.get(i) > availableItems.getOrDefault(i,0)){
                return 0;
            }else{
               maxGoal = Math.min(maxGoal, availableItems.get(i)/requirements.get(i));
            }
        }
        return maxGoal;
    }
}