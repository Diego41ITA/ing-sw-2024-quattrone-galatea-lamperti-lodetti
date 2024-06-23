package it.polimi.ingsw.TestJunit.testModel;
import it.polimi.ingsw.model.card.*;
import it.polimi.ingsw.model.card.strategyPattern.CheckInterface;
import it.polimi.ingsw.model.card.strategyPattern.ItemCheck;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import it.polimi.ingsw.model.gameDataManager.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
public class TableOfDecksTest {

    private TableOfDecks table;

    @BeforeEach
    public void setup() {
        table = new TableOfDecks();
        table.initializeTable();
    }

    @Test
    public void testInitializeTable() {
        table.initializeTable();

        // Verify that the decks were initialized correctly
        assertNotNull(table.getDeckGold());
        //assertNotNull(table.getDeckGoal());
        assertNotNull(table.getDeckResource());
        assertNotNull(table.getDeckStart());
        assertFalse(table.getDeckGold().isEmpty());
        //assertFalse(table.getDeckGoal().isEmpty());
        assertFalse(table.getDeckResource().isEmpty());
        assertFalse(table.getDeckStart().isEmpty());
    }
    @Test
    public void testSetGoals() {
        HashMap<Item, Integer> objects = new HashMap<>();
        objects.put(Item.FEATHER, 1);
        objects.put(Item.POTION, 1);
        objects.put(Item.PARCHMENT, 1);
        CheckInterface item = new ItemCheck();
        GoalCard goalCard1 = new GoalCard(99, true, 3, item, objects);
        GoalCard goalCard2 = new GoalCard(100, true, 3, item, objects);
        ArrayList<GoalCard> goals = new ArrayList<>();
        goals.add(goalCard1);
        goals.add(goalCard2);
        Deck deck = new Deck(goals);
        table.setDeckGoal(deck);
        // Set goals on the table
        table.setGoals(deck);
        // Assert that goals have been set correctly
        assertEquals(4, table.getGoals().size());
        assertEquals(goalCard1, table.getGoals().get(3));
        assertEquals(goalCard2, table.getGoals().get(2));
    }
    @Test
    public void testOtherMethods() {
        table.setCards(table.getCards().get(0)); // Replace the first card
        // Assert that the first card has been replaced correctly
        assertEquals(4, table.getCards().size()); // Ensure still 4 cards in total
        ArrayList<ResourceCard> empty = new ArrayList<>();
        // Test setting cards when the resource deck is empty
        table.setDeckResource(new Deck<>(empty)); // Empty the resource deck
        table.setCards(table.getCards().get(0)); // Try replacing the first card again
        //verify resource cards are 2 and also gold cards
        assertEquals(table.getResourceCards().size(), 2);
        assertEquals(table.getGoldCards().size(), 2);
        table.getGoldCards();
        // Assert that the first card is null due to empty deck
        assertNull(table.getCards().get(0));
    }
}