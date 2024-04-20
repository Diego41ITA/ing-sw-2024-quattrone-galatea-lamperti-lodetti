package it.polimi.ingsw.StrategyPatternTest;
import it.polimi.ingsw.model.card.*;
        import it.polimi.ingsw.model.card.strategyPattern.DiagonalAnimalCheck;
import it.polimi.ingsw.model.card.strategyPattern.DiagonalMushroomCheck;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.*;
import static org.junit.Assert.*;

public class DiagonalMushroomCheckTest {

    private HashMap<Point, PlayableCard> playedCards;
    private GoalCard goalCard;

    @BeforeEach
    public void SetUp() {
        playedCards = new HashMap<>();
        playedCards.put(new Point(0, 0), new InitialCard(123, true, setFrontAngles(), SetbackAngles(), SetBack()));
        goalCard = new GoalCard(0, true, 1, new DiagonalMushroomCheck(), new HashMap<>());
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
        for(int i = 1; i<=num; i++){
            playedCards.put(new Point(i, i), new ResourceCard(setFrontAngles(), SetbackAngles(), SetBack(), TypeOfCard.MUSHROOM, true, 123 + i, 0));
        }
    }

    @Test
    public void TestInitialCardOnly() {
        assertEquals(0, goalCard.getGoalPoints(playedCards, new HashMap<>()));
    }
    @Test
    public void TestOneCard() {
        SetCards(1);
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