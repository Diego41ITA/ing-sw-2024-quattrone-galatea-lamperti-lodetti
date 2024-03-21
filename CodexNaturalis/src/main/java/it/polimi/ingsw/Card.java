package it.polimi.ingsw;

public class Card {
    protected Boolean isFront;
    protected TypeOfCard type;

    public void flip(){
        isFront = !isFront;
    }
}
