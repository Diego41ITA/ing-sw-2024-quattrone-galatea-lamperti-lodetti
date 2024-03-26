package it.polimi.ingsw.model.gameDataManager;

import java.util.*;

public class Game {
    private Map<Player, Boolean> players;
    private TableOfDecks tableOfDecks;
    private PointTable pointTable;
    private Player winner;
    private boolean started;
    private String id;
    private Turn turn;
    private ArrayList<Player> playersOrder;

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
        this.players = players;
    }

    public void setPointTable(PointTable pointTable) {
        this.pointTable = pointTable;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public void setTableOfDecks(TableOfDecks tableOfDecks) {
        this.tableOfDecks = tableOfDecks;
    }

    public void setTurn(Turn turn) {
        this.turn = turn;
    }
    public ArrayList<Player> generatePlayersOrder(){
        this.playersOrder.addAll(this.players.keySet());
        Collections.shuffle(playersOrder);
        return this.playersOrder;
    }

    public boolean isStarted(){return started;}
}
