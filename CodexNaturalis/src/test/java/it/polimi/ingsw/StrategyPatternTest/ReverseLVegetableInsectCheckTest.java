package it.polimi.ingsw.StrategyPatternTest;

import it.polimi.ingsw.model.card.*;
import it.polimi.ingsw.model.card.strategyPattern.ReverseLVegetableInsectCheck;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.*;

import static org.junit.Assert.assertEquals;

public class ReverseLVegetableInsectCheckTest {
    private HashMap<Point, PlayableCard> playedCards;
    private GoalCard goalCard;

    @BeforeEach
    public void SetUp() {
        playedCards = new HashMap<>();
        playedCards.put(new Point(0, 0), new InitialCard(123, true, new HashMap<>(), new HashMap<>(), new ArrayList<>()));
        goalCard = new GoalCard(0, true, 1, new ReverseLVegetableInsectCheck(), new HashMap<>());
    }

    private void SetCards(int num){
        for(int i = 1; i <= num; i++){
            playedCards.put(new Point(-i, i), new ResourceCard(new HashMap<>(), new HashMap<>(), new ArrayList<>(), TypeOfCard.VEGETABLE, true, 123, 0));
            playedCards.put(new Point(-i, i -2), new ResourceCard(new HashMap<>(), new HashMap<>(), new ArrayList<>(), TypeOfCard.VEGETABLE, true, 123, 0));
            playedCards.put(new Point(-i -1, i-3), new ResourceCard(new HashMap<>(), new HashMap<>(), new ArrayList<>(), TypeOfCard.INSECT, true, 123, 0));
        }
    }

    private void SetCardsMixed(int num){
        for(int i = -num*2 + 1; i <= num*2 -1; i = i +2 ){
            playedCards.put(new Point(-1, i), new ResourceCard(new HashMap<>(), new HashMap<>(), new ArrayList<>(), TypeOfCard.VEGETABLE, true, 123, 0));
            playedCards.put(new Point(-2, i-1), new ResourceCard(new HashMap<>(), new HashMap<>(), new ArrayList<>(), TypeOfCard.INSECT, true, 123, 0));
        }
    }

    @Test
    public void TestInitialCardOnly() {
        assertEquals(0, goalCard.getGoalPoints(playedCards, new HashMap<>()));
    }
    @Test
    public void TestOneCardOnly() {
        playedCards.put(new Point(-1, -1), new ResourceCard(new HashMap<>(), new HashMap<>(), new ArrayList<>(), TypeOfCard.INSECT, true, 123, 0));
        assertEquals(0, goalCard.getGoalPoints(playedCards, new HashMap<>()));
    }
    @Test
    public void TestSecondCardWrong() {
        playedCards.put(new Point(1, -1), new ResourceCard(new HashMap<>(), new HashMap<>(), new ArrayList<>(), TypeOfCard.ANIMAL, true, 123, 0));
        playedCards.put(new Point(-2, -2), new ResourceCard(new HashMap<>(), new HashMap<>(), new ArrayList<>(), TypeOfCard.INSECT, true, 123, 0));
        assertEquals(0, goalCard.getGoalPoints(playedCards, new HashMap<>()));
    }
    @Test
    public void TestThirdCardWrong() {
        playedCards.put(new Point(-1, 1), new ResourceCard(new HashMap<>(), new HashMap<>(), new ArrayList<>(), TypeOfCard.MUSHROOM, true, 123, 0));
        playedCards.put(new Point(-1, -1), new ResourceCard(new HashMap<>(), new HashMap<>(), new ArrayList<>(), TypeOfCard.VEGETABLE, true, 123, 0));
        playedCards.put(new Point(-2, -2), new ResourceCard(new HashMap<>(), new HashMap<>(), new ArrayList<>(), TypeOfCard.INSECT, true, 123, 0));
        assertEquals(0, goalCard.getGoalPoints(playedCards, new HashMap<>()));
    }
    @Test
    public void TestOnePoint() {
        SetCards(1);
        assertEquals(1, goalCard.getGoalPoints(playedCards, new HashMap<>()));
    }
    @Test
    public void TestTwoPoint() {
        SetCards(2);
        assertEquals(2, goalCard.getGoalPoints(playedCards, new HashMap<>()));
    }
    @Test
    public void TestTWoPointsMixed() {
        SetCardsMixed(2);
        assertEquals(2, goalCard.getGoalPoints(playedCards, new HashMap<>()));
    }
    @Test
    public void TestThreePointsMixed() {
        SetCardsMixed(3);
        assertEquals(3, goalCard.getGoalPoints(playedCards, new HashMap<>()));
    }
    @Test
    public void TestFourPointsMixed() {
        SetCardsMixed(4);
        assertEquals(4, goalCard.getGoalPoints(playedCards, new HashMap<>()));
    }
    @Test
    public void TestFivePointsMixed() {
        SetCardsMixed(5);
        assertEquals(5, goalCard.getGoalPoints(playedCards, new HashMap<>()));
    }
}