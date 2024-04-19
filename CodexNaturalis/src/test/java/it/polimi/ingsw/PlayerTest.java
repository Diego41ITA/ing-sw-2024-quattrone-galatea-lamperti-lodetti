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
        assertEquals(gameStation, player.getGameStation());
    }

    @Test
    public void testSetGameStation() {
        GameStation newGameStation = new GameStation(initialCard);
        assertEquals(newGameStation, player.getGameStation());
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
    public void testGetGoal() {
        assertNull(player.getGoal());
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
        player.chooseGoal(test, 0);
        assertEquals(goal1, player.getGoal());
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
        assertTrue(player.showCard().isEmpty());
    }

    @Test
    public void testChooseGoal() {
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
        player.chooseGoal(test, 1);
        assertEquals(goal2, player.getGoal());

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
        assertTrue(player.showCard().contains(cardToPlay));
    }

}
