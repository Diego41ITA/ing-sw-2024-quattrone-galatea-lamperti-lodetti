package it.polimi.ingsw.StrategyPatternTest;
import it.polimi.ingsw.model.card.*;
import it.polimi.ingsw.model.card.strategyPattern.DiagonalInsectCheck;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.*;

import static org.junit.Assert.*;

public class DiagonalInsectCheckTest {

    private HashMap<Point, PlayableCard> playedCards;
    private GoalCard goalCard;

    @BeforeEach
    public void SetUp() {
        playedCards = new HashMap<>();
        playedCards.put(new Point(0, 0), new InitialCard(123, true, new HashMap<>(), new HashMap<>(), new ArrayList<>()));
        goalCard = new GoalCard(0, true, 1, new DiagonalInsectCheck(), new HashMap<>());
    }

    private void SetCards(int num) {
        for (int i = 1; i <= num; i++) {
            playedCards.put(new Point(i, -i), new ResourceCard(new HashMap<>(), new HashMap<>(), new ArrayList<>(), TypeOfCard.INSECT, true, 123 + i, 0));
        }
    }

    @Test
    public void TestInitialCardOnly() {
        assertEquals(0, goalCard.getGoalPoints(playedCards, new HashMap<>()));
    }
    @Test
    public void TestSecondCardWrong() {
        SetCards(1);
        playedCards.put(new Point(2, -2), new ResourceCard(new HashMap<>(), new HashMap<>(), new ArrayList<>(), TypeOfCard.MUSHROOM, true, 123, 0));
        assertEquals(0, goalCard.getGoalPoints(playedCards, new HashMap<>()));
    }
    @Test
    public void TestThirdCardWrong() {
        SetCards(2);
        playedCards.put(new Point(3, -3), new ResourceCard(new HashMap<>(), new HashMap<>(), new ArrayList<>(), TypeOfCard.MUSHROOM, true, 123, 0));
        assertEquals(0, goalCard.getGoalPoints(playedCards, new HashMap<>()));
    }
    @Test
    public void TestThreeCards() {
        SetCards(3);
        assertEquals(1, goalCard.getGoalPoints(playedCards, new HashMap<>()));
    }

    @Test
    public void TestSixCards() {
        SetCards(6);
        assertEquals(2, goalCard.getGoalPoints(playedCards, new HashMap<>()));
    }

    @Test
    public void TestNineCards() {
        SetCards(9);
        assertEquals(3, goalCard.getGoalPoints(playedCards, new HashMap<>()));
    }

    @Test
    public void TestTwelveCards() {
        SetCards(12);
        assertEquals(4, goalCard.getGoalPoints(playedCards, new HashMap<>()));
    }

    @Test
    public void TestFifteenCards() {
        SetCards(15);
        assertEquals(5, goalCard.getGoalPoints(playedCards, new HashMap<>()));
    }

    @Test
    public void TestMaxCards() {
        SetCards(20);
        assertEquals(6, goalCard.getGoalPoints(playedCards, new HashMap<>()));
    }
}
