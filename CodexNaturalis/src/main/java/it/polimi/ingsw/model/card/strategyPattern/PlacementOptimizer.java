package it.polimi.ingsw.model.card.strategyPattern;

import java.awt.*;
import java.util.Set;
import java.util.stream.Collectors;
/**@author Luca Lamperti
 *
 *
 */
public class PlacementOptimizer {
    public static int optimize(Set<Set<Point>> validPlacements){
        for(Set<Point> CoordGroup : validPlacements) {
            boolean isContained;
            isContained = true;
            outsideLoop:
            for (Point p : CoordGroup) {
                for (Set<Point> coordGroupCheck : validPlacements) {
                    if (!coordGroupCheck.equals(CoordGroup) && !coordGroupCheck.contains(p)) {
                        isContained = false;
                        break outsideLoop;
                    }
                }
            }
            if(isContained){
                validPlacements.remove(CoordGroup);
            }else {
                Set<Set<Point>> validPlacementsFiltered;
                for (Point p : CoordGroup) {
                    validPlacementsFiltered = validPlacements.stream()
                            .filter(x -> x.equals(CoordGroup) || !x.contains(p))
                            .collect(Collectors.toSet());
                    validPlacements = validPlacementsFiltered;
                }
            }
        }
        return validPlacements.size();
    }
}
