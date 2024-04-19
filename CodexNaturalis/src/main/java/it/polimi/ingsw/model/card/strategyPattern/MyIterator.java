package it.polimi.ingsw.model.card.strategyPattern;

import java.awt.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MyIterator implements Iterator<Set<Point>> {
    private Iterator<Set<Point>> iterator;

    public MyIterator(Set<Set<Point>> validPlacements) {
        this.iterator = validPlacements.iterator();
    }

    public boolean hasNext() {
        return iterator.hasNext();
    }

    public Set<Point> next() {
        return iterator.next();
    }

    public void remove() {
        iterator.remove();
    }

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
