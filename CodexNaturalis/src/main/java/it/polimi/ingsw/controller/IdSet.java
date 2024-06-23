package it.polimi.ingsw.controller;

import java.util.*;

public class IdSet {
    private Set<Integer> set;
    public IdSet(List<ControllerOfGame> list){
        fillUp();
        removeFromSet(convertToInt(list));
    }

    /**
     * this method covert a list of game controllers in the list of the game id
     * @param list it's a list of game controllers
     * @return returns a list of integer lik "Game1" -> 1 ecc...
     */
    private List<Integer> convertToInt(List<ControllerOfGame> list){
        return list.stream().map(g -> Integer.parseInt(g.getGameId().substring(4)))
                .toList();
    }

    /**
     * allows to get a random element.
     * @return returns an int contained in the set
     */
    public synchronized int getRandomId(){
        Iterator<Integer> setIterator = set.iterator();
        int randomElement = -1;
        if(setIterator.hasNext()) {
            randomElement = setIterator.next();
            setIterator.remove();
        }
        return randomElement;
    }

    /**
     * fills the set with 100 integers.
     */
    private void fillUp(){
        this.set = new HashSet<>();
        for(int i = 0; i<100; i++)
            this.set.add(i);
    }

    /**
     * removes the already used id
     * @param list it's a list of the already used id.
     */
    private void removeFromSet(List<Integer> list){
        for(Integer id: list){
            this.set.remove(id);
        }
    }
}
