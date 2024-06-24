
        package it.polimi.ingsw.TestJunit.testController;
        import static org.junit.jupiter.api.Assertions.*;
        import it.polimi.ingsw.controller.*;
        import it.polimi.ingsw.model.exceptions.MaxPlayersInException;
        import it.polimi.ingsw.model.gameDataManager.*;
        import it.polimi.ingsw.observer.GameObserver;
        import it.polimi.ingsw.view.FsmGame;
        import it.polimi.ingsw.view.TUI.Cli;
        import it.polimi.ingsw.view.UI;
        import it.polimi.ingsw.view.input.InputParser;
        import it.polimi.ingsw.view.input.InputUi;
        import org.junit.jupiter.api.BeforeEach;
        import org.junit.jupiter.api.Test;
        import java.rmi.RemoteException;
        import java.util.*;

        import it.polimi.ingsw.model.card.*;
        import it.polimi.ingsw.model.card.strategyPattern.CheckInterface;
        import it.polimi.ingsw.model.card.strategyPattern.ItemCheck;
        import it.polimi.ingsw.model.exceptions.illegalOperationException;
        import it.polimi.ingsw.model.gameDataManager.Color;
        import java.awt.*;
        import java.util.List;


        public class TestController  {

    private ControllerOfMatches controllerOfMatches;

    private ControllerOfGame controllerOfGame;


    @BeforeEach
    public void setUp() throws RemoteException {
        controllerOfMatches = ControllerOfMatches.getMainController();
        controllerOfMatches.clearActiveGames();
        controllerOfGame = new ControllerOfGame("Game1", 4);
    }

    @Test
    public void testConstructor() {
        assertNotNull(controllerOfGame);
        assertNotNull(controllerOfGame.getObservers());
        assertEquals("Game1", controllerOfGame.getGameId());
    }

    @Test
    public void testCreateGame() throws RemoteException {
        UI ui = new Cli();
        InputParser input = null;
        GameObserver obs = new FsmGame(ui, input);
        ControllerOfGameInterface gameController = controllerOfMatches.createGame(obs, "Player1", 4);
        assertNotNull(gameController);
        assertEquals(1, controllerOfMatches.getActiveGames().size());
        assertEquals("Game1", gameController.getGameId());
    }
    @Test
    public void testSetGame() {
        Game newGame = new Game("newGameId");
        // Call the setGame method
        controllerOfGame.setGame(newGame);
        // Verify the game is set correctly
        assertEquals("newGameId", controllerOfGame.getGameId(), "Game ID should be updated to newGameId.");
    }

    @Test
    public void testJoinRandomGame() throws RemoteException {
        UI ui = new Cli();
        InputParser input = null;
        GameObserver obs = new FsmGame(ui, input);
        ControllerOfGameInterface gameController = controllerOfMatches.joinRandomGame(obs, "Player1");
        assertNull(gameController);
        ControllerOfGameInterface gameController1 = controllerOfMatches.createGame(obs, "Player1", 2);
        UI ui2 = new Cli();
        GameObserver obs2 = new FsmGame(ui2, input);
        ControllerOfGameInterface gameController2 = controllerOfMatches.joinRandomGame(obs2, "Player2");
        assertNotNull(gameController2);
        //test if the player joined(the size of Players needs to be 2)
        assertEquals(2, gameController1.getPlayers().size());
    }
    @Test
    public void testRejoinGame() throws RemoteException, MaxPlayersInException {
        Game game = new Game("testGameId");
        game.setPointTable(new PointTable());
        ArrayList players = new ArrayList<>();
        Player player = new Player("Player1");
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
        player.chooseGoal(test, 100);
        players.add(player);
        game.setTurn(new Turn(players));
        controllerOfGame = new ControllerOfGame(game);
        controllerOfMatches.getActiveGames().add(controllerOfGame);
        String gameID = controllerOfGame.getGameId();
        String playerNick = "Player1";
        // observer
        UI ui = new Cli();
        InputParser input = null;
        GameObserver obs = new FsmGame(ui, input);
        // Add player to the game
        controllerOfGame.addPlayer(player);
        controllerOfGame.addObserver(obs, playerNick);
        // Simulate player disconnecting
        controllerOfGame.changePlayerStatus(playerNick, false);
        // Attempt to rejoin the game
        ControllerOfGameInterface result = controllerOfMatches.rejoinGame(obs, gameID, playerNick);
        controllerOfMatches.printActiveGames();
        // Check if the player has successfully rejoined
        assertTrue(controllerOfGame.getPlayers().get(player), "The player's status should be active.");
    }

    @Test
    public void testLeaveGame() throws RemoteException {
        UI ui = new Cli();
        InputParser input = null;
        GameObserver obs = new FsmGame(ui, input);
        ControllerOfGameInterface gameController = controllerOfMatches.createGame(obs, "Player1", 4);
        UI ui2 = new Cli();
        GameObserver obs2 = new FsmGame(ui2, input);
        ControllerOfGameInterface gameController2 = controllerOfMatches.joinRandomGame(obs2, "Player2");
        assertNotNull(gameController2);
        UI ui3= new Cli();
        GameObserver obs3= new FsmGame(ui2, input);
        ControllerOfGameInterface gameController3 = controllerOfMatches.joinRandomGame(obs2, "Player3");
        controllerOfMatches.leaveGame(gameController2.getGameId(), "Player3");
        Player player1 = new Player("Player3");
        //check that the player3 is not there
        assertFalse(gameController.getPlayers().containsKey(player1));
    }

    @Test
    public void testDeleteGame() throws RemoteException {
        UI ui = new Cli();
        InputParser input = null;
        GameObserver obs = new FsmGame(ui, input);
        ControllerOfGameInterface gameController = controllerOfMatches.createGame(obs, "Player1", 4);
        assertEquals(1, controllerOfMatches.getActiveGames().size());
        assertNotNull(gameController);
        controllerOfMatches.leaveGame("Game2", "Player1");
        assertEquals(false, controllerOfMatches.getActiveGames().contains(gameController)); // The game should have been removed from the active games list
        assertEquals(0, controllerOfMatches.getActiveGames().size());
        String gameID = controllerOfGame.getGameId();
        controllerOfMatches.removeGame("Game0");
        boolean gameStillExists = controllerOfMatches.getActiveGames().stream()
                .anyMatch(game -> game.getGameId().equals(gameID));
        assertFalse(gameStillExists, "The game should be removed from active games.");
    }


    @Test
    public void testAddObserver() throws RemoteException {
        UI ui = new Cli();
        InputParser input = null;
        GameObserver obs = new FsmGame(ui, input);
        Player player = new Player("Player1");
        controllerOfGame.addObserver(obs, "Player1");
        assertTrue(controllerOfGame.getObservers().containsKey(player.getNick()));
    }
    @Test
    public void testAddPoints2Player() throws RemoteException, MaxPlayersInException {
        Game game = new Game("testGameId");
        Player player = new Player("Player1");
        player.setColor(Color.RED);
        PointTable table = new PointTable();
        table.setColorPoints(Color.RED);
        game.addPlayer(player);
        game.setPointTable(table);
        controllerOfGame = new ControllerOfGame(game);
        //add points
        controllerOfGame.addPoints2Player("Player1", 10);
        // Verify the points are added correctly
        int points = game.getPointTable().getPoint(player);
        assertEquals(10, points, "Player should have 10 points.");
    }

    @Test
    public void testRemoveObserver() throws RemoteException {
        UI ui = new Cli();
        InputParser input = null;
        GameObserver obs = new FsmGame(ui, input);
        Player player = new Player("Player1");
        controllerOfGame.addObserver(obs, "Player2");
        controllerOfGame.removeObserver(player);
        assertFalse(controllerOfGame.getObservers().containsKey(player.getNick()));
    }

    @Test
    public void testStartGame() throws RemoteException {
        UI ui = new Cli();
        InputParser input = null;
        GameObserver obs = new FsmGame(ui, input);
        Player player = new Player("Player1");
        controllerOfGame.start_Game();
        assertEquals(Status.ACTIVE, controllerOfGame.getStatus());
    }
    @Test
    public void testGoOn() throws RemoteException, MaxPlayersInException {
        //observer creation
        UI ui = new Cli();
        InputParser input = null;
        GameObserver obs = new FsmGame(ui, input);

        // Player creation
        Player player1 = new Player("Player1");
        Player player2 = new Player("Player2");

        // Creation of objects
        HashMap<Item, Integer> objects1 = new HashMap<>();
        objects1.put(Item.FEATHER, 1);
        objects1.put(Item.POTION, 1);
        objects1.put(Item.PARCHMENT, 1);

        HashMap<Item, Integer> objects2 = new HashMap<>();
        objects2.put(Item.PARCHMENT, 2); // Corretto da objects1.put

        // creation goal cards
        CheckInterface itemCheck = new ItemCheck();
        GoalCard goal1 = new GoalCard(99, true, 3, itemCheck, objects1);
        GoalCard goal2 = new GoalCard(100, true, 2, itemCheck, objects2);

        // List of goals cards
        ArrayList<GoalCard> testGoals = new ArrayList<>();
        testGoals.add(goal1);
        testGoals.add(goal2);
        controllerOfGame.addPlayer(player1);
        controllerOfGame.addObserver(obs,"Player1");
        controllerOfGame.readiness.put("Player1", 3);
        controllerOfGame.chooseGoal(testGoals, 99, "Player1");
        controllerOfGame.initializeTurn("Player1");
        controllerOfGame.addPlayer(player2);
        controllerOfGame.addObserver(obs, "Player2");
        controllerOfGame.readiness.put("Player2", 3);
        controllerOfGame.chooseGoal(testGoals, 99, "Player2");
        controllerOfGame.initializeTurn("Player2");
        controllerOfGame.goOn();
        //check that the current player has changed, if it was previously
        // player1 it is now player2 otherwise if it was previously player2 it is now player1
        if (controllerOfGame.getCurrentPlayer().equals("Player1")) {
            controllerOfGame.goOn();
            assertEquals("Player2", controllerOfGame.getCurrentPlayer());
        } else {
            controllerOfGame.goOn();
            assertEquals("Player1", controllerOfGame.getCurrentPlayer());
        }
    }

/**
    @Test
    public void testAssignBlackColor() throws RemoteException {
        // Let's add a player to the game to test the assignBlackColor method
        Player player1 = new Player("Player1");
        try {
            controllerOfGame.addPlayer(player1);
        } catch (MaxPlayersInException e) {
            //
        }
        // Create observer
        UI ui = new Cli();
        InputParser input = null;
        GameObserver obs = new FsmGame(ui, input);
        HashMap<Item, Integer> objects1 = new HashMap<>();
        objects1.put(Item.FEATHER, 1);
        objects1.put(Item.POTION, 1);
        objects1.put(Item.PARCHMENT, 1);

        HashMap<Item, Integer> objects2 = new HashMap<>();
        objects2.put(Item.PARCHMENT, 2);

        // goal Card
        CheckInterface itemCheck = new ItemCheck();
        GoalCard goal1 = new GoalCard(99, true, 3, itemCheck, objects1);
        GoalCard goal2 = new GoalCard(100, true, 2, itemCheck, objects2);
        ArrayList<GoalCard> testGoals = new ArrayList<>();
        testGoals.add(goal1);
        testGoals.add(goal2);
        // Add of Players and observers
        controllerOfGame.addObserver(obs, "Player1");

        controllerOfGame.readiness.put("Player1", 3);
        controllerOfGame.chooseGoal(testGoals, 99, "Player1");
        controllerOfGame.initializeTurn("Player1");
        // Verify that the assignBlackColor method works correctly
        controllerOfGame.assignBlackColor();
        ArrayList<Player> players = new ArrayList<>(controllerOfGame.getPlayers().keySet());
        Color actualColor = players.get(0).getOptionalColor().orElse(null);
        // Check that the player has been correctly assigned the color black
        assertEquals(Color.BLACK, actualColor);
    }
*/
    @Test
    public void setColor_WhenColorAvailable_ShouldSetCorrectly() throws MaxPlayersInException {
        String color = "blue";
        String playerName = "Player1";
        Player player1 = new Player("Player1");
        controllerOfGame.addPlayer(player1);
        controllerOfGame.setColor(color, playerName);
        ArrayList<Player> players = new ArrayList<>(controllerOfGame.getPlayers().keySet());
        Color colorTest = players.get(0).getColor();
        assertEquals(Color.BLUE, colorTest);
    }

    @Test
    public void setColor_WhenColorNotAvailable_ShouldNotSet() throws MaxPlayersInException {
        // Simulate the case where the color is not available
        String color = "pink";
        String playerName = "Player1";
        Player player1 = new Player("Player1");
        controllerOfGame.addPlayer(player1);
        try {
            controllerOfGame.setColor(color, playerName);
        }catch (IllegalArgumentException e){
            // Check that the player and pointTable color have not been set
            ArrayList<Player> players = new ArrayList<>(controllerOfGame.getPlayers().keySet());
            assertNull(players.get(0).getColor());
        }
    }

    @Test
    public void PlayCard_Resource() throws illegalOperationException, MaxPlayersInException, RemoteException {
        UI ui = new Cli();
        InputParser input = null;
        GameObserver obs = new FsmGame(ui, input);
        HashMap<Angle, Item> front = new HashMap<>();
        front.put(Angle.DOWNLEFT, Item.EMPTY);
        HashMap<Angle, Item> back = new HashMap<>();
        ResourceCard resourceCard = new ResourceCard(front, back, Collections.singletonList(Item.MUSHROOM), TypeOfCard.ANIMAL, true, 8, 1);
        Player player1 = new Player("TestPlayer");
        controllerOfGame.addPlayer(player1);
        HashMap<Angle, Item> frontItems = new HashMap<>();
        frontItems.put(Angle.HIGHRIGHT, Item.MUSHROOM);
        HashMap<Angle, Item> backItems = new HashMap<>();
        ArrayList<Item> backResource = new ArrayList<>();
        backResource.add(Item.MUSHROOM);
        backResource.add(Item.VEGETABLE);
        InitialCard initialCard = new InitialCard(1, true, frontItems, backItems, backResource);
        ArrayList<Player> players = new ArrayList<>(controllerOfGame.getPlayers().keySet());
        players.get(0).setGameStation(new GameStation(initialCard));
        ArrayList<PlayableCard> cards = new ArrayList<>();
        cards.add(resourceCard);
        players.get(0).setNickname("TestPlayer");
        players.get(0).setCards(cards);
        controllerOfGame.addPlayer(players.get(0));
        controllerOfGame.setColor("blue", "TestPlayer");
        CheckInterface itemCheck = new ItemCheck();
        HashMap<Item, Integer> objects1 = new HashMap<>();
        objects1.put(Item.FEATHER, 1);
        objects1.put(Item.POTION, 1);
        objects1.put(Item.PARCHMENT, 1);
        GoalCard goal1 = new GoalCard(99, true, 3, itemCheck, objects1);
        GoalCard goal2 = new GoalCard(100, true, 2, itemCheck, objects1);
        ArrayList<GoalCard> testGoals = new ArrayList<>();
        testGoals.add(goal1);
        testGoals.add(goal2);
        controllerOfGame.addPlayer(player1);
        controllerOfGame.readiness.put("TestPlayer", 3);
        controllerOfGame.addObserver(obs, "TestPlayer");
        controllerOfGame.chooseGoal(testGoals, 99, "TestPlayer");
        controllerOfGame.initializeTurn("TestPlayer");
        Point cord = new Point(1, 1);
        try {
            controllerOfGame.playCard(resourceCard, "TestPlayer", true, cord);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        Game game = controllerOfGame.returnGame();
        //after adding the card, check if you have reached the correct number of points
        assertEquals(game.getPointTable().getPoint(players.get(0)), 4);
        ArrayList<Player> players3 = new ArrayList<>(controllerOfGame.getPlayers().keySet());
        ////check that after playing the card there is nothing left in your hand
        assertTrue(players3.get(0).showCard().isEmpty());

    }

    @Test
    public void test_DrawPlayableCardFromTableOfDecks() throws MaxPlayersInException, RemoteException {
        //I have to initialize the game table (I use the game Controller method)
        //I have to see the hand has at least one card in hand (I do this for all decks)
        controllerOfGame.initializeTable();
        Player player1 = new Player("Player1");
        controllerOfGame.addPlayer(player1);
        controllerOfGame.drawPlayableCardFromTableOfDecks("gold", "Player1");
        ArrayList<Player> players = new ArrayList<>(controllerOfGame.getPlayers().keySet());
        assertEquals(players.get(0).showCard().size(), 1);
        assertTrue(players.get(0).showCard().get(0).getCardId() > 40);
        controllerOfGame.drawPlayableCardFromTableOfDecks("resource", "Player1");
        assertEquals(players.get(0).showCard().size(), 2);
        assertTrue(players.get(0).showCard().get(1).getCardId() <= 40);
        controllerOfGame.drawPlayableCardFromTableOfDecks("initial", "Player1");
        assertEquals(players.get(0).showCard().size(), 3);
        assertTrue(players.get(0).showCard().get(2).getCardId() > 80);

    }

    @Test
    public void test_DrawFromTable() throws MaxPlayersInException {
        //I need to initialize the game table
        //I have to see if I have at least one card in my hand and I have to see if I don't have nulls in the position I drew
        controllerOfGame.initializeTable();
        Player player1 = new Player("Player1");
        controllerOfGame.addPlayer(player1);
        Game game1 = controllerOfGame.returnGame();
        int num = game1.getTableOfDecks().getCards().get(0).getCardId();
        Card card = game1.getTableOfDecks().getCards().get(0);
        controllerOfGame.drawFromTable(card, "Player1");
        ArrayList<Player> players = new ArrayList<>(controllerOfGame.getPlayers().keySet());
        assertEquals(players.get(0).showCard().size(), 1);
        Game game2 = controllerOfGame.returnGame();
        assertNotNull(game2.getTableOfDecks().getCards().get(0));
        assertNotEquals(num, game2.getTableOfDecks().getCards().get(0).getCardId());
    }

    @Test
    public void test_ChooseGoal() throws MaxPlayersInException, RemoteException {
        Player player1 = new Player("Player1");
        UI ui = new Cli();
        InputParser input = null;
        GameObserver obs = new FsmGame(ui, input);
        controllerOfGame.addObserver(obs,"Player1");
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
        controllerOfGame.addPlayer(player1);
        controllerOfGame.readiness.put("Player1", 0);
        controllerOfGame.chooseGoal(test, 99, "Player1");
        ArrayList<Player> players = new ArrayList<>(controllerOfGame.getPlayers().keySet());
        assertEquals(players.get(0).getGoal().getCardId(), 99);
    }

    @Test
    public void testSetGameStation() throws RemoteException, MaxPlayersInException {
        // Creation of the player and the observer
        Player player1 = new Player("Player1");
        controllerOfGame.addPlayer(player1);
        UI ui = new Cli();
        InputParser input = null;
        GameObserver obs = new FsmGame(ui, input);
        controllerOfGame.addObserver(obs,"Player1");
        //initial Card
        HashMap<Angle, Item> frontItems = new HashMap<>();
        HashMap<Angle, Item> backItems = new HashMap<>();
        ArrayList<Item> backResource = new ArrayList<>();
        backResource.add(Item.MUSHROOM);
        backResource.add(Item.VEGETABLE);
        InitialCard initialCard  = new InitialCard(1, true, frontItems, backItems, backResource);
        controllerOfGame.readiness.put("Player1", 0);
        // Call the setGameStation method
        controllerOfGame.setGameStation("Player1", initialCard, true);
        // Verify that the player's GameStation has been set up correctly
        Point point = new Point(0, 0);
        assertFalse(player1.getGameStation().getFreeCords().contains(point));

    }
    @Test
    public void testChangePlayerStatus() throws MaxPlayersInException, RemoteException {
        // Change the state of Player1
        UI ui = new Cli();
        InputParser input = null;
        GameObserver obs = new FsmGame(ui, input);
        Player player1 = new Player("Player1");
        controllerOfGame.addObserver(obs, "Player1");
        controllerOfGame.addPlayer(player1);
        controllerOfGame.changePlayerStatus("Player1", false);
        controllerOfGame.getPlayers();
        // Verify that the player's state has been changed
        assertEquals(false, controllerOfGame.getPlayers().get(player1));

    }
}