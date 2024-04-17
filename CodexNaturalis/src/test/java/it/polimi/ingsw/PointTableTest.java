package it.polimi.ingsw;
import it.polimi.ingsw.model.gameDataManager.*;
import it.polimi.ingsw.model.gameDataManager.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

    public class PointTableTest {
        private PointTable pointTable;
        private Player player1;
        private Player player2;

        @BeforeEach
        public void setUp() {
            pointTable = new PointTable();
            player1 = new Player();
            player2 = new Player();
            player1.setNickname("Lorenzo");
            player2.setNickname("Luca");
            player1.setColor(Color.RED);
            player2.setColor(Color.BLUE);
        }

        @Test
        public void testSetColorPoints() {
            pointTable.setColorPoints(Color.RED);
            assertEquals(0, pointTable.getPoint(player1)); // Check if player1's points are initialized to 0
        }

        @Test
        public void testGetPoint() {
            pointTable.setColorPoints(Color.RED);
            pointTable.updatePoint(player1, 10);
            assertEquals(10, pointTable.getPoint(player1)); // Check if player1's points are updated correctly
        }
        @Test
        public void testNotify20PointReached() {
            pointTable.setColorPoints(Color.RED);
            pointTable.setColorPoints(Color.BLUE);

            // Update player points
            pointTable.updatePoint(player1, 18); // Below 20 points
            pointTable.updatePoint(player2, 25); // Above 20 points

            assertTrue(pointTable.notify20PointReached()); // Check if 20 point limit is reached
        }

        @Test
        public void testResetPoints() {
            pointTable.setColorPoints(Color.RED);
            pointTable.updatePoint(player1, 15);

            pointTable.resetPoints();
            assertEquals(0, pointTable.getPoint(player1)); // Check if player1's points are reset to 0
        }
    }
