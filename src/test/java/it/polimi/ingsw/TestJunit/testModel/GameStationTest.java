package it.polimi.ingsw.TestJunit.testModel;

import it.polimi.ingsw.model.card.*;
import it.polimi.ingsw.model.exceptions.illegalOperationException;
import it.polimi.ingsw.model.gameDataManager.GameStation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class GameStationTest {

    private GameStation gameStation;
    private InitialCard initialCard;

    @BeforeEach
    public void setUp() {
        HashMap<Angle, Item> frontItems = new HashMap<>();
        HashMap<Angle, Item> backItems = new HashMap<>();
        ArrayList<Item> backResource = new ArrayList<>();
        backResource.add(Item.MUSHROOM);
        backResource.add(Item.VEGETABLE);
        frontItems.put(Angle.HIGHLEFT, Item.MUSHROOM);
        frontItems.put(Angle.HIGHRIGHT, Item.VEGETABLE);
        frontItems.put(Angle.DOWNLEFT, Item.INSECT);
        frontItems.put(Angle.DOWNRIGHT, Item.ANIMAL);
        initialCard = new InitialCard(1, true, frontItems, backItems, backResource);
        gameStation = new GameStation(initialCard);
    }

    @Test
    public void testGetPlayedCards() {
        Map<Point, PlayableCard> playedCards = gameStation.getPlayedCards();
        assertNotNull(playedCards);
        assertFalse(playedCards.isEmpty()); // Initially, there should be one played card (the initial card)

        // Check if the initial card is at the correct position (0, 0)
        Point initialCardPosition = new Point(0, 0);
        assertTrue(playedCards.containsKey(initialCardPosition));
        assertEquals(gameStation.getPlayedCards().get(initialCardPosition), initialCard);
    }


    @Test
    public void testGetFreeCords() {
        List<Point> freeCords = gameStation.getFreeCords();
        assertNotNull(freeCords);
        assertFalse(freeCords.isEmpty()); // Initially, free coordinates should be available
    }


    @Test
    public void testUpdateFreeCoordsAndCalculateGoldPoints() {
        //verifies that if I place a card that has an unoccupiable corner then the coordinate will not be available in freecord
        HashMap<Angle, Item> frontResource = new HashMap<>();
        frontResource.put(Angle.HIGHLEFT, Item.MUSHROOM);
        frontResource.put(Angle.HIGHRIGHT, Item.EMPTY);
        frontResource.put(Angle.DOWNLEFT, Item.MUSHROOM);
        ResourceCard card = new ResourceCard(frontResource, new HashMap<>(), new ArrayList<>(), TypeOfCard.MUSHROOM, true, 1,
                0);
        Point cardPosition = new Point(1, 1);
        Point unOccupiablePosition = new Point(2, 0);
        gameStation.placeCard(card, cardPosition);
        //goldCard(num 1) for testing GoldCard
        HashMap<Angle, Item> front = new HashMap<>();
        HashMap<Angle, Item> back = new HashMap<>();
        ArrayList<Item> backResourceGold = new ArrayList<>();
        backResourceGold.add(Item.MUSHROOM);
        HashMap<Item, Integer> resources = new HashMap<>();
        resources.put(Item.MUSHROOM, 1);
         GoldCard goldCard = new GoldCard(front, back, 3, backResourceGold, GoldType.ITEM, resources, Item.MUSHROOM, TypeOfCard.MUSHROOM, true, 1);

        Point cardPosition2 = new Point(-1, 1);
        gameStation.placeCard(goldCard, cardPosition2);
        // Ensure that calculateGoldPoint works
        List<Point> freeCords = gameStation.getFreeCords();
        assertFalse(freeCords.contains(unOccupiablePosition));
        assertEquals(gameStation.calculateGoldPoints(goldCard), 6);

    }

    @Test
    public void testVerifyResourcesNeeded() {
        //checks that if there are enough resources then we have true
        HashMap<Angle, Item> front = new HashMap<>();
        HashMap<Angle, Item> back = new HashMap<>();
        ArrayList<Item> backResourceGold = new ArrayList<>();
        backResourceGold.add(Item.MUSHROOM);
        HashMap<Item, Integer> resources = new HashMap<>();
        resources.put(Item.MUSHROOM, 1);
        resources.put(Item.ANIMAL, 1);
        GoldCard goldCard = new GoldCard(front, back, 3, backResourceGold, GoldType.ITEM, resources, Item.FEATHER, TypeOfCard.MUSHROOM, true, 1);

        assertTrue(gameStation.verifyResourcesNeeded(goldCard));
    }

}
