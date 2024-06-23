package it.polimi.ingsw.TestJunit.testModel;
import it.polimi.ingsw.model.card.*;
import it.polimi.ingsw.model.card.strategyPattern.CheckInterface;
import it.polimi.ingsw.model.card.strategyPattern.ItemCheck;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class Cardtest {
    private Card testCard;
    private InitialCard initialCard;
    private GoalCard goalCard;
    private GoldCard goldCard;

    private ResourceCard resourceCard;
    private CheckInterface item = new ItemCheck();

    @BeforeEach
    public void setUp() {
        //initial Card for Testing Card and InitialCard
        HashMap<Angle, Item> frontItems = new HashMap<>();
        HashMap<Angle, Item> backItems = new HashMap<>();
        ArrayList<Item> backResource = new ArrayList<>();
        backResource.add(Item.MUSHROOM);
        backResource.add(Item.VEGETABLE);
        testCard = new InitialCard(1, true, frontItems, backItems, backResource);
        initialCard = (InitialCard) testCard;


        // goalcard(num 99) for testing GoalCard

        HashMap<Item, Integer> objects = new HashMap<>();
        objects.put(Item.FEATHER, 1);
        objects.put(Item.POTION, 1);
        objects.put(Item.PARCHMENT, 1);

        goalCard = new GoalCard(99, true, 3, item, objects);

        //goldCard(num 1) for testing GoldCard
        HashMap<Angle, Item> front = new HashMap<>();
        HashMap<Angle, Item> back = new HashMap<>();
        ArrayList<Item> backResourceGold = new ArrayList<>();
        backResourceGold.add(Item.MUSHROOM);
        HashMap<Item, Integer> resources = new HashMap<>();
        resources.put(Item.MUSHROOM, 2);
        resources.put(Item.ANIMAL,1);
        goldCard = new GoldCard(front, back, 3, backResourceGold, GoldType.ITEM, resources, Item.FEATHER, TypeOfCard.MUSHROOM, true, 1);

        //test resource Card
        resourceCard = new ResourceCard(front, back, Collections.singletonList(Item.MUSHROOM), TypeOfCard.ANIMAL, true, 1, 1);
    }

    @Test
    public void testConstructorAndGetters() {
        assertEquals(TypeOfCard.STARTING, testCard.getType());
        assertTrue(testCard.isFront());
    }

    @Test
    public void testFlip() {
        assertTrue(testCard.isFront());
        testCard.flip();
        assertFalse(testCard.isFront());

        testCard.flip();
        assertTrue(testCard.isFront());
    }
    @Test
    public void testGetBackResources() {
        ArrayList<Item> initialBackResources = (ArrayList<Item>) initialCard.getBackResources();
        assertNotNull(initialBackResources);
        assertEquals(2, initialBackResources.size());
        assertEquals(Item.MUSHROOM, initialBackResources.get(0));
        assertEquals(Item.VEGETABLE, initialBackResources.get(1));
        Item resourceBackResources = resourceCard.getBackResource();
        assertNotNull(resourceBackResources);
        assertEquals(Item.MUSHROOM, resourceBackResources);
    }
    @Test
    public void testGoalCardInitialization() {
        // initialization check
        assertNotNull(goalCard);
        assertEquals(99, goalCard.getCardId());
        assertEquals(item, goalCard.getGoalType());
        assertEquals(true, goalCard.isFront());
        assertEquals(3, goalCard.getNumberOfPoints());
        assertNotNull(goalCard.getGoalType());
        assertEquals(3, goalCard.getListOfObjects().size());
    }

    @Test
    public void testGetGoalPoints() {
        HashMap<Item, Integer> availableItems = new HashMap<>();
        availableItems.put(Item.FEATHER, 2);
        availableItems.put(Item.POTION, 2);

        availableItems.put(Item.PARCHMENT, 2);
        int expectedPoints = 6;
        int actualPoints = goalCard.getGoalPoints(null, availableItems);
        assertEquals(expectedPoints, actualPoints);
    }
    @Test
    public void testGettersGoldCard() {
        assertEquals(3, goldCard.getNumberOfPoints());
        assertEquals(GoldType.ITEM, goldCard.getGoldType());
        assertEquals(Item.FEATHER, goldCard.getBox());
        assertEquals(Item.MUSHROOM, goldCard.getBackResource());
    }



    @Test
    public void testGetResources() {
        HashMap<Item, Integer> expectedResources = new HashMap<>();
        expectedResources.put(Item.MUSHROOM, 2);
        expectedResources.put(Item.ANIMAL,1);

        assertEquals(expectedResources, goldCard.getNeededResources());
    }
    @Test
    public void testPointResourceCard() {
        assertEquals(1, resourceCard.getNumberOfPoints());
    }

}
