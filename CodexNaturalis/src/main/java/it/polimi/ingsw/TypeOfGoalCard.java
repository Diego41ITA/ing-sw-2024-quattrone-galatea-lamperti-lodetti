package it.polimi.ingsw;

public enum TypeOfGoalCard {
    OBJECT, POSITION, RESOURCE;

    public static TypeOfGoalCard assignVALUE(String value){
        return TypeOfGoalCard.valueOf(value);
    }
}
