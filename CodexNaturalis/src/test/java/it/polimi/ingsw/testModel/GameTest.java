package it.polimi.ingsw.testModel;
import it.polimi.ingsw.model.card.GoalCard;
import it.polimi.ingsw.model.card.Item;
import it.polimi.ingsw.model.card.strategyPattern.CheckInterface;
import it.polimi.ingsw.model.card.strategyPattern.ItemCheck;
import it.polimi.ingsw.model.exceptions.MaxPlayersInException;
import it.polimi.ingsw.model.gameDataManager.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    private Game game;
    private Player player1;
    private Player player2;
    private Player player3;

    @BeforeEach
    public void setUp() {
        game = new Game("Game");
        player1 = new Player();
        player2 = new Player();
        player3 = new Player();
        player1.setNickname("Lorenzo");
        player2.setNickname("Alessandro");
        player3.setNickname("Diego");
        }

        @Test
        public void testAddPlayer() throws MaxPlayersInException {
            game.addPlayer(player1);
            game.addPlayer(player2);
            assertEquals(2, game.getPlayers().size());
            assertTrue(game.getPlayers().containsKey(player1));
            assertTrue(game.getPlayers().containsKey(player2));
        }

        @Test
        public void testGameStatusTransitions() throws MaxPlayersInException {
            assertEquals(Status.WAITING, game.getStatus());
            game.addPlayer(player1);
            game.addPlayer(player2);
            game.setMaxNumberPlayer(2);
            game.start();
            assertEquals(Status.ACTIVE, game.getStatus());
            game.suspend();
            assertEquals(Status.SUSPENDED, game.getStatus());
            game.start();
            assertEquals(Status.ACTIVE, game.getStatus());
            game.endGame();
            assertEquals(Status.FINISHED, game.getStatus());
        }

        @Test
        public void testPlayerConnection() throws MaxPlayersInException {
            game.addPlayer(player1);
            game.addPlayer(player2);
            assertTrue(game.isConnected(player1));
            assertTrue(game.isConnected(player2));
        }

        @Test
        public void testSetMaxNumberPlayer() throws MaxPlayersInException {
            game.setMaxNumberPlayer(2);
            game.addPlayer(player1);
            game.addPlayer(player2);
            assertThrows(MaxPlayersInException.class, () -> game.addPlayer(player3)); // Adding the third player should fail
            // Ensure maxNumberPlayer is clamped within valid range (2 to 4)
            game.setMaxNumberPlayer(1);
            assertEquals(2, game.getMaxNumberPlayer());
            game.setMaxNumberPlayer(10);
            assertEquals(4, game.getMaxNumberPlayer());
        }

        @Test
        public void testCheckName() throws MaxPlayersInException {
            game.addPlayer(player1);
            game.addPlayer(player2);

            // Checking non-existing and existing player names
            assertFalse(game.checkName("Luca"));
            assertTrue(game.checkName("Alessandro"));
        }

    @Test
    public void testPointTableAttribute() {
        game.setPointTable(new PointTable());
        PointTable pointTable = game.getPointTable();
        assertNotNull(pointTable);
        assertNotSame(game.getPointTable(), pointTable);
    }

    @Test
    public void testGetId() {
        assertEquals("Game", game.getId());
    }

    @Test
    public void testTableOfDecksAttribute() {
        game.setTableOfDecks(new TableOfDecks());
        TableOfDecks tableOfDecks = game.getTableOfDecks();
        assertNotNull(tableOfDecks);
        assertNotSame(game.getTableOfDecks(), tableOfDecks);
    }

    @Test
    public void testTurnAttribute() {
        ArrayList<Player> players = new ArrayList<>();
        ArrayList<GoalCard> goals = new ArrayList<>();
        HashMap<Item, Integer> objects = new HashMap<>();
        objects.put(Item.FEATHER, 1);
        objects.put(Item.POTION, 1);
        objects.put(Item.PARCHMENT, 1);
        CheckInterface item = new ItemCheck();
        GoalCard goal = new GoalCard(99, true, 3, item, objects);
        goals.add(goal);
        player1.chooseGoal(goals, 99);
        players.add(player1);
        Turn turn1= new Turn(players);
        game.setTurn(turn1);
        Turn turn2 = game.getTurn();
        assertNotNull(turn2);
        assertNotSame(game.getTurn(), turn2);
        }

    @Test
    public void testSetStatus() {
        game.setStatus(Status.ACTIVE);
        assertEquals(Status.ACTIVE, game.getStatus());
    }

    @Test
    public void testSetPlayers() {
        HashMap<Player, Boolean> players = new HashMap<>();
        ArrayList<GoalCard> goals = new ArrayList<>();
        HashMap<Item, Integer> objects = new HashMap<>();
        objects.put(Item.FEATHER, 1);
        objects.put(Item.POTION, 1);
        objects.put(Item.PARCHMENT, 1);
        CheckInterface item = new ItemCheck();
        GoalCard goal = new GoalCard(99, true, 3, item, objects);
        goals.add(goal);
        player1.chooseGoal(goals, 99);
        players.put(player1, true);
        game.setPlayers(players);
        boolean condition = false;
        for (Player player : game.getPlayers().keySet()) {
            if (player.getNick().equals("Lorenzo")) {
                condition = true;
                break;
            }
        }
        assertTrue(condition);

        //assertEquals(true, game.getPlayers().get(player1)); manca controllo sul value
    }

    @Test
    public void testSetSinglePlayer() {
        HashMap<Player, Boolean> players = new HashMap<>();
        ArrayList<GoalCard> goals = new ArrayList<>();
        HashMap<Item, Integer> objects = new HashMap<>();
        objects.put(Item.FEATHER, 1);
        objects.put(Item.POTION, 1);
        objects.put(Item.PARCHMENT, 1);
        CheckInterface item = new ItemCheck();
        GoalCard goal = new GoalCard(99, true, 3, item, objects);
        goals.add(goal);
        player2.chooseGoal(goals, 99);
        game.setSinglePlayer(player2);
        boolean condition = false;
        for (Player player : game.getPlayers().keySet()) {
            if (player.getNick().equals("Alessandro")) {
                condition = true;
                break;
            }
        }
        assertTrue(condition);
    }

    }

