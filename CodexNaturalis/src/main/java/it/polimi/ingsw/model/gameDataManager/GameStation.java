package it.polimi.ingsw.model.gameDataManager;
import it.polimi.ingsw.model.card.*;
import java.util.*;
import java.awt.Point;

/** @author 
 * this class represents the Station where the player places his cards for make point
 */
public class GameStation {
    private final Map<Point, PlayableCard> playedCards;
    private ArrayList<Point> freeCords;

    public GameStation(InitialCard card) {
        playedCards = new HashMap<>();
        playedCards.put(new Point(0, 0), card);
        freeCords = new ArrayList<>();
        freeCords.add(new Point(-1,1));
        freeCords.add(new Point(1,1));
        freeCords.add(new Point(-1,-1));
        freeCords.add(new Point(1,-1));
    }

    public GameStation(GameStation gs){
        this.playedCards = new HashMap<>();
        setPlayedCards(gs.getPlayedCards());
        this.freeCords = new ArrayList<>(gs.getFreeCords());
    }

    /**
     * useful to know which cards have been played.
     * @return a copy of the disposition of the card
     */
    public Map<Point, PlayableCard> getPlayedCards(){
        return new HashMap<>(this.playedCards);
    }

    /**
     * this method is useful to set the playedCards by making sure that nobody has access to the private attribute
     * @param map is a map of played card to copy.
     */
    public void setPlayedCards(Map<Point, PlayableCard> map){
        for(Point p: map.keySet()){
            this.playedCards.put(new Point(p), map.get(p)); //get(p) is already a copy of the reference
        }
    }

    /**
     * returns the free coordinates
     * @return returns a copy of the available coordinates.
     */
    public List<Point> getFreeCords(){
        return new ArrayList<>(this.freeCords);
    }

    public void placeCard(PlayableCard card, Point cord){
            playedCards.put(cord, card);
            freeCords.remove(cord);
            updateFreeCoords(cord);
    }

    //da sistemare
    public void updateFreeCoords(Point cord){
        Point a = new Point(cord.x - 1, cord.y - 1);
        Point b = new Point(cord.x - 1, cord.y + 1);
        Point c = new Point(cord.x + 1, cord.y - 1);
        Point d = new Point(cord.x + 1, cord.y + 1);

        if(!playedCards.containsKey(a)){
            freeCords.add(a);
        }else {
            playedCards.get(a).hideAngle(Angle.HIGHRIGHT);
        }
        if(!playedCards.containsKey(b)) {
            freeCords.add(b);
        }else {
            playedCards.get(b).hideAngle(Angle.DOWNRIGHT);
        }
        if(!playedCards.containsKey(c)){
            freeCords.add(c);
        }else {
            playedCards.get(c).hideAngle(Angle.HIGHLEFT);
        }
        if(!playedCards.containsKey(d)){
            freeCords.add(d);
        }else {
            playedCards.get(d).hideAngle(Angle.DOWNLEFT);
        }
    }
    public calculateScore(){

    }
    public showFreeCorners(){

    }
    public calculateFinalPoint(){

    }
    public verifyResorcePoint(){

    }

    public countAngleOccupied(){

    }

    public verifyObjectNeeded(){

    }

    public ControllPositioning(){

    }
    public showCardDispositioning(){


    }

}
