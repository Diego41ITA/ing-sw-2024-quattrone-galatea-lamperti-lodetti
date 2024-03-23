package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class InitialCard extends Card{
    private final HashMap<Angle, Item> frontAngles = new HashMap<>();
    private final HashMap<Angle, Item> backAngles = new HashMap<>();

    private final ArrayList<Item> resourcesBack = new ArrayList<>();

    //Inserire nello stack angles gli angoli in questo ordine DOWNRIGHT, DOWNLEFT, HIGHRIGHT, HIGHLEFT
    public InitialCard(TypeOfCard type, Stack<Item> frontAngles, Stack<Item> backAngles, Stack<Item> back){
        isFront = true;
        this.type = type;
        this.frontAngles.put(Angle.HIGHLEFT, frontAngles.pop());
        this.frontAngles.put(Angle.HIGHRIGHT, frontAngles.pop());
        this.frontAngles.put(Angle.DOWNLEFT, frontAngles.pop());
        this.frontAngles.put(Angle.DOWNRIGHT, frontAngles.pop());
        this.backAngles.put(Angle.HIGHLEFT, backAngles.pop());
        this.backAngles.put(Angle.HIGHRIGHT, backAngles.pop());
        this.backAngles.put(Angle.DOWNLEFT, backAngles.pop());
        this.backAngles.put(Angle.DOWNRIGHT, backAngles.pop());
        while(!back.empty()){
            resourcesBack.add(back.pop());
        }
    }

    public ArrayList<Item> getResourcesBack(){
        return new ArrayList<>(resourcesBack);
    }

    public HashMap<Angle, Item> getAngles(){
        if(isFront) {
            return new HashMap<>(frontAngles);
        }else{
            return new HashMap<>(backAngles);
        }
    }
}