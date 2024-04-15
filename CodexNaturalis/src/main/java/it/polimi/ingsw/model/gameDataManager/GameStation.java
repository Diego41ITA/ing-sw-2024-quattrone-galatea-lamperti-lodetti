package it.polimi.ingsw.model.gameDataManager;
import it.polimi.ingsw.model.card.*;
import java.util.*;
import java.awt.Point;
import it.polimi.ingsw.model.exceptions.illegalOperationException;

/**
 * this class represents the Station where the player places his cards for make point
 */
public class GameStation {
    /**
     * It's a map that associates each card that has been played with its coordinates.
     * The coordinates refer to the center of the card
     */
    private final Map<Point, PlayableCard> playedCards;
    /**
     * It's a list of all the available coordinates where a new card can be played
     */
    private ArrayList<Point> freeCords;

    /**
     * it's a constructor that initialize the gameStation object. It places the initial card and updates the new freeCords
     *
     * @param card is the initial card chosen by the player
     */
    public GameStation(InitialCard card) {
        playedCards = new HashMap<>();
        playedCards.put(new Point(0, 0), card);
        freeCords = new ArrayList<>();
        freeCords.add(new Point(-1, 1));
        freeCords.add(new Point(1, 1));
        freeCords.add(new Point(-1, -1));
        freeCords.add(new Point(1, -1));
    }

    public GameStation(GameStation gs) {
        this.playedCards = new HashMap<>();
        setPlayedCards(gs.getPlayedCards());
        this.freeCords = new ArrayList<>(gs.getFreeCords());
    }

    /**
     * useful to know which cards have been played.
     *
     * @return a copy of the disposition of the card
     */
    public Map<Point, PlayableCard> getPlayedCards() {
        return new HashMap<>(this.playedCards);
    }

    /**
     * this method is useful to set the playedCards by making sure that nobody has access to the private attribute
     *
     * @param map is a map of played card to copy.
     */
    public void setPlayedCards(Map<Point, PlayableCard> map) {
        for (Point p : map.keySet()) {
            this.playedCards.put(new Point(p), map.get(p)); //get(p) is already a copy of the reference
        }
    }

    /**
     * returns the free coordinates
     *
     * @return returns a copy of the available coordinates.
     */
    public List<Point> getFreeCords() {
        return new ArrayList<>(this.freeCords);
    }

    public void setFreeCords(ArrayList<Point> freeCords) {
        this.freeCords = new ArrayList<>(freeCords);
    }

    /**
     * is called from the player when he wants to play a card
     *
     * @param card is the card that the player wants to play
     * @param cord is the cord in which the player wants to play the card
     */
    public void placeCard(PlayableCard card, Point cord) {
        playedCards.put(cord, card);
        freeCords.remove(cord);
        updateFreeCoords(cord);
    }

    /**
     * updates the coordinates where its possible to play a new card
     *
     * @param cord is the coordinate of the card played right before calling this method
     */
    public void updateFreeCoords(Point cord) {
        Point[] offsets = {new Point(-1, -1), new Point(-1, 1), new Point(1, -1), new Point(1, 1)};
        Angle[] angles = {Angle.DOWNLEFT, Angle.HIGHLEFT, Angle.DOWNRIGHT, Angle.HIGHRIGHT};
        List<Point> forbiddenCoords = new ArrayList<>();

        for (int i = 0; i < offsets.length; i++) {
            Point neighbor = new Point(cord.x + offsets[i].x, cord.y + offsets[i].y);
            if (!forbiddenCoords.contains(neighbor)) {
                if (!playedCards.containsKey(neighbor)) {
                    if (!playedCards.get(cord).checkIfHidden(angles[i])) {
                        freeCords.add(neighbor);
                    } else {
                        forbiddenCoords.add(neighbor);
                    }
                } else {
                    playedCards.get(neighbor).hideAngle(angles[(i + 2) % 4]);
                }
            }
        }
    }


    /**
     * @param goldCard it is the card the client wants to play
     * @return true if  it is ok to place the card, false otherwise
     * @author Lodetti Alessandro, Lorenzo Galatea
     * Checks if there are enough resources to place the Gold Card
     */
    public boolean verifyResourcesNeeded(GoldCard goldCard) {
        Map<Item, Integer> resources = calculateAvailableResources();
        Map<Item, Integer> neededResources = goldCard.getNeededResources();
        for (Map.Entry<Item, Integer> entry : neededResources.entrySet()) {
            Item item = entry.getKey();
            int requiredAmount = entry.getValue();
            if (!resources.containsKey(item))
                return false;
            int availableAmount = resources.get(item);

            if (availableAmount < requiredAmount)
                return false;

        }
        return true;
    }


    /**
     * this method helps to calculate the available resource displayed on table
     *
     * @return a map with items and their cardinality.
     */
    private Map<Item, Integer> calculateAvailableResources() {
        Map<Item, Integer> resources = new HashMap<>();

        for (PlayableCard c : this.playedCards.values()) {
            for (Item i : c.getFreeItem()) {
                if (resources.containsKey(i)) {
                    resources.put(i, resources.get(i) + 1);
                } else
                    resources.put(i, 1);
            }

            //now it adds the permanent resources
            if (!c.isFront()) {
                for (Item i : c.getAListOfBackResource()) {
                    if (resources.containsKey(i))
                        resources.put(i, resources.get(i) + 1);
                    else
                        resources.put(i, 1);
                }
            }
        }

        return resources;
    }


    /**@author Lorenzo Galatea
     * checks if a corner of a card is covered by another card
     * @param card: card which corner must be checked
     * @param angle: angle of a Card which must be checked
     * @return true: if the angle is covered, false if the angle is not covered
     * @author Lorenzo Galatea
     */
    public boolean isAngleCovered(PlayableCard card, Angle angle) {
        Point cardPosition = null;
        for (Map.Entry<Point, PlayableCard> entry : playedCards.entrySet()) {
            if (entry.getValue().equals(card)) {
                cardPosition = entry.getKey();
                break;
            }
        }
        int x = cardPosition.x;
        int y = cardPosition.y;
        switch (angle) {
            case HIGHLEFT:
                x--;
                y++;
                break;
            case HIGHRIGHT:
                x++;
                y++;
                break;
            case DOWNLEFT:
                x--;
                y--;
                break;
            case DOWNRIGHT:
                x++;
                y--;
                break;
        }
        Point adjacentPosition = new Point(x, y);
        return playedCards.containsKey(adjacentPosition);
    }


    /**
     * this method works with this precondition: the card passed as parameter should be already placed, otherwise it
     * returns a wrong value.
     * Anyway, this method provides a way to get how many points should be added to the player that placed the card
     * @author Lodetti Alessandro
     * @param c the card that gives points.
     * @return the number of points that the placement of the card gave.
     */
    public int calculateGoldPoints(GoldCard c){
        int points;
        switch (c.getGoldType()){
            case GoldType.CLASSIC -> points = c.getNumberOfPoints();
            case GoldType.ANGLE -> points = c.getNumberOfPoints() * cornerCovered(c);
            case GoldType.ITEM -> points = c.getNumberOfPoints() * itemPresent(c.getBox());
            default -> points = 0;
        }
        return points;
    }

    /**
     * it determines how many angle a card covers.
     * @author Lodetti Alessandro
     * @param card the card that you want analyze
     * @return how many angle are covered by the card passed as parameter
     */
    private int cornerCovered(PlayableCard card){
        Point point = findCardPosition(card);
        int coveredAngles = 0;
        Point[] adjacentCard = {new Point((int) point.getX()+1, (int) point.getY()+1),
                new Point((int) point.getX()+1, (int) point.getY()-1),
                new Point((int) point.getX()-1, (int) point.getY()+1),
                new Point((int) point.getX()-1, (int) point.getY()-1)};
        for(Point p: adjacentCard){
            if(playedCards.containsKey(p))
                coveredAngles++;
        }
        return coveredAngles;
    }

    /**
     * this method is useful to know how many specified items are present
     * @author Lodetti Alessandro
     * @param item it's the item of which the caller wants to now the cardinality
     * @return the cardinality of the specified item.
     */
    private int itemPresent(Item item){
        int num = 0;
        for(Point p: playedCards.keySet()){
            List<Item> l = playedCards.get(p).getFreeItem();
            for(Item i: l){
                if(i == item)
                    num++;
            }
        }
        return num;
    }


    /**
     * it defines a fast way to determine which point correspond to a card
     * @author Lodetti Alessandro
     * @param card the user wants to know the position of this card
     * @return a Point object which corresponds to the location of the card
     */
    private Point findCardPosition(PlayableCard card){
        for(Point p: playedCards.keySet()){
            if(playedCards.get(p).equals(card))
                return new Point(p);
        }
    }
}