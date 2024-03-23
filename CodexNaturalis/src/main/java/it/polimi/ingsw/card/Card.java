package it.polimi.ingsw.CARD;

public class Card {
    protected Boolean isFront;
    protected TypeOfCard type;

    public Card(TypeOfCard type){
        isFront = true;
        this.type = type;
    }

    public void flip(){
        isFront = !isFront;
    }

    public Boolean getIsFront(){ return isFront;}

    public TypeOfCard getType() {
        return type;
    }
}
