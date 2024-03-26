/**
 * @author Luca Lamperti
 * define a subClass GoalCard of superclass Card
 * numOfPoints is an int and indicates the number of points on the GoalCard
 * ListOfObjects is an ArrayList used to display the resources needed to earn the points
 * GoalType indicates the type of requirement(from the enum TypeOfGoalCard)
 * positionType indicates the position of the cards needed to earn points(if the TypeOfGoalCard is POSITION) or it is "EMPTY" in the case there are no position requirements
 */

package it.polimi.ingsw.model.card;

import java.util.ArrayList;

public class GoalCard extends Card {
    private final int numOfPoints;
    private final ArrayList<Item> listOfObjects;
    private final TypeOfGoalCard goalType;
    private final TypeOfPositioning positionType;

    /**
     * @author Luca Lamperti
     * the constructor of the GoalCard class
     * @param type is the type of the GoalCard(from enum TypeOfCard)
     * @param points are the points on the GoalCard
     * @param goalType is the requirement type of the GoalCard
     * @param positionType is the position requirement(it is "EMPTY" in case there is no position requirement)
     * @param objects is a Stack that contains the items needed to get points(this is the case of a typeOfGoalCard OBJECT or RESOURCE)
     */
    public GoalCard(TypeOfCard type, int points, TypeOfGoalCard goalType, TypeOfPositioning positionType, ArrayList<Item> objects){
        super(type);
        numOfPoints = points;
        this.goalType = goalType;
        this.positionType = positionType;
        listOfObjects = new ArrayList<>(objects);
    }

    /**
     * @author Luca Lamperti
     * getter for the attribute NumOfPoints
     * @return an int as the points
     */
    public int getNumOfPoints(){
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
