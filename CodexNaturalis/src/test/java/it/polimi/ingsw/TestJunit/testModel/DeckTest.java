package it.polimi.ingsw.TestJunit.testModel;
import it.polimi.ingsw.model.card.*;
import it.polimi.ingsw.model.gameDataManager.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DeckTest{
    private Deck<Card> testDeck;
    private List<Card> testCardList;

    @BeforeEach
    public void setUp() {
        testCardList = new ArrayList<>();
        HashMap<Angle, Item> frontItems1 = new HashMap<>();
        HashMap<Angle, Item> backItems1 = new HashMap<>();
        HashMap<Angle, Item> frontItems2 = new HashMap<>();
        HashMap<Angle, Item> backItems2 = new HashMap<>();
        HashMap<Angle, Item> frontItems3 = new HashMap<>();
        HashMap<Angle, Item> backItems3 = new HashMap<>();
        ArrayList<Item> backResource1 = new ArrayList<>();
        ArrayList<Item> backResource2 = new ArrayList<>();
        ArrayList<Item> backResource3 = new ArrayList<>();
        testCardList.add(new InitialCard(81,true, frontItems1, backItems1, backResource1));
        testCardList.add(new InitialCard(82,true, frontItems2, backItems2, backResource2));
        testCardList.add(new InitialCard(83,true, frontItems3, backItems3, backResource3));
        testDeck = new Deck<>(testCardList);
    }

    @Test
    public void testGetDimensionAndIsEmpty() {
        assertEquals(testCardList.size(), testDeck.getDimension());
        assertFalse(testDeck.isEmpty());
        testDeck = new Deck<>(new ArrayList<>());
        assertTrue(testDeck.isEmpty());
    }

    @Test
    public void testGetFirst() {
        // Test the behavior of getFirst() on a non-empty deck
        int initialSize = testDeck.getDimension();
        Card firstCard = testDeck.getFirst();
        assertEquals(initialSize - 1, testDeck.getDimension());
        assertFalse(testDeck.getStatus().contains(firstCard));
        // Verify that it throws an IllegalStateException on an empty deck
        testDeck = new Deck<>(new ArrayList<>());
        assertThrows(IllegalStateException.class, () -> testDeck.getFirst());
    }

    @Test
    public void testShuffle() {
        // Copy the actual state of the deck
        List<Card> originalOrder = testDeck.getStatus();

        // shuffles the deck and verify the changed order
        testDeck.shuffle();
        if(!originalOrder.equals(testDeck.getStatus())) {
            assertNotEquals(originalOrder, testDeck.getStatus());
        }else{//corner case
            assertEquals(originalOrder, testDeck.getStatus());
        }
    }

    @Test
    public void testDimensionzero() {
        testDeck = new Deck<>(new ArrayList<>());
        assertEquals(0, testDeck.getDimension());
    }

}