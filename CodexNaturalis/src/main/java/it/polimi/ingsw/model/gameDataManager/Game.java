package it.polimi.ingsw.model.gameDataManager;

import java.util.*;

public class Game {
    private Map<Player, Boolean> players;
    private int maxNumberPlayer;
    private TableOfDecks tableOfDecks;
    private PointTable pointTable;
    private Player winner; //we can remove it.
    private boolean started;
    private boolean suspended;
    private boolean finished;
    private String id;
    private Turn turn;
    private List<Player> playersOrder;

    //need to add constructors: one for the new game and one for the already started game.

    public boolean isStarted() {
        return this.started;
    }
    public boolean checkName(String name){
        for(Player p: players.keySet()){
            if(p.getNick().equals(name))
                return false;
        }
        return true;
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

    public void setPlayers(HashMap<Player, Boolean> players) {
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
    public List<Player> generatePlayersOrder(){
        this.playersOrder.addAll(this.players.keySet());
        Collections.shuffle(playersOrder);
        return this.playersOrder;
    }

    public boolean isConnected(Player player){
        return players.get(player);
    }


    /**
     * @author Lodetti Alessandro
     * This method add a player to this game only if the number of current player is less then 4
     * @param player the new player
     * @throws IllegalStateException thrown if there are already 4 players
     */
    public void addPlayer(Player player) throws IllegalStateException {
        if(players.size() >= this.maxNumberPlayer)
            throw new IllegalStateException();
        players.put(player, true);
        if(players.size() == this.maxNumberPlayer)
            this.started = true;
    }

    public boolean isSuspended(){return suspended;}
    public boolean isFinished(){return finished;}
}
