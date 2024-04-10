package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.card.strategyPattern.CheckInterface;

import java.awt.*;
import java.util.*;

/**
 * @author Luca Lamperti
 * define a subClass GoalCard of superclass Card
 * numOfPoints is an int and indicates the number of points on the GoalCard
 * ListOfObjects is a Hashmap used to display the resources needed to earn the points
 * GoalType indicates the type of requirement(from the enum TypeOfGoalCard)
 * positionType indicates the position of the cards needed to earn points(if the TypeOfGoalCard is POSITION) or it is "EMPTY" in the case there are no position requirements
 */
public class GoalCard extends Card {
    private final int numOfPoints;
    private final HashMap<Item, Integer> listOfObjects;
    //private final TypeOfGoalCard goalType;
    private final CheckInterface goalType;

    //private final TypeOfPositioning positionType;

    /**
     * @author Luca Lamperti
     * the constructor of the GoalCard class
     * @param type is the type of the GoalCard(from enum TypeOfCard)
     * @param isFront show how the card is displayed
     * @param points are the points on the GoalCard
     * @param goalType is the requirement type of the GoalCard
     * @param positionType is the position requirement(it is "EMPTY" in case there is no position requirement)
     * @param objects is a HashMap that contains the items needed to get points(this is the case of a typeOfGoalCard OBJECT or RESOURCE)
     */
    public GoalCard(TypeOfCard type, boolean isFront, int points, CheckInterface goalType, TypeOfPositioning positionType, HashMap<Item, Integer> objects){
        super(type, isFront);
        this.numOfPoints = points;
        this.goalType = goalType;
//        this.positionType = positionType;
        this.listOfObjects = new HashMap<>(objects);
    }

    /**
     * @author Lodetti Alessandro
     * this constructor is useful to initialize the goal card by reading from a json file.
     * @param card this is the object read from the json file
     */
    public GoalCard(GoalCard card){
        super(card.getType(), card.isFront());
        this.numOfPoints = card.getNumberOfPoints();
        this.listOfObjects = new HashMap<Item, Integer>(card.getListOfObjects());
        this.goalType = card.getGoalType();
        //        this.positionType = card.getPositionType();
    }


    /**
     * @author Luca Lamperti
     * getter for the attribute NumOfPoints
     * @return an int as the points
     */
    public int getNumberOfPoints(){
        return numOfPoints;
    }

    /**
     * @author Luca Lamperti
     * getter for the attribute GoalType
     * @return an enum that indicates the TypeOfGoalCard
     */
    public CheckInterface getGoalType() {
        return goalType;
    }

    /**
     * @author Luca Lamperti
     * getter for the attribute PositionType
     * @return an enum that indicates the position requirement
     */
    /*
    public TypeOfPositioning getPositionType() {
        return positionType;
    }
    */
    /**
     * @author Luca Lamperti
     * getter for the attribute listOfObjects
     * @return an ArrayList that contains the items needed to earn the NumOfPoints of the GoalCard
     */
    public HashMap<Item, Integer> getListOfObjects() {
        return new HashMap<>(listOfObjects);
    }

    public Boolean checkGoal(HashMap<Point, PlayableCard> playedCard, HashMap<Item, Integer> availableItems){
        return this.goalType.check(playedCard, availableItems, this.listOfObjects);
    }
}
