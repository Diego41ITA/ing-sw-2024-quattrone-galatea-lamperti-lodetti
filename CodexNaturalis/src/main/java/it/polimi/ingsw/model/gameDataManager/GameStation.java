package it.polimi.ingsw.model.gameDataManager;
import it.polimi.ingsw.model.card.*;
import java.util.*;
import java.awt.Point;

/** @author 
 * this class represents the Station where the player places his cards for make point
 */
class GameStation {
    private final Map<Point, PlayableCard> playedCards;
    private ArrayList<Point> FreeCords;

    public GameStation(InitialCard card) {
        playedCards = new HashMap<>();
        playedCards.put(new Point(0, 0), card);
        FreeCords = new ArrayList<>();
        FreeCords.add(new Point(-1,1));
        FreeCords.add(new Point(1,1));
        FreeCords.add(new Point(-1,-1));
        FreeCords.add(new Point(1,-1));
    }

    public void placeCard(PlayableCard card, Point coord){
            playedCards.put(coord, card);
            FreeCords.remove(coord);
            updateFreeCoords(coord);
    }

    //da sistemare
    public void updateFreeCoords(Point coord){
        Point a = new Point(coord.x - 1, coord.y - 1);
        Point b = new Point(coord.x - 1, coord.y + 1);
        Point c = new Point(coord.x + 1, coord.y - 1);
        Point d = new Point(coord.x + 1, coord.y + 1);

        if(!playedCards.containsValue(a)){
            FreeCords.add(a);
        }else {
            playedCards.get(a).hideAngle(Angle.HIGHRIGHT);
        }
        if(!playedCards.containsValue(b)) {
            FreeCords.add(b);
        }else {
            playedCards.get(b).hideAngle(Angle.DOWNRIGHT);
        }
        if(!playedCards.containsValue(c)){
            FreeCords.add(c);
        }else {
            playedCards.get(c).hideAngle(Angle.HIGHLEFT);
        }
        if(!playedCards.containsValue(d)){
            FreeCords.add(d);
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
