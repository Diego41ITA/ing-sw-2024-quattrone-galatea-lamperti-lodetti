package it.polimi.ingsw.model.card.strategyPattern;

import java.awt.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
/**
 * @author Luca Lamperti
 * Implementation of a personalized iterator for validPlacements
 * This iterator is designed to iterate over a set of sets of points
 */
public class ValidPlacementsIterator implements Iterator<Set<Point>> {
    private Iterator<Set<Point>> iterator;

    public ValidPlacementsIterator(Set<Set<Point>> validPlacements) {
        this.iterator = validPlacements.iterator();
    }
    /**
     * to check if the iteration has more elements.
     *
     * @return true if the iteration has more elements, false otherwise
     */
    public boolean hasNext() {
        return iterator.hasNext();
    }

    /**
     * To get the next element in the iteration
     *
     * @return the next element in the iteration
     */
    public Set<Point> next() {
        return iterator.next();
    }
    /**
     * Removes the last element returned by the iterator
     */
    public void remove() {
        iterator.remove();
    }
    /**
     * Remove sets of points from the iterator that contains at least one of the points contained in coordGroup
     *
     * @param coordGroup A set of points representing the coordinate used to filter the iterator
     * @return A filtered version of valid placements
     */
    public Set<Set<Point>>  filter(Set<Point> coordGroup) {
        Set<Set<Point>> validPlacementsFiltered = new HashSet<>();
        validPlacementsFiltered.add(coordGroup);
        while (iterator.hasNext()) {
            Set<Point> next = iterator.next();
            boolean keep = true;
            for (Point p : coordGroup){
                if(next.contains(p)){
                    keep = false;
                    break;
                }
            }
            if(keep) validPlacementsFiltered.add(next);
        }
        return validPlacementsFiltered;
    }
}
