package it.polimi.ingsw.model.card;

import java.util.*;

/**
 * @author Luca Lamperti
 * define a subClass GoalCard of superclass Card
 * numOfPoints is an int and indicates the number of points on the GoalCard
 * ListOfObjects is an ArrayList used to display the resources needed to earn the points
 * GoalType indicates the type of requirement(from the enum TypeOfGoalCard)
 * positionType indicates the position of the cards needed to earn points(if the TypeOfGoalCard is POSITION) or it is "EMPTY" in the case there are no position requirements
 */
public class GoalCard extends Card {
    private final int numOfPoints;
    private final List<Item> listOfObjects;
    private final TypeOfGoalCard goalType;
    private final TypeOfPositioning positionType;

    /**
     * @author Luca Lamperti
     * the constructor of the GoalCard class
     * @param type is the type of the GoalCard(from enum TypeOfCard)
     * @param isFront show how the card is displayed
     * @param points are the points on the GoalCard
     * @param goalType is the requirement type of the GoalCard
     * @param positionType is the position requirement(it is "EMPTY" in case there is no position requirement)
     * @param objects is a Stack that contains the items needed to get points(this is the case of a typeOfGoalCard OBJECT or RESOURCE)
     */
    public GoalCard(TypeOfCard type, boolean isFront, int points, TypeOfGoalCard goalType, TypeOfPositioning positionType, ArrayList<Item> objects){
        super(type, isFront);
        this.numOfPoints = points;
        this.goalType = goalType;
        this.positionType = positionType;
        this.listOfObjects = new ArrayList<>(objects);
    }

    /**
     * @author Lodetti Alessandro
     * this constructor is useful to initialize the goal card by reading from a json file.
     * @param card this is the object read from the json file
     */
    public GoalCard(GoalCard card){
        super(card.getType(), card.isFront());
        this.numOfPoints = card.getNumberOfPoints();
        this.listOfObjects = new ArrayList<>(card.getListOfObjects());
        this.goalType = card.getGoalType();
        this.positionType = card.getPositionType();
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
    public TypeOfGoalCard getGoalType() {
        return goalType;
    }

    /**
     * @author Luca Lamperti
     * getter for the attribute PositionType
     * @return an enum that indicates the position requirement
     */
    public TypeOfPositioning getPositionType() {
        return positionType;
    }

    /**
     * @author Luca Lamperti
     * getter for the attribute listOfObjects
     * @return an ArrayList that contains the items needed to earn the NumOfPoints of the GoalCard
     */
    public ArrayList<Item> getListOfObjects() {
        return new ArrayList<>(listOfObjects);
    }
}
