package it.polimi.ingsw;

import it.polimi.ingsw.model.card.*;
import it.polimi.ingsw.model.card.strategyPattern.CheckInterface;
import it.polimi.ingsw.model.card.strategyPattern.ItemCheck;
import it.polimi.ingsw.model.exceptions.illegalOperationException;
import it.polimi.ingsw.model.gameDataManager.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;
import java.util.List;
import java.awt.Point;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    private Player player;
    private GameStation gameStation;
    private ArrayList<PlayableCard> cards;
    private InitialCard initialCard;

    private ResourceCard Card1;
    private ResourceCard Card2;

    private GoldCard card3;


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
        cards = new ArrayList<PlayableCard>();
        cards.add(new ResourceCard(new HashMap<>(),new HashMap<>(), new ArrayList<>(), TypeOfCard.MUSHROOM, true,1,
                0));
        cards.add(new ResourceCard(new HashMap<>(),new HashMap<>(),new ArrayList<>(), TypeOfCard.MUSHROOM, true,2,
                0));
        cards.add(new GoldCard(new HashMap<>(),new HashMap<>(), 1, new ArrayList<>(),
                GoldType.ITEM, new HashMap<>(),Item.FEATHER, TypeOfCard.MUSHROOM,
                true, 1));
        player = new Player("TestPlayer", gameStation, Color.YELLOW, cards);

    }

    @Test
    public void testGetNick() {
        assertEquals("TestPlayer", player.getNick());
    }

    @Test
    public void testSetNickname() {
        player.setNickname("NewNickname");
        assertEquals("NewNickname", player.getNick());
    }

    @Test
    public void testGetGameStation() {
        assertNotNull( player.getGameStation());
    }

    @Test
    public void testSetGameStation() {
        HashMap<Angle, Item> frontItems = new HashMap<>();
        HashMap<Angle, Item> backItems = new HashMap<>();
        ArrayList<Item> backResource = new ArrayList<>();
        backResource.add(Item.MUSHROOM);
        backResource.add(Item.VEGETABLE);
        GameStation newGameStation = new GameStation(new InitialCard(1, true, frontItems, backItems, backResource));
        player.setGameStation(newGameStation);
        assertNotSame(newGameStation.getPlayedCards(), player.getGameStation().getPlayedCards());
    }

    @Test
    public void testGetOptionalColor() {
        assertFalse(player.getOptionalColor().isPresent());
        player.setOptionalColor();
        assertEquals(Optional.of(Color.BLACK), player.getOptionalColor());
    }

    @Test
    public void testGetColor() {
        assertEquals(Color.YELLOW, player.getColor());
    }

    @Test
    public void testSetColor() {
        player.setColor(Color.RED);
        assertEquals(Color.RED, player.getColor());
    }

    @Test
    public void testDrawSingleCard() {
        assertEquals(3, player.showCard().size());
        assertThrows(IllegalStateException.class, () -> player.draw(new ResourceCard(new HashMap<>(),new HashMap<>(), new ArrayList<>(), TypeOfCard.MUSHROOM, true,1,
                0)));
        assertEquals(3, player.showCard().size());
    }

    @Test
    public void testDrawDeck() {
        List<PlayableCard> cardList = new ArrayList<>();
        Deck<PlayableCard> deck = new Deck<>(cardList);
        assertEquals(3, player.showCard().size());
        assertThrows(IllegalStateException.class, () -> player.draw(deck));
        assertEquals(3, player.showCard().size());
    }

    @Test
    public void testShowCard() {
        assertEquals(cards, player.showCard());
        cards.clear();
        assertFalse(player.showCard().isEmpty());
    }

    @Test
    public void testChooseGoalAndGetGoal() {
        HashMap<Item, Integer> objects1 = new HashMap<>();
        objects1.put(Item.FEATHER, 1);
        objects1.put(Item.POTION, 1);
        objects1.put(Item.PARCHMENT, 1);
        HashMap<Item, Integer> objects2 = new HashMap<>();
        objects1.put(Item.PARCHMENT, 2);
        CheckInterface item = new ItemCheck();

        GoalCard goal1 = new GoalCard(99, true, 3, item, objects1);
        GoalCard goal2 = new GoalCard(100, true, 2, item, objects2);
        ArrayList<GoalCard> test = new ArrayList<GoalCard>();
        test.add(goal1);
        test.add(goal2);
        player.chooseGoal(test, 1);
        //verifies that the ids are the same
        assertEquals(goal2.getCardId(), player.getGoal().getCardId());
        assertThrows(IllegalArgumentException.class, () -> player.chooseGoal(test, -1));
        assertThrows(IllegalArgumentException.class, () -> player.chooseGoal(test, 2));
    }

    @Test
    public void testRemoveCards() {
        assertFalse(player.showCard().isEmpty());
        player.removeCards();
        assertTrue(player.showCard().isEmpty());
    }
    @Test
    public void testPlayCard_successfulPlay() throws illegalOperationException {
        PlayableCard cardToPlay = new ResourceCard(new HashMap<>(),new HashMap<>(), new ArrayList<>(), TypeOfCard.MUSHROOM, true,1,
                0);
        Point cord = new Point(1, 1);
        player.playCard(cardToPlay, cord);
        assertTrue(player.getGameStation().getPlayedCards().containsValue(cardToPlay));
        assertFalse(player.showCard().contains(cardToPlay));
    }
    @Test
    public void testPlayCard_unsuccessfulPlay() {
        HashMap<Angle, Item> front = new HashMap<>();
        HashMap<Angle, Item> back = new HashMap<>();
        ArrayList<Item> backResourceGold = new ArrayList<>();
        backResourceGold.add(Item.MUSHROOM);
        HashMap<Item, Integer> resources = new HashMap<>();
        resources.put(Item.MUSHROOM, 2);
        resources.put(Item.ANIMAL,1);
        PlayableCard cardToPlay = new GoldCard(front, back, 3, backResourceGold, GoldType.ITEM, resources, Item.FEATHER, TypeOfCard.MUSHROOM, true, 1);
        Point cord = new Point(1, 1);
        try {
            player.playCard(cardToPlay, cord);
            fail("Expected IllegalOperationException to be thrown");
        } catch (illegalOperationException e) {
            assertEquals("There are not enough resources to play this card", e.getMessage());
        }
    }

}
