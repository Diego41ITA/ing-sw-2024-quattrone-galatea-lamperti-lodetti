package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.HashMap;

public class InitialCard extends Card{
    private HashMap<Angle, TypeOfAngles> frontAngles = new HashMap<Angle, TypeOfAngles>();
    private HashMap<Angle, TypeOfAngles> backAngles = new HashMap<Angle, TypeOfAngles>();

    private ArrayList<TypeOfAngles> resourcesBack = new ArrayList<TypeOfAngles>();

    public InitialCard(Boolean isFront, TypeOfCard type,
                       TypeOfAngles fhl, TypeOfAngles fhr, TypeOfAngles fdl, TypeOfAngles fdr,
                       TypeOfAngles bhl, TypeOfAngles bhr, TypeOfAngles bdl, TypeOfAngles bdr,
                       TypeOfAngles back1, TypeOfAngles back2, TypeOfAngles back3){
        this.isFront = isFront;
        this.type = type;
        frontAngles.put(Angle.HIGHLEFT, fhl);
        frontAngles.put(Angle.HIGHRIGHT, fhr);
        frontAngles.put(Angle.DOWNLEFT, fdl);
        frontAngles.put(Angle.DOWNRIGHT, fdr);
        backAngles.put(Angle.HIGHLEFT, bhl);
        backAngles.put(Angle.HIGHRIGHT, bhr);
        backAngles.put(Angle.DOWNLEFT, bdl);
        backAngles.put(Angle.DOWNRIGHT, bdr);
        if(!back1.equals(TypeOfAngles.HIDDEN)){
            resourcesBack.add(back1);
        }
        if(!back2.equals(TypeOfAngles.HIDDEN)){
            resourcesBack.add(back2);
        }
        if(!back3.equals(TypeOfAngles.HIDDEN)){
            resourcesBack.add(back3);
        }
    }

    public ArrayList<TypeOfAngles> getResourcesBack() {
        return resourcesBack;
    }

    public HashMap<Angle, TypeOfAngles> getAngles() {
        if(isFront) {
            return frontAngles;
        }else{
            return backAngles;
        }
    }
}