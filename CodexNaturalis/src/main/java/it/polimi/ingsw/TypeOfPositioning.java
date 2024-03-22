package it.polimi.ingsw;

public enum TypeOfPositioning {
    POSITION_1, POSITION_2, POSITION_3, POSITION_4, POSITION_5, POSITION_6, POSITION_7, POSITION_8;

    public static TypeOfPositioning assignVALUE(String value){
        return TypeOfPositioning.valueOf(value);
    }
}
