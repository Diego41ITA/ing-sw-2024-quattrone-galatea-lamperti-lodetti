package it.polimi.ingsw.CARD;

import java.util.ArrayList;
import java.util.Stack;

public class GoalCard extends Card {
    private final int numOfPoints;
    private final ArrayList<Item> listOfObjects = new ArrayList<>();
    private final TypeOfGoalCard goalType;
    private final TypeOfPositioning positionType;

    public GoalCard(TypeOfCard type, int points, TypeOfGoalCard goalType, TypeOfPositioning positionType, Stack<Item> objects){
        super(type);
        numOfPoints = points;
        this.goalType = goalType;
        this.positionType = positionType;
        while (!objects.empty()){
            listOfObjects.add(objects.pop());
        }
    }

    public int getNumOfPoints(){
        return numOfPoints;
    }

    public TypeOfGoalCard getGoalType() {
        return goalType;
    }

    public TypeOfPositioning getPositionType() {
        return positionType;
    }

    public ArrayList<Item> getListOfObjects() {
        return new ArrayList<>(listOfObjects);
    }
}
