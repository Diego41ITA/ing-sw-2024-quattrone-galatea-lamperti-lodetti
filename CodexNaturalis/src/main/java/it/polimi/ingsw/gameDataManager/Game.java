package it.polimi.ingsw.gameDataManager;

import java.util.Collection;
import java.util.Map;
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
        this.players = new Map<Player, Boolean>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean containsKey(Object key) {
                return false;
            }

            @Override
            public boolean containsValue(Object value) {
                return false;
            }

            @Override
            public Boolean get(Object key) {
                return null;
            }

            @Override
            public Boolean put(Player key, Boolean value) {
                return null;
            }

            @Override
            public Boolean remove(Object key) {
                return null;
            }

            @Override
            public void putAll(Map<? extends Player, ? extends Boolean> m) {

            }

            @Override
            public void clear() {

            }

            @Override
            public Set<Player> keySet() {
                return null;
            }

            @Override
            public Collection<Boolean> values() {
                return null;
            }

            @Override
            public Set<Entry<Player, Boolean>> entrySet() {
                return null;
            }
        };
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
