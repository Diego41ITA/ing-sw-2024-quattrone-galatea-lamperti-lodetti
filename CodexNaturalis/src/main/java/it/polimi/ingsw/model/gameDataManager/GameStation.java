package it.polimi.ingsw.model.gameDataManager;
import it.polimi.ingsw.model.card.*;
import java.util.*;
import java.awt.Point;

/** @author 
 * this class represents the Station where the player places his cards for make point
 */
class GameStation {
    private final Map<Card, Point> playedCards;
    private ArrayList<Point> FreeCords;

    public GameStation(InitialCard card) {
        playedCards = new HashMap<>();
        playedCards.put(card, new Point(0, 0));
        FreeCords = new ArrayList<>();
        FreeCords.add(new Point(-1,1));
        FreeCords.add(new Point(1,1));
        FreeCords.add(new Point(-1,-1));
        FreeCords.add(new Point(1,-1));
    }

    public void placeCard(Card card, Point coord){
            playedCards.put(card, coord);
            FreeCords.remove(coord);
            updateFreeCoords(coord);
    }

    //da sistemare
    public void updateFreeCoords(Point coord){
        Point a = new Point(coord.x - 1, coord.y - 1);
        Point b = new Point(coord.x - 1, coord.y - 1);
        Point c = new Point(coord.x - 1, coord.y - 1);
        Point d = new Point(coord.x - 1, coord.y - 1);

        if(!playedCards.containsValue(a)) FreeCords.add(a);
        if(!playedCards.containsValue(b)) FreeCords.add(b);
        if(!playedCards.containsValue(c)) FreeCords.add(c);
        if(!playedCards.containsValue(d)) FreeCords.add(d);
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
