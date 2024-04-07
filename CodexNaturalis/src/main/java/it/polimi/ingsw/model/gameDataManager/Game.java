package it.polimi.ingsw.model.gameDataManager;

import java.util.*;

/**
 * this class represents a single game of CodexNaturalis.
 * @author Quattrone Diego
 */
public class Game {
    /**a HashMap that associates every player partecipating to a game with a boolean, representing its connection status. */
    private final Map<Player, Boolean> players;
    /**the maximum number of players that can partecipate to a game. */
    private int maxNumberPlayer;
    /**an object representing the common playing field, holding the decks and all the drawable cards. */
    private TableOfDecks tableOfDecks;
    /**an object representing the score board. */
    private PointTable pointTable;
    /**an enumeration representing the status of the game. */
    private Status status;
    /**a unique code associated with the game. */
    private final String id;
    /**an object that manages the orders in which players must play. */
    private Turn turn;

    //need to add constructors: one for the new game and one for the already started game.

    /**
     * it the base constructor; it only initializes the id.
     * @param id it's an unique attribute
     */
    public Game(String id){
        this.id = id;
        this.players = new HashMap<>();
        this.maxNumberPlayer = 4;   //in the future it could be
        this.tableOfDecks = new TableOfDecks(); //useless you can set it later with setTableOfDecks()
        this.pointTable = new PointTable(); //useless you can set it later with setPointTable()
        this.status = Status.WAITING;
        this.turn = new Turn(); //useless you can set it later with setTurn()
    }

    /**
     * this constructor initializes an already started game. For the attribute players is essential call another method
     * to copy the singular object contained in the map.
     * @param g
     */
    public Game(Game g){
        this.players = new HashMap<Player, Boolean>();
        setPlayers(g.getPlayers());
        this.maxNumberPlayer = g.getMaxNumberPlayer();
        this.tableOfDecks = new TableOfDecks(g.getTableOfDecks());
        this.pointTable = new PointTable(g.getPointTable());
        this.status = g.getStatus();
        this.id = g.getId();
        this.turn = new Turn(g.getTurn());

    }

    //-------------------------------------------------------------------------
    //we need to return a "copy": essential to preserve the encapsulation.

    public Map<Player, Boolean> getPlayers() {
        return new HashMap<>(players);
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

    public int getMaxNumberPlayer() {
        return maxNumberPlayer;
    }

    /**
     * this method return the game status.
     * @return a Status enum
     */
    public Status getStatus(){
        return this.status;
    }

    //------------------------------------------------------------------------


    /**
     * sets a new status for the current game
     * @param status it is an enum literal of Status
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * this method helps to instantiate a new map of player: these players already exist thus we need to utilize a copy
     * constructor also for the key player.
     * @param players map of players and their status: active (true), inactive (false)
     */
    public void setPlayers(Map<Player, Boolean> players) {
        for(Player p: players.keySet())
        {
            if(!this.players.containsKey(p))
                this.players.put(new Player(p), true);
        }
    }

    /**
     * it sets the max number of player that can play in this lobby
     * @param maxNumberPlayer it's int, which will be rounded if it exceeds maximum or minimum value.
     */
    public void setMaxNumberPlayer(int maxNumberPlayer){
        if(maxNumberPlayer > 4)
            this.maxNumberPlayer = 4;
        else if(maxNumberPlayer < 2)
            this.maxNumberPlayer = 2;
        else
            this.maxNumberPlayer = maxNumberPlayer;
    }



    //----------------------------------------------------------------
    //THESE METHODS ARE NOT NECESSARY BECAUSE THE ATTRIBUTES HAVE BEEN ALREADY INITIALIZED IN THE CONSTRUCTORS.
    //actually, if the first constructor is called there is the need to change this attribute content, to do that we
    //need to implement methods in this game class that allow to call specific method on the attribute.


    /**
     *
     * @param pointTable
     */
    public void setPointTable(PointTable pointTable) {

    }


    public void setTableOfDecks(TableOfDecks tableOfDecks) {
        this.tableOfDecks = tableOfDecks;
    }

    public void setTurn(Turn turn) {
        this.turn = turn;
    }


    /**
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

    //----------------------------------------------------------------


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
     * This method add a player to this game only if the number of current player is less then 4
     * @param player the new player
     * @throws IllegalStateException thrown if there are already 4 players
     * @author Lodetti Alessandro
     */
    public void addPlayer(Player player) throws IllegalStateException {
        if(players.size() >= this.maxNumberPlayer)
            throw new IllegalStateException();
        players.put(player, true);
    }
}
