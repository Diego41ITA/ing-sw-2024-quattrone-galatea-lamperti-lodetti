package it.polimi.ingsw.GameView;
import it.polimi.ingsw.model.gameDataManager.*;
import javafx.scene.layout.BorderImage;

import java.io.Closeable;
import java.util.*;
import java.io.Serializable;
import java.util.stream.Collectors;

/**
 * this class make an immutable copy of a Game Object, and exposes only getter methods.
 * @author Lodetti Alessandro
 */
public class GameView implements Serializable {
    private final List<Player> players;
    private final Map<String, Boolean> activity;
    private final TableOfDecks tableOfDecks;
    private final PointTable points;
    private final int maxNumOfPlayer;
    private final Status status;
    private final Turn turn;
    private final String id;


    public GameView(){
        this.players = new ArrayList<>();
        this.activity = new HashMap<>();
        this.tableOfDecks = new TableOfDecks();
        this.points = new PointTable();
        this.maxNumOfPlayer = 4;
        this.status = Status.WAITING;
        this.turn = null;
        this.id = "Default-Game";
    }
    public GameView(Game game){
        this.players = new ArrayList<>(game.getPlayers());
        this.activity = new HashMap<>(game.getActivity());
        this.tableOfDecks = new TableOfDecks(game.getTableOfDecks());
        this.points = new PointTable(game.getPointTable());
        this.maxNumOfPlayer = game.getMaxNumberPlayer();
        this.status = game.getStatus();
        this.turn = new Turn(game.getTurn());
        this.id = game.getId();
    }

    public Map<String, Boolean> getActivity(){
        return this.activity;
    }
    public Status getStatus(){
        return this.status;
    }

    public Player getCurrentPlayer(){
        return getPlayerByNick(this.turn.getCurrentPlayerNick());
    }

    public boolean isMyTurn(String nick){
        if(this.getCurrentPlayer().getNick().equals(nick))
            return true;
        return false;
    }

    public PointTable getPoints(){
        return new PointTable(this.points);
    }

    public TableOfDecks getTableOfDecks(){
        return new TableOfDecks(this.tableOfDecks);
    }

    public List<Player> getPlayers(){
        return this.players;
    }

    public int getMaxNumOfPlayer(){
        return this.maxNumOfPlayer;
    }

    public List<GameStation> getAllGameStation(){
        return this.players.stream()
                .map(Player::getGameStation)
                .collect(Collectors.toList());
    }

    public GameStation getMyGameStation(String nick){
        for(Player p: players){
            if(p.getNick().equals(nick)){
                return p.getGameStation();
            }
        }
        return null;
    }

    public String getId() {
        return id;
    }

    /**
     * This method returns an object player by passing his nickname
     * @param nick is the nickname of the player that you want
     * @return a player object.
     */
    public Player getPlayer(String nick){
        Player player = null;
        for(Player p: players){
            if(p.getNick().equals(nick))
                player = p;
        }
        return player;
    }

    public Player getPlayerByNick(String name) {
        for (Player p : players) {
            if (p.getNick().equals(name))
                return p;
        }
        System.err.println("No player with nick:" + name + "founded");
        return null;
    }

    public Turn getTurn() {
        return turn;
    }
}
