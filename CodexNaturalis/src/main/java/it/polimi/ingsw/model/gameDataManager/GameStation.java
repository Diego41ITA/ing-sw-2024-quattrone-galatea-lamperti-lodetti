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
    public List<Point> getFreeCords(){ return new ArrayList<>(this.freeCords); }

    public void setFreeCords(ArrayList<Point> freeCords) {
        this.freeCords = new ArrayList<>(freeCords);
    }

    public void placeCard(PlayableCard card, Point cord){
            playedCards.put(cord, card);
            freeCords.remove(cord);
            updateFreeCoords(cord);
    }

    /**
     * updates the coordinates where its possible to play a new card
     * @param cord is the coordinate of the card played right before calling this method
     */
    public void updateFreeCoords(Point cord) {
        Point[] offsets = { new Point(-1, -1), new Point(-1, 1), new Point(1, -1), new Point(1, 1) };
        Angle[] angles = { Angle.DOWNLEFT, Angle.HIGHLEFT, Angle.DOWNRIGHT, Angle.HIGHRIGHT };
        List<Point> forbiddenCoords = new ArrayList<>();

        for (int i = 0; i < offsets.length; i++) {
            Point neighbor = new Point(cord.x + offsets[i].x, cord.y + offsets[i].y);
            if (!forbiddenCoords.contains(neighbor)) {
                if (!playedCards.containsKey(neighbor)) {
                    if (!playedCards.get(cord).checkIfEmpty(angles[i])) {
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



    /**Calculatefinalpoint() ritorna il punteggio ottenuto attraverso la carta obiettivo: 
    è composta da 11 rami if che controlla il tipo della carta obiettivo
    in ogni ramo c'è il controllo dell'obiettivo: 
    per esempio per le posizioni possiamo usare 3 cicli for per vedere se abbiamo il posizionamento 
    corretto analizzando colore e posizionamento, 
    per le risorse possiamo analizzare il numero delle risolrse attraverso il verifyResourceNeeded()/verifyObjectneeded()
    */
    public calculateFinalPoint(){

    }

    /**
     * @author Lodetti Alessandro
     * Checks if there are enough resources to place the Gold Card
     * @param goldCard it is the card the client wants to play
     * @return true if  it is ok to place the card, false otherwise
     */
    public boolean verifyResourcesNeeded(GoldCard goldCard){
        Map<Item, Integer> resources = calculateAvailableResources();

        for(Item i: goldCard.getNeededResources()){
            if(!resources.containsKey(i))
                return false;
            else if(resources.get(i) == 0)
                return false;
            else
                resources.put(i, resources.get(i) - 1);
        }
        return true;
    }

    /**
    stessa cosa di verifyResourceNedded solo per le carte goal.
    (ci deve essere un controllo per identificare il tipo della carta obiettivo)
    */
    public verifyObjectNeeded(){

    }
    /**un metodo che a seconda del tipo di carta Gold
    calcola il punteggio che ci da la carta Gold appena posizionamento.
    Per esempio è composta da degli if per capire che tipo di Carta Gold è e poi
    in ciascuno di quei if facciamo una analisi in base al tipo di carta Gold.
    Per esempio nel tipo Angle andiamo a contare il numero di angoli occupati
    e moltipichiamo pe il numeor di numOfPoint.*/
    public CalculateGoldPoint(){

    }

    /**
     * this method helps to calculate the available resource displayed on table
     * @return a map with items and their cardinality.
     */
    private Map<Item, Integer> calculateAvailableResources(){
        Map<Item, Integer> resources = new HashMap<>();

        for(PlayableCard c: this.playedCards.values())
        {
            for(Item i: c.getFreeItem()){
                if(resources.containsKey(i)){
                    resources.put(i, resources.get(i) + 1);
                }
                else
                    resources.put(i, 1);
            }

            //now it adds the permanent resources
            if(!c.isFront())
            {
                for(Item i: c.getAListOfBackResource()){
                    if(resources.containsKey(i))
                        resources.put(i, resources.get(i) + 1);
                    else
                        resources.put(i, 1);
                }
            }
        }

        return resources;
    }
}