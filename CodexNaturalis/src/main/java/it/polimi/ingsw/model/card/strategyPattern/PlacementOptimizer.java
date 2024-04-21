package it.polimi.ingsw.model.card.strategyPattern;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;
/**
 * @author Luca Lamperti
 *
 * Class used to get the maximum number of valid placements without common cards
 * This class provides a method to optimize the placement of cards that maximize the number of valid placements
 * checking that no common cards are in any placement
 */
public class PlacementOptimizer {
    /**
     * Optimizes the placement of cards maximizing the number of valid placements without common cards
     *
     * @param validPlacements A set of sets of points representing valid placements
     * @return The maximum number of valid placements without common cards
     */
    public static int optimize(Set<Set<Point>> validPlacements) {
        Set<Set<Point>> AlreadyViewedCoordGroups = new HashSet<>();
        ValidPlacementsIterator iterator = new ValidPlacementsIterator(validPlacements);
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
                ValidPlacementsIterator iteratorFilter = new ValidPlacementsIterator(validPlacements);
                validPlacements = new HashSet<>(iteratorFilter.filter(coordGroup));
                iterator = new ValidPlacementsIterator(validPlacements);
            }
        }
        return validPlacements.size();
    }
}
