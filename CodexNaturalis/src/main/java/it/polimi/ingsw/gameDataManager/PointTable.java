package it.polimi.ingsw.gameDataManager;
import java.util.HashMap;
import java.util.Map;

public class PointTable {
    private Map<Color, Integer> points;
    public PointTable() {
        points = new HashMap<>();
    }
    public void setColorPoints(Color color) {
        points.put(color, 0);
    }
    public int getPoint(Player player) {
        Color playerColor = player.getColor();
        Integer score = points.get(playerColor);
        return (score != null) ? score.intValue() : 0;
    }
    public void updatePoint(Player player, int score) {
        Color playerColor = player.getColor();
        points.put(playerColor, score);
    }

    public boolean notify20PointReached() {
        for (int score : points.values()) {
            if (score >= 20) {
                return true;
            }
        }
        return false;
    }
    public void resetPoints() {
        points.clear();
    }

}
