package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.card.strategyPattern.CheckInterface;
import it.polimi.ingsw.model.card.strategyPattern.ItemCheck;

import java.awt.*;
import java.util.*;

/**
 * define a subClass GoalCard of superclass Card
 * numOfPoints is an int and indicates the number of points on the GoalCard
 * ListOfObjects is a Hashmap used to display the resources needed to earn the points
 * GoalType indicates the type of requirement(from the enum TypeOfGoalCard)
 * positionType indicates the position of the cards needed to earn points(if the TypeOfGoalCard is POSITION) or it is "EMPTY" in the case there are no position requirements
 * @author Luca Lamperti
 */
public class GoalCard extends Card {
    private final int numOfPoints;
    private final HashMap<Item, Integer> listOfObjects;
    private final CheckInterface goalType;

    /**
     * the constructor of the GoalCard class
     * @author Luca Lamperti
     * @param id it's the identification of the card
     * @param isFront show how the card is displayed
     * @param points are the points on the GoalCard
     * @param goalType is the requirement type of the GoalCard
     * @param objects is a HashMap that contains the items needed to get points(this is the case of a typeOfGoalCard OBJECT or RESOURCE)
     */
    public GoalCard(int id, boolean isFront, int points, CheckInterface goalType, HashMap<Item, Integer> objects){
        super(id, TypeOfCard.GOAL, isFront);
        this.numOfPoints = points;
        this.goalType = goalType;
        this.listOfObjects = new HashMap<>(objects);
    }

    /**
     * this constructor is useful to initialize the goal card by reading from a json file.
     * @author Lodetti Alessandro
     * @param card this is the object read from the json file
     */
    public GoalCard(GoalCard card){
        super(card.getCardId(), card.getType(), card.isFront());
        this.numOfPoints = card.getNumberOfPoints();
        this.listOfObjects = new HashMap<Item, Integer>(card.getListOfObjects());
        this.goalType = card.getGoalType();
    }
    /**
     * getter for the attribute NumOfPoints
     * @author Luca Lamperti
     * @return an int as the points
     */
    public int getNumberOfPoints(){
        return numOfPoints;
    }

    /**
     * getter for the attribute GoalType
     * @author Luca Lamperti
     * @return an enum that indicates the TypeOfGoalCard
     */
    public CheckInterface getGoalType() {
        return goalType;
    }

    /**
     * getter for the attribute listOfObjects
     * @author Luca Lamperti
     * @return an ArrayList that contains the items needed to earn the NumOfPoints of the GoalCard
     */
    public HashMap<Item, Integer> getListOfObjects() {
        return new HashMap<>(listOfObjects);
    }

    public int getGoalPoints(HashMap<Point, PlayableCard> playedCard, HashMap<Item, Integer> availableItems){
        return this.numOfPoints * this.goalType.check(playedCard, availableItems, this.listOfObjects);
    }
}
