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
        Set<Set<Point>> AlreadyViewedCoordGroups = new HashSet<>();
        MyIterator iterator = new MyIterator(validPlacements);
        while (iterator.hasNext()) {
            Set<Point> coordGroup = iterator.next();
            boolean hasUniquePoint = true;
            for (Point p : coordGroup) {
                hasUniquePoint = true;
                for(Set<Point> coordGroupCheck : validPlacements){
                    if ((!coordGroupCheck.equals(coordGroup)) && coordGroupCheck.contains(p)) {
                        hasUniquePoint = false;
                        break;
                    }
                }
                if (hasUniquePoint) break;
            }
            if (hasUniquePoint && !AlreadyViewedCoordGroups.contains(coordGroup)){
                AlreadyViewedCoordGroups.add(coordGroup);
                MyIterator iteratorFilter = new MyIterator(validPlacements);
                validPlacements = new HashSet<>(iteratorFilter.filter(coordGroup));
                iterator = new MyIterator(validPlacements);
            }
        }
        return validPlacements.size();
    }
}
