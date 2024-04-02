package it.polimi.ingsw.model.gameDataManager;
import java.util.HashMap;
import java.util.Map;
/** @author Lorenzo Galatea
*it is the class that represents the score table associated with each game
points represents a map where each marker has a score associated with it
*/

public class PointTable {
    private final Map<Color, Integer> points;       //maybe Map<Player, int> points;
    /** @author Lorenzo Galatea
    *is the constructor of the PointTable class
        */
    public PointTable() {
        points = new HashMap<>();
    }
    /** @author Lorenzo Galatea
    * is the marker setter
    *@param color: maker of the player
    */
    public void setColorPoints(Color color) {
        points.put(color, 0);
    }
    /** @author Lorenzo Galatea
    *is the getter of the score associated with the Player/Maker
    *@return int which is the point associated with the Player
    *@param player: the player whose score you want to know
    */
    public int getPoint(Player player) {
        Color playerColor = player.getColor();
        Integer score = points.get(playerColor);
        return (score != null) ? score.intValue() : 0;
    }
    /**@author Lorenzo Galatea 
    *updates the score value
    @param player: player whose score you want to change
    @param score: int which represents the updated score
    */
    public void updatePoint(Player player, int score) {
        Color playerColor = player.getColor();
        points.put(playerColor, score);
    }
    /**@author Lorenzo Galatea
    *notifies if any player has reached 20 points
    *@return true if there is a player with more of 20 points 
    */
    public boolean notify20PointReached() {
        for (int score : points.values()) {
            if (score >= 20) {
                return true;
            }
        }
        return false;
    }
    /**@author Lorenzo Galatea
    * clear the Map points
    */
    public void resetPoints() {
        points.clear();
    }

}
