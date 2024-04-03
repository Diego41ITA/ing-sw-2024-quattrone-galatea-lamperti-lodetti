package it.polimi.ingsw.model.gameDataManager;

import java.util.*;

public class Game {
    private Map<Player, Boolean> players;
    private int maxNumberPlayer;
    private TableOfDecks tableOfDecks;
    private PointTable pointTable;
    private Player winner; //we can remove it.
    private Status status;
    private String id;
    private Turn turn;

    //need to add constructors: one for the new game and one for the already started game.


    /**
     * @author Lodetti Alessandro
     * This method checks the validity of the name.
     * @param name the name which needs to be checked
     * @return true if the name is correct, false otherwise.
     */
    public boolean checkName(String name){
        for(Player p: players.keySet()){
            if(p.getNick().equals(name))
                return false;
        }
        return true;
    }

    public Map<Player, Boolean> getPlayers() {
        return new HashMap<>(players);
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


    public void setTableOfDecks(TableOfDecks tableOfDecks) {
        this.tableOfDecks = tableOfDecks;
    }

    public void setTurn(Turn turn) {
        this.turn = turn;
    }

    /**
     * this method return the game status.
     * @return a Status enum
     */
    public Status getStatus(){
        return this.status;
    }

    /**
     * this method starts the game by assigning active status to "this.status"
     */
    public void start() {
        if(players.size() == maxNumberPlayer)
            this.status = Status.ACTIVE;
    }

    /**
     * This method suspends the game only if the previous status was ACTIVE
     * @throws IllegalStateException if the previous status was not ACTIVE
     */
    public void suspend() throws IllegalStateException {
        if(this.status == Status.ACTIVE)
            this.status = Status.SUSPENDED;
        else
            throw new IllegalStateException("this game is not active");
    }

    /**
     * this method ends the game only if the previous status was ACTIVE
     * @throws IllegalStateException if the previous status was not ACTIVE
     */
    public void endGame() throws IllegalStateException{
        if(this.status == Status.ACTIVE)
            this.status = Status.FINISHED;
        else
            throw new IllegalStateException("this game is not active");
    }

    /**
     * this method checks if the player is active.
     * @param player the player to check.
     * @return true if the player is active, false otherwise.
     */
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
    }
}
