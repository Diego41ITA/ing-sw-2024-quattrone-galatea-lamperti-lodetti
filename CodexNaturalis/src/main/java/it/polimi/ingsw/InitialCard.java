package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class InitialCard extends Card{
    private HashMap<Angle, TypeOfAngles> frontAngles = new HashMap<Angle, TypeOfAngles>();
    private HashMap<Angle, TypeOfAngles> backAngles = new HashMap<Angle, TypeOfAngles>();

    private ArrayList<TypeOfAngles> resourcesBack = new ArrayList<TypeOfAngles>();

    //Inserire nello stack angles gli angoli in questo ordine DOWNRIGHT, DOWNLEFT, HIGHRIGHT, HIGHLEFT
    public InitialCard(TypeOfCard type, Stack<String> frontAngles, Stack<String> backAngles, Stack<String> back){
        isFront = true;
        this.type = type;
        this.frontAngles.put(Angle.HIGHLEFT, TypeOfAngles.assignVALUE(frontAngles.pop()));
        this.frontAngles.put(Angle.HIGHRIGHT, TypeOfAngles.assignVALUE(frontAngles.pop()));
        this.frontAngles.put(Angle.DOWNLEFT, TypeOfAngles.assignVALUE(frontAngles.pop()));
        this.frontAngles.put(Angle.DOWNRIGHT, TypeOfAngles.assignVALUE(frontAngles.pop()));
        this.backAngles.put(Angle.HIGHLEFT, TypeOfAngles.assignVALUE(frontAngles.pop()));
        this.backAngles.put(Angle.HIGHRIGHT, TypeOfAngles.assignVALUE(frontAngles.pop()));
        this.backAngles.put(Angle.DOWNLEFT, TypeOfAngles.assignVALUE(frontAngles.pop()));
        this.backAngles.put(Angle.DOWNRIGHT, TypeOfAngles.assignVALUE(frontAngles.pop()));
        while(!back.empty()){
            resourcesBack.add(TypeOfAngles.assignVALUE(back.pop()));
        }
    }

    public ArrayList<TypeOfAngles> getResourcesBack(){
        return resourcesBack;
    }

    public HashMap<Angle, TypeOfAngles> getAngles(){
        if(isFront) {
            return frontAngles;
        }else{
            return backAngles;
        }
    }
}