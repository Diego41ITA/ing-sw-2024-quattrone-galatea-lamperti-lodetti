package it.polimi.ingsw.model;
import it.polimi.ingsw.model.gameDataManager.*;

import java.io.Closeable;
import java.util.*;
import java.io.Serializable;
import java.util.stream.Collectors;

/**
 * this class make an immutable copy of a Game Object, and exposes only getter methods.
 * @author Lodetti Alessandro
 */
public class GameView implements Serializable {
    private final Map<Player, Boolean> players;
    private final TableOfDecks tableOfDecks;
    private final PointTable points;
    private final int maxNumOfPlayer;
    private final Status status;
    private final Turn turn;
    private final String id;


    public GameView(){
        this.players = new HashMap<>();
        this.tableOfDecks = new TableOfDecks();
        this.points = new PointTable();
        this.maxNumOfPlayer = 4;
        this.status = Status.WAITING;
        this.turn = null;
        this.id = "Default-Game";
    }
    public GameView(Game game){
        this.players = new HashMap<>(game.getPlayers());
        this.tableOfDecks = new TableOfDecks(game.getTableOfDecks());
        this.points = new PointTable(game.getPointTable());
        this.maxNumOfPlayer = game.getMaxNumberPlayer();
        this.status = game.getStatus();
        this.turn = new Turn(game.getTurn());
        this.id = game.getId();
    }

    public Status getStatus(){
        return this.status;
    }
    public Player getCurrentPlayer(){
        return this.turn.getCurrentPlayer();
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

    public Map<Player, Boolean> getPlayers(){
        return this.players;
    }

    public List<GameStation> getAllGameStation(){
        return this.players.keySet().stream()
                .map(Player::getGameStation)
                .collect(Collectors.toList());
    }

    public GameStation getMyGameStation(String nick){
        for(Player p: players.keySet()){
            if(p.getNick().equals(nick)){
                return p.getGameStation();
            }
        }
        return null;
    }

    public String getId() {
        return id;
    }


}
