package it.polimi.ingsw.StrategyPatternTest;

import it.polimi.ingsw.model.card.*;
import it.polimi.ingsw.model.card.strategyPattern.ItemCheck;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.*;

import static org.junit.Assert.assertEquals;

public class ItemCheckTest {
    private HashMap<Point, PlayableCard> playedCards;
    HashMap<Item, Integer> resources = new HashMap<>();
    HashMap<Item, Integer> requirements = new HashMap<>();
    private GoalCard goalCard;

    @BeforeEach
    public void SetUp() {
        playedCards = new HashMap<>();
        playedCards.put(new Point(0, 0), new InitialCard(123, true, new HashMap<>(), new HashMap<>(), new ArrayList<>()));
    }
    @Test
    public void TestInitialCardOnly() {
        requirements.put(Item.PARCHMENT, 2);
        goalCard = new GoalCard(0, true, 1, new ItemCheck(), requirements);
        assertEquals(0, goalCard.getGoalPoints(playedCards, resources));
    }
    @Test
    public void TestInsufficientResources(){
        requirements.put(Item.PARCHMENT, 2);
        resources.put(Item.PARCHMENT, 1);
        goalCard = new GoalCard(0, true, 1, new ItemCheck(), requirements);
        assertEquals(0, goalCard.getGoalPoints(new HashMap<>(), resources));
    }
    @Test
    public void TestOnePoint(){
        requirements.put(Item.PARCHMENT, 2);
        resources.put(Item.PARCHMENT, 3);
        goalCard = new GoalCard(0, true, 1, new ItemCheck(), requirements);
        assertEquals(1, goalCard.getGoalPoints(new HashMap<>(), resources));
    }
    @Test
    public void TestTwoPoint(){
        requirements.put(Item.PARCHMENT, 2);
        resources.put(Item.PARCHMENT, 4);
        goalCard = new GoalCard(0, true, 1, new ItemCheck(), requirements);
        assertEquals(2, goalCard.getGoalPoints(new HashMap<>(), resources));
    }
    @Test
    public void TestDifferentItems(){
        requirements.put(Item.PARCHMENT, 2);
        resources.put(Item.PARCHMENT, 4);
        requirements.put(Item.POTION, 1);
        resources.put(Item.POTION, 1);
        goalCard = new GoalCard(0, true, 1, new ItemCheck(), requirements);
        assertEquals(1, goalCard.getGoalPoints(new HashMap<>(), resources));
    }
}
