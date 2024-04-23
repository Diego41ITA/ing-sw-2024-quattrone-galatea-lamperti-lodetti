package it.polimi.ingsw;
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
    @Test // il test va fatto sulla carta numero 40 e non sulla prima
    public void TestJsonExampleGoldCard(){
        Deck<GoldCard> deckgold = table.getDeckGold();
        Card example = deckgold.getFirst();
        assertEquals(example.getCardId(), 1);
        assertEquals(((GoldCard) example).getNumberOfPoints(),1);
        assertEquals(((GoldCard) example).getBox(),Item.FEATHER);
        assertEquals(((GoldCard) example).getGoldType(),GoldType.ITEM);
        Boolean condition = true;
        //control there isn't an angle
        assertTrue(((GoldCard) example).getFront().containsKey(Angle.HIGHLEFT));
        //control of the front

        for (Angle key : ((GoldCard) example).getFront().keySet()) {
            if (key == Angle.HIGHRIGHT) {
                if (!((GoldCard) example).getFront().get(key).equals(Item.EMPTY)) ;
                condition = false;
            }
            if (key == Angle.DOWNRIGHT) {
                if (!((GoldCard) example).getFront().get(key).equals(Item.FEATHER)) ;
                condition = false;
            }
            if (key == Angle.DOWNLEFT) {
                if (((GoldCard) example).getFront().get(key).equals(Item.EMPTY)) ;
                condition = false;


            }
        }
        //controll of the beck
        for (Angle key : ((GoldCard) example).getBack().keySet()) {
                if(key == Angle.HIGHLEFT){
                    if(!((GoldCard) example).getBack().get(key).equals(Item.EMPTY));
                    condition = false;
                }
                if(key == Angle.HIGHRIGHT){
                    if(!((GoldCard) example).getBack().get(key).equals(Item.EMPTY));
                    condition = false;
                }
                if(key == Angle.DOWNRIGHT){
                    if(!((GoldCard) example).getBack().get(key).equals(Item.EMPTY));
                    condition = false;
                }
                if(key == Angle.DOWNLEFT){
                    if(((GoldCard) example).getBack().get(key).equals(Item.EMPTY));
                    condition = false;

                }
        }
        assertTrue(condition);

        assertEquals(((GoldCard) example).getBackResource(),Item.MUSHROOM);
        assertEquals(example.isFront(), false);
        assertEquals(example.getType(),Item.MUSHROOM);
        for (Item key : ((GoldCard) example).getNeededResources().keySet()) {
            if (key == Item.MUSHROOM) {
                if (!((GoldCard) example).getNeededResources().get(key).equals(2)) {
                    condition = false;
                }
            }
            if (key == Item.ANIMAL) {
                if (!((GoldCard) example).getNeededResources().get(key).equals(1)) {
                    condition = false;
                }
            }

        }
        if (((GoldCard) example).getNeededResources().size() != 2) {
            condition = false;
        }
            assertTrue(condition);

    }

    @Test
    public void TestJsonExampleResourceCard() {
        Deck<ResourceCard> deckResource = table.getDeckResource();
        Card example = deckResource.getFirst();
        assertEquals(example.getCardId(), 38);
        ResourceCard example2 = deckResource.getFirst();
        assertEquals(example2.getCardId(), 37);
    }
    @Test
    public void TestJsonExampleInitialCard() {
        Deck<InitialCard> deckResource = table.getDeckStart();
        Card example = deckResource.getFirst();
        assertEquals(example.getCardId(), 1);
        //aggiungere gli altri controlli
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
        assertEquals(2, table.getGoals().size());
        assertEquals(goalCard1, table.getGoals().get(1));
        assertEquals(goalCard2, table.getGoals().get(0));
    }
    @Test
    public void testSetCards() {
        table.setCards(table.getCards().get(0)); // Replace the first card
        // Assert that the first card has been replaced correctly
        assertEquals(4, table.getCards().size()); // Ensure still 4 cards in total
        ArrayList<ResourceCard> empty = new ArrayList<>();
        // Test setting cards when the resource deck is empty
        table.setDeckResource(new Deck<>(empty)); // Empty the resource deck
        table.setCards(table.getCards().get(0)); // Try replacing the first card again
        // Assert that the first card is null due to empty deck
        assertNull(table.getCards().get(0));
    }
}