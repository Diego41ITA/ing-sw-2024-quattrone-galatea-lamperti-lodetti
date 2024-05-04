package it.polimi.ingsw.model.gameDataManager;
import java.awt.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
/**
 * it is the class that represents the score table associated with each game
 * points represents a map where each marker has a score associated with it
 * @author Lorenzo Galatea
 */

public class PointTable implements Serializable {
    private final HashMap<Color, Integer> points;

    /**
     * is the constructor of the PointTable class
     * @author Lorenzo Galatea
     */
    public PointTable() {
        points = new HashMap<>();
    }

    /**
     * it's a simple copy-constructor
     * @param pt is the point table that the user wants to copy
     */
    public PointTable(PointTable pt){
        this.points = new HashMap<>(pt.getMap());
    }

    /**
     * this return a copy of the table with all the scores
     * @return an HashMap<Color, Integer>.
     */
    public Map<Color, Integer> getMap(){
        return new HashMap<>(points);
    }

    /**
     * is the marker setter
     * @author Lorenzo Galatea
     *@param color: maker of the player
     */
    public void setColorPoints(Color color) {
        points.put(color, 0);
    }

    /**
     * is the getter of the score associated with the Player/Maker
     * @author Lorenzo Galatea
     *@return int which is the point associated with the Player
     *@param player: the player whose score you want to know
    */
    public int getPoint(Player player) {
        Color playerColor = player.getColor();
        Integer score = points.get(playerColor);
        return (score != null) ? score.intValue() : 0;
    }
    /**
     * updates the score value
     * @author Lorenzo Galatea
     * @param player: player whose score you want to change
     * @param score: int which represents the updated score
    */
    public void updatePoint(Player player, int score) {
        Color playerColor = player.getColor();
        points.replace(playerColor, score);
    }
    /**
     * notifies if any player has reached 20 points
     * @author Lorenzo Galatea
     * @return true if there is a player with more of 20 points
     */
    public boolean notify20PointReached() {
        for (int score : points.values()) {
            if (score >= 20) {
                return true;
            }
        }
        return false;
    }
    /**
     * clear the Map points
     * @author Lorenzo Galatea
     */
    public void resetPoints() {
        points.clear();
    }

}
