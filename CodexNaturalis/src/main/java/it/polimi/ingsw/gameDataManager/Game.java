package it.polimi.ingsw.gameDataManager;

import java.util.Collection;
import java.util.*;
import java.util.Set;

public class Game {
    private Map<Player, Boolean> players;
    private TableOfDecks tableOfDecks;
    private PointTable pointTable;
    private Player winner;
    private boolean started;
    private String id;
    private Turn turn;

    public Boolean getStarted() {
        return this.started;
    }

    public Map<Player, Boolean> getPlayers() {
        return this.players;
    }

    public Player getWinner() {
        return this.winner;
    }

    public PointTable getPointTable() {
        return this.pointTable;
    }

    public String getId() {
        return this.id;
    }

    public TableOfDecks getTableOfDecks() {
        return this.tableOfDecks;
    }

    public Turn getTurn() {
        return this.turn;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPlayers(Map<Player, Boolean> players) {
        this.players = new HashMap<Player, Boolean>();
    }

    public void setPointTable(PointTable pointTable) {
        this.pointTable = new PointTable();
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public void setTableOfDecks(TableOfDecks tableOfDecks) {
        this.tableOfDecks = new TableOfDecks();
    }

    public void setTurn(Turn turn) {
        this.turn = new Turn();
    }
}
