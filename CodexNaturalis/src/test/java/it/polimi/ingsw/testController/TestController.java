
        package it.polimi.ingsw.testController;
        import static org.junit.jupiter.api.Assertions.*;
        import it.polimi.ingsw.controller.*;
        import it.polimi.ingsw.model.exceptions.MaxPlayersInException;
        import it.polimi.ingsw.model.gameDataManager.*;
        import it.polimi.ingsw.observer.GameObserver;
        import it.polimi.ingsw.view.GameFlow;
        import it.polimi.ingsw.view.TUI.Cli;
        import it.polimi.ingsw.view.UI;
        import org.junit.jupiter.api.BeforeEach;
        import org.junit.jupiter.api.Test;
        import java.rmi.RemoteException;
        import java.util.ArrayList;
        import java.util.HashMap;
        import it.polimi.ingsw.model.card.*;
        import it.polimi.ingsw.model.card.strategyPattern.CheckInterface;
        import it.polimi.ingsw.model.card.strategyPattern.ItemCheck;
        import it.polimi.ingsw.model.exceptions.illegalOperationException;
        import it.polimi.ingsw.model.gameDataManager.Color;
        import java.awt.*;
        import java.util.Collections;
public class TestController {

    private MainController mainController;

    private GameController gameController;

    @BeforeEach
    public void setUp() {
        mainController = MainController.getMainController();
        gameController = new GameController("Game1", 4);
    }

    @Test
    public void testConstructor() {
        assertNotNull(gameController);
        assertNotNull(gameController.getObservers());
        assertEquals("Game1", gameController.getGameId());
    }

    @Test
    public void testCreateGame() throws RemoteException {
        UI ui = new Cli();
        GameObserver obs = new GameFlow(ui);
        GameControllerInterface gameController = mainController.createGame(obs, "Player1", 4);
        assertNotNull(gameController);
        assertEquals(1, mainController.getActiveGames().size());
        assertEquals("Game1", gameController.getGameId());

    }

    @Test// manca verifica notify
    public void testJoinRandomGame() throws RemoteException {
        UI ui = new Cli();
        GameObserver obs = new GameFlow(ui);
        GameControllerInterface gameController = mainController.joinRandomGame(obs, "Player1");
        assertNull(gameController);
        GameControllerInterface gameController1 = mainController.createGame(obs, "Player1", 2);
        UI ui2 = new Cli();
        GameObserver obs2 = new GameFlow(ui2);
        GameControllerInterface gameController2 = mainController.joinRandomGame(obs2, "Player2");
        assertNotNull(gameController2);
        assertEquals(2, gameController1.getPlayers().size());
    }

    @Test
    public void testLeaveGame() throws RemoteException {
        // Aggiungiamo un gioco attivo per il test
        UI ui = new Cli();
        GameObserver obs = new GameFlow(ui);
        GameControllerInterface gameController = mainController.createGame(obs, "Player1", 4);
        assertNotNull(gameController);
        // Verifica che il metodo leaveGame funzioni correttamente
        GameControllerInterface leftGameController = mainController.leaveGame(obs, "Player1", gameController.getGameId());
        assertNull(leftGameController); // Il metodo restituisce null perché il gioco è ancora attivo
    }

    @Test
    public void testDeleteGame() throws RemoteException {
        // Aggiungiamo un gioco attivo per il test
        UI ui = new Cli();
        GameObserver obs = new GameFlow(ui);
        GameControllerInterface gameController = mainController.createGame(obs, "Player1", 4);
        assertNotNull(gameController);
        // Verifica che il metodo deleteGame funzioni correttamente
        mainController.deleteGame(gameController.getGameId());
        assertEquals(0, mainController.getActiveGames().size()); // Il gioco dovrebbe essere stato rimosso dalla lista dei giochi attivi
    }

    @Test
    public void testAddObserver() throws RemoteException {
        UI ui = new Cli();
        GameObserver obs = new GameFlow(ui);
        Player player = new Player("Player1");
        gameController.addObserver(obs, player);
        assertTrue(gameController.getObservers().containsKey(player.getNick()));
    }

    @Test
    public void testRemoveObserver() throws RemoteException {
        UI ui = new Cli();
        GameObserver obs = new GameFlow(ui);
        Player player = new Player("Player1");
        gameController.addObserver(obs, player);
        gameController.removeObserver(player);
        assertFalse(gameController.getObservers().containsKey(player.getNick()));
    }

    @Test//manca verifica notify
    public void testStartGame() throws RemoteException {
        UI ui = new Cli();
        GameObserver obs = new GameFlow(ui);
        Player player = new Player("Player1");
        gameController.addObserver(obs, player);
        assertEquals(Status.ACTIVE, gameController.getStatus());
    }

    @Test
    public void testGoOn() {
        // Aggiungere giocatori al gioco per testare il metodo goOn
        Player player1 = new Player("Player1");
        Player player2 = new Player("Player2");
        try {
            gameController.addPlayer(player1);
            gameController.addPlayer(player2);
        } catch (MaxPlayersInException e) {
            //
        }
        if (gameController.getCurrentPlayer().equals("Player1")) {
            gameController.goOn();
            assertEquals("Player2", gameController.getCurrentPlayer());
        } else {
            gameController.goOn();
            assertEquals("Player1", gameController.getCurrentPlayer());
        }

    }

    @Test
    public void testAssignBlackColor() {
        // Aggiungiamo un giocatore al gioco per testare il metodo assignBlackColor
        Player player1 = new Player("Player1");
        try {
            gameController.addPlayer(player1);
        } catch (MaxPlayersInException e) {
            //
        }
        // Verifica che il metodo assignBlackColor funzioni correttamente
        gameController.assignBlackColor();

        ArrayList<Player> players = new ArrayList<>(gameController.getPlayers().keySet());

        // Verifica che al giocatore sia stato assegnato correttamente il colore nero
        assertEquals(Color.BLACK, players.get(0).getOptionalColor());
    }

    @Test
    public void setColor_WhenColorAvailable_ShouldSetCorrectly() throws MaxPlayersInException {
        String color = "blue";
        String playerName = "Player1";
        Player player1 = new Player("Player1");
        gameController.addPlayer(player1);
        gameController.setColor(color, playerName);
        ArrayList<Player> players = new ArrayList<>(gameController.getPlayers().keySet());
        Color colorTest = players.get(0).getColor();
        assertEquals(Color.BLUE, colorTest);
    }

    @Test
    public void setColor_WhenColorNotAvailable_ShouldNotSet() throws MaxPlayersInException {
        // Simula il caso in cui il colore non sia disponibile
        String color = "pink"; // Colore non disponibile
        String playerName = "Player1"; // Nome del giocatore
        Player player1 = new Player("Player1");
        gameController.addPlayer(player1);
        gameController.setColor(color, playerName);
        // Verifica che il colore del giocatore e del pointTable non sia stato impostato
        ArrayList<Player> players = new ArrayList<>(gameController.getPlayers().keySet());
        assertNull(players.get(0).getColor());
    }

    @Test
    public void PlayCard_Resource() throws illegalOperationException, MaxPlayersInException {
        HashMap<Angle, Item> front = new HashMap<>();
        HashMap<Angle, Item> back = new HashMap<>();
        ResourceCard resourceCard = new ResourceCard(front, back, Collections.singletonList(Item.MUSHROOM), TypeOfCard.ANIMAL, true, 8, 1);
        Player player1 = new Player("Player1");
        gameController.addPlayer(player1);
        ArrayList<Player> players = new ArrayList<>(gameController.getPlayers().keySet());
        HashMap<Angle, Item> frontItems = new HashMap<>();
        HashMap<Angle, Item> backItems = new HashMap<>();
        ArrayList<Item> backResource = new ArrayList<>();
        backResource.add(Item.MUSHROOM);
        backResource.add(Item.VEGETABLE);
        InitialCard initialCard = new InitialCard(1, true, frontItems, backItems, backResource);
        players.get(0).setGameStation(new GameStation(initialCard));
        ArrayList<PlayableCard> cards = new ArrayList<>();
        cards.add(resourceCard);
        players.get(0).setNickname("testPlayer");
        players.get(0).setCards(cards);
        gameController.addPlayer(players.get(0));
        gameController.setColor("blue", "testPlayer");
        Point cord = new Point(1, 1);
        gameController.playCard(resourceCard, "testPlayer", true, cord);
        Game game = gameController.returnGame();
        assertEquals(game.getPointTable().getPoint(players.get(0)), 1);
        ArrayList<Player> players3 = new ArrayList<>(gameController.getPlayers().keySet());
        if (players3.get(0).getNick().equals("testPlayer")) {
            assertTrue(players3.get(0).showCard().isEmpty());
        } else {
            assertTrue(players3.get(1).showCard().isEmpty());
        }

    }

    @Test
    public void test_DrawPlayableCardFromTableOfDecks() throws MaxPlayersInException {
        //devo inizializzare il tavolo da gioco(faccio uso il metodo di game Controller)
        //devo vedere la mano ha almeno una carta in mano(lo faccio per tutti i mazzi)
        gameController.initializeTable();
        Player player1 = new Player("Player1");
        gameController.addPlayer(player1);
        gameController.drawPlayableCardFromTableOfDecks("gold", "Player1");
        ArrayList<Player> players = new ArrayList<>(gameController.getPlayers().keySet());
        assertEquals(players.get(0).showCard().size(), 1);
        assertTrue(players.get(0).showCard().get(0).getCardId() > 40);
        gameController.drawPlayableCardFromTableOfDecks("resource", "Player1");
        assertEquals(players.get(0).showCard().size(), 2);
        assertTrue(players.get(0).showCard().get(1).getCardId() < 40);
        gameController.drawPlayableCardFromTableOfDecks("initial", "Player1");
        assertEquals(players.get(0).showCard().size(), 3);
        assertTrue(players.get(0).showCard().get(2).getCardId() > 80);

    }

    @Test
    public void test_DrawFromTable() throws MaxPlayersInException {
        //devo inizializzare il tavolo da gioco
        //devo vedere se ho almeno una carta in mano e devo vedere se nella posizione in cui ho pescato non ho null
        gameController.initializeTable();
        Player player1 = new Player("Player1");
        gameController.addPlayer(player1);
        Game game1 = gameController.returnGame();
        int num = game1.getTableOfDecks().getCards().get(0).getCardId();
        Card card = game1.getTableOfDecks().getCards().get(0);
        gameController.drawFromTable(card, "Player1");
        ArrayList<Player> players = new ArrayList<>(gameController.getPlayers().keySet());
        assertEquals(players.get(0).showCard().size(), 1);
        Game game2 = gameController.returnGame();
        assertNotNull(game2.getTableOfDecks().getCards().get(0));
        assertNotEquals(num, game2.getTableOfDecks().getCards().get(0).getCardId());
    }

    @Test
    public void test_ChooseGoal() throws MaxPlayersInException, RemoteException {
        Player player1 = new Player("Player1");
        UI ui = new Cli();
        GameObserver obs = new GameFlow(ui);
        gameController.addObserver(obs,player1);
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
        gameController.chooseGoal(test, 0, "Player1");
        ArrayList<Player> players = new ArrayList<>(gameController.getPlayers().keySet());
        assertEquals(players.get(0).getGoal().getCardId(), 99);
    }

}