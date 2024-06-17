package it.polimi.ingsw.model.gameDataManager;

import it.polimi.ingsw.model.exceptions.MaxPlayersInException;

import java.io.Serializable;
import java.util.*;

/**
 * this class represents a single game of CodexNaturalis.
 * @author Quattrone Diego
 */
public class Game implements Serializable {
    /**a HashMap that associates every player partecipating to a game with a boolean, representing its connection status. */
    private List<Player> players;
    private Map<String, Boolean> activity;
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
        this.players = new ArrayList<>();
        this.activity = new HashMap<>();
        this.maxNumberPlayer = 4;   //in the future it could be changed
        this.status = Status.WAITING;
        //this.turn = new Turn(); //useless you can set it later with setTurn()
    }

    /**
     * this constructor initializes an already started game. For the attribute players is essential call another method
     * to copy the singular object contained in the map.
     * @param g it's a Game object to copy.
     */
    public Game(Game g){
        this.players = new ArrayList<>(g.getPlayers());
        setPlayers(g.getPlayers());
        this.maxNumberPlayer = g.getMaxNumberPlayer();
        this.tableOfDecks = new TableOfDecks(g.getTableOfDecks());
        this.pointTable = new PointTable(g.getPointTable());
        this.status = g.getStatus();
        this.id = g.getId();
        this.turn = new Turn(g.getTurn());

    }

    public List<Player> getPlayers() {
        return new ArrayList<>(players);
    }

    public PointTable getPointTable() {
        return new PointTable(this.pointTable);
    }

    public String getId() {
        return this.id;
    }

    public TableOfDecks getTableOfDecks() {
        if (this.tableOfDecks == null) {
            this.tableOfDecks = new TableOfDecks();
        }
        return new TableOfDecks(this.tableOfDecks);
    }

    public Turn getTurn() {
        return new Turn(this.turn);
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
    public void setPlayers(List<Player> players) {
        this.players = new ArrayList<>(players);
    }

    /**
     * This method update the stats of the player passed as a parameter
     * @author Lodetti Alessandro
     * @param p is a Player Object with updated stats.
     */
    public void setSinglePlayer(Player p, Boolean bool){
        this.activity.remove(p.getNick());
        this.activity.put(p.getNick(), bool);
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

    /**
     *This method sets a new this.pointTable which is a copy of the parameter.
     * @author Lodetti Alessandro
     * @param pointTable it's the item to copy
     */
    public void setPointTable(PointTable pointTable) {
        this.pointTable = new PointTable(pointTable);   //makes a copy
    }

    /**
     *This method sets a new this.tableOfDecks which is a copy of the parameter.
     * @author Lodetti Alessandro
     * @param tableOfDecks it's the item to copy
     */
    public void setTableOfDecks(TableOfDecks tableOfDecks) {
        this.tableOfDecks = new TableOfDecks(tableOfDecks);
    }

    /**
     *This method sets a new this.turn which is a copy of the parameter.
     * @author Lodetti Alessandro
     * @param turn it's the item to copy
     */
    public void setTurn(Turn turn) {
        this.turn = new Turn(turn);
    }

    /**
     * This method checks the validity of the name.
     * @param name the name which needs to be checked
     * @return true if the name is correct, false otherwise.
     */
    public boolean checkName(String name){
        for(Player p: players){
            if(p.getNick().equals(name))
                return true;
        }
        return false;
    }

    public Player getPlayerByNick(String name){
        for(Player p: players){
            if(p.getNick().equals(name))
                return p;
        }
        System.err.println("No player with nick:" + name + "founded");
        return null;
    }

    /**
     * this method starts the game by assigning active status to "this.status"
     */
    public void start() {
        if(players.size() == maxNumberPlayer && this.activity.values().stream().allMatch(b -> b))
            this.status = Status.ACTIVE;
    }

    /**
     * This method suspends the game only if the previous status was ACTIVE
     * @throws IllegalStateException if the previous status was not ACTIVE
     */
    public void suspend(){
        if(this.status == Status.ACTIVE || this.status == Status.SUSPENDED)
            this.status = Status.SUSPENDED;
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
        return activity.get(player.getNick());
    }

    /**
     * This method add a player to this game only if the number of current player is less then 4
     * @author Lodetti Alessandro
     * @param player the new player
     * @throws MaxPlayersInException thrown if there are already 4 players
     */
    public void addPlayer(Player player) throws MaxPlayersInException {
        if(players.size() >= this.maxNumberPlayer)
            throw new MaxPlayersInException();
        players.add(player);
        activity.put(player.getNick(), true);
    }

    /**
     * Remove a player from the game
     * @param nick Nickname of the player to remove
     */
    public void removePlayer(String nick) {
        this.players.removeIf(entry -> entry.getNick().equals(nick));
        this.activity.entrySet().removeIf(entry -> entry.getKey().equals(nick));
    }


    public void reconnectPlayer(String nick) {
        boolean matched = false;
        for(Player player : this.players){
            if(player.getNick().equals(nick) && !this.activity.get(player.getNick())) {
                this.activity.put(player.getNick(), true);
                matched = true;
                //return;
            }
        }
        if(activity.values().stream().allMatch(b -> b))
            this.status = Status.ACTIVE;
        if(!matched)
            System.err.println("Error during player" + nick + "reconnection.");
    }

    public int getNumOfOnlinePlayers(){
        int count = 0;
        for (Boolean value : activity.values()) {
            if (value) {
                count++;
            }
        }
        return count;
    }

    public boolean isColorAvailable(String color) {
        for (Player player : players) {
            if (player.getColor() != null && player.getColor().toString().equalsIgnoreCase(color)) {
                return false;
            }
        }
        return true;
    }

    public void printAvailableColors() {
        System.out.println("Available colors:");
        Set<Color> chosenColors = new HashSet<>();
        for (Player player : players) {
            Color playerColor = player.getColor();
            if (playerColor != null) {
                chosenColors.add(playerColor);
            }
        }
        for (Color color : Color.values()) {
            if (!chosenColors.contains(color)) {
                System.out.println(color);
            }
        }
    }

    /**
     * this method is an override useful to put a game into a hash-map and to use Map methods like: containsKey() ecc...
     * @return this method returns the hash code of every Game object with the same gameId.
     */
    @Override
    public int hashCode(){
        return this.getId().hashCode();
    }

    /**
     * this method compares this object with another Object passed as parameter.
     * @param game it's the only parameter and its type is Object. That's because this method override superclass
     *             method. Be aware that you should pass an object that has the same type of "this"
     * @return true if the two object have the same id (same gameID), false otherwise.
     */
    @Override
    public boolean equals(Object game){
        try{
            Game newGame = (Game) game;
            if(this.getId().equals(newGame.getId()))
                return true;
            else
                return false;
        }catch(ClassCastException e){
            return false;
        }
    }

    public void setActivity(Map<String, Boolean> activity) {
        this.activity = activity;
    }

    public Map<String, Boolean> getActivity(){
        return this.activity;
    }
}
