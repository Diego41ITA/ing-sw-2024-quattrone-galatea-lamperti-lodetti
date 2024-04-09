package it.polimi.ingsw.model.card.strategyPattern;

import it.polimi.ingsw.model.card.Item;
import it.polimi.ingsw.model.card.PlayableCard;

import java.util.*;
import java.awt.*;

public interface CheckInterface {
    public boolean check(HashMap<Point, PlayableCard> PlayedCard, HashMap<Item, Integer> AvailableItems, HashMap<Item, Integer> requirements);
}
