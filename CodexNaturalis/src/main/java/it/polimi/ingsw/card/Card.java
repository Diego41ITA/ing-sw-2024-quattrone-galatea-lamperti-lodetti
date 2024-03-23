package it.polimi.ingsw.card;

public class Card {
    //prova per Git
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
