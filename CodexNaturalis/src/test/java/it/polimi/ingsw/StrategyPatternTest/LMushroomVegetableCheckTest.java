package it.polimi.ingsw.StrategyPatternTest;

import it.polimi.ingsw.model.card.*;
import it.polimi.ingsw.model.card.strategyPattern.LMushroomVegetableCheck;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.*;

import static org.junit.Assert.assertEquals;

public class LMushroomVegetableCheckTest {
    private HashMap<Point, PlayableCard> playedCards;
    private GoalCard goalCard;

    @BeforeEach
    public void SetUp() {
        playedCards = new HashMap<>();
        playedCards.put(new Point(0, 0), new InitialCard(123, true, setFrontAngles(), SetbackAngles(), SetBack()));
        goalCard = new GoalCard(0, true, 1, new LMushroomVegetableCheck(), new HashMap<>());
    }

    private HashMap<Angle, Item> setFrontAngles(){
        HashMap<Angle, Item> frontAngles = new HashMap<>();
        frontAngles.put(Angle.HIGHLEFT, Item.ANIMAL);
        frontAngles.put(Angle.HIGHRIGHT, Item.VEGETABLE);
        frontAngles.put(Angle.DOWNLEFT, Item.INSECT);
        frontAngles.put(Angle.DOWNRIGHT, Item.ANIMAL);
        return frontAngles;
    }

    private HashMap<Angle, Item> SetbackAngles(){
        HashMap<Angle, Item> backAngles = new HashMap<>();
        backAngles.put(Angle.HIGHLEFT, Item.ANIMAL);
        backAngles.put(Angle.HIGHRIGHT, Item.ANIMAL);
        backAngles.put(Angle.DOWNLEFT, Item.VEGETABLE);
        backAngles.put(Angle.DOWNRIGHT, Item.VEGETABLE);
        return backAngles;
    }

    private ArrayList<Item> SetBack(){
        ArrayList<Item> back = new ArrayList<>();
        back.add(Item.ANIMAL);
        return back;
    }

    private void SetCards(int num){
        for(int i = 1; i <= num; i++){
            playedCards.put(new Point(i, i), new ResourceCard(setFrontAngles(), SetbackAngles(), SetBack(), TypeOfCard.MUSHROOM, true, 123, 0));
            playedCards.put(new Point(i, i -2), new ResourceCard(setFrontAngles(), SetbackAngles(), SetBack(), TypeOfCard.MUSHROOM, true, 123, 0));
            playedCards.put(new Point(i +1, i-3), new ResourceCard(setFrontAngles(), SetbackAngles(), SetBack(), TypeOfCard.VEGETABLE, true, 123, 0));
        }
    }

    private void SetCardsMixed(int num){
        for(int i = -num*2 + 1; i <= num*2 -1; i = i +2 ){
            playedCards.put(new Point(1, i), new ResourceCard(setFrontAngles(), SetbackAngles(), SetBack(), TypeOfCard.MUSHROOM, true, 123, 0));
            playedCards.put(new Point(2, i-1), new ResourceCard(setFrontAngles(), SetbackAngles(), SetBack(), TypeOfCard.VEGETABLE, true, 123, 0));
            }
    }

    @Test
    public void TestInitialCardOnly() {
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
