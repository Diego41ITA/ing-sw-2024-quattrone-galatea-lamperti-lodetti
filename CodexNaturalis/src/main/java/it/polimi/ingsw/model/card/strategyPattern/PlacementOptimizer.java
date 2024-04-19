package it.polimi.ingsw.model.card.strategyPattern;

import java.awt.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
/**@author Luca Lamperti
 *
 *
 */
public class PlacementOptimizer {
    public static int optimize(Set<Set<Point>> validPlacements) {
        MyIterator iterator = new MyIterator(validPlacements);
        while (iterator.hasNext()) {
            Set<Point> coordGroup = iterator.next();
            boolean isUnique = true;
            for (Point p : coordGroup) {
                isUnique = true;
                MyIterator checkIterator = new MyIterator(validPlacements);
                while (checkIterator.hasNext()){
                    Set<Point> coordGroupCheck = checkIterator.next();
                    if (!coordGroupCheck.equals(coordGroup) && coordGroupCheck.contains(p)) {
                        isUnique = false;
                        break;
                    }
                }
                if (isUnique) break;
            }
            if (!isUnique){
                iterator.remove();
            } else if (iterator.hasNext()){
                Set<Set<Point>> validPlacementsFiltered = iterator.filter(coordGroup);
                if(!validPlacementsFiltered.equals(validPlacements)){
                    iterator = new MyIterator(validPlacementsFiltered);
                }
            }
        }
        return validPlacements.size();
    }
}
