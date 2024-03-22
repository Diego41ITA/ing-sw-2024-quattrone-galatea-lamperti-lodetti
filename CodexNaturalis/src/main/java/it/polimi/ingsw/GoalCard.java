package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.Stack;

public class GoalCard {
    private int numOfPoints;
    private ArrayList<TypeOfAngles> listOfObjects;
    private TypeOfGoalCard goalType;
    private TypeOfPositioning positionType;

    public GoalCard(int points, String goalType, String positionType, Stack<String> objects){
        numOfPoints = points;
        this.goalType = TypeOfGoalCard.assignVALUE(goalType);
        this.positionType = TypeOfPositioning.assignVALUE(positionType);
        while (!objects.empty()){
            listOfObjects.add(TypeOfAngles.assignVALUE(objects.pop()));
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

    public ArrayList<TypeOfAngles> getListOfObjects() {
        return listOfObjects;
    }
}
