package it.polimi.ingsw;

public enum TypeOfAngles {
    VEGETABLE, ANIMAL, INSECT, MUSHROOM, RESOURCE, HIDDEN, EMPTY, POTION, FEATHER, PARCHMENT;

    public static TypeOfAngles assignVALUE(String value){
        return TypeOfAngles.valueOf(value);
    }
}
