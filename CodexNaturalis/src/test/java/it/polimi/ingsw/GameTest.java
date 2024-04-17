package it.polimi.ingsw;
import it.polimi.ingsw.model.gameDataManager.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
        public void testAddPlayer() {
            game.addPlayer(player1);
            game.addPlayer(player2);
            assertEquals(2, game.getPlayers().size());
            assertTrue(game.getPlayers().containsKey(player1));
            assertTrue(game.getPlayers().containsKey(player2));
        }

        @Test
        public void testGameStatusTransitions() {
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
        public void testPlayerConnection() {
            game.addPlayer(player1);
            game.addPlayer(player2);
            assertTrue(game.isConnected(player1));
            assertTrue(game.isConnected(player2));
        }

        @Test
        public void testSetMaxNumberPlayer() {
            game.setMaxNumberPlayer(2);
            game.addPlayer(player1);
            game.addPlayer(player2);
            assertThrows(IllegalStateException.class, () -> game.addPlayer(player3)); // Adding the third player should fail
            // Ensure maxNumberPlayer is clamped within valid range (2 to 4)
            game.setMaxNumberPlayer(1);
            assertEquals(2, game.getMaxNumberPlayer());
            game.setMaxNumberPlayer(10);
            assertEquals(4, game.getMaxNumberPlayer());
        }

        @Test
        public void testCheckName() {
            game.addPlayer(player1);
            game.addPlayer(player2);

            // Checking non-existing and existing player names
            assertFalse(game.checkName("Luca"));
            assertTrue(game.checkName("Alessandro"));
        }
    }

