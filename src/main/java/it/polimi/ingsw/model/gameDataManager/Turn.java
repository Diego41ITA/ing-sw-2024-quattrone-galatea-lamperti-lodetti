package it.polimi.ingsw.model.gameDataManager;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Lodetti Alessandro
 * This class defines the object turn, and it has the list of player and another attribute to store the current player.
 * With all the methods defined the attribut turnNumber is totaly useless.
 * @author Lodetti Alessandro
 */
public class Turn implements Serializable {

    //it is possible to do this with nicknames only.
    private List<Player> players;
    private Player currentPlayer;
    private boolean isLast = false;
    private String endingPlayerNick = null;

    public void setEndingPlayer(String nick){
        this.endingPlayerNick = nick;
    }

    public String getEndingPlayer(){
        return this.endingPlayerNick;
    }

    public void setIsLast(boolean flag){
        this.isLast=flag;
    }

    public boolean checkIfLast(){
        return this.isLast;
    }

    //probabilmente inutile perchè non andrebbe messo nel costruttore base di game. (si usa dopo il set)
    //public Turn(){
        //this.players = new LinkedList<Player>();
        //this.currentPlayer = new Player();
    //}

    /**
     * this is the only constructor current player it is initialized as null.
     * @param players it is the list of player that joined a game.
     */
    public Turn(List<Player> players){
        this.players = new ArrayList<>(players);
        if(!this.players.isEmpty()) this.sort();
    }

    public Turn(Turn t){
        this.players = new ArrayList<>();
        setPlayers(t.getPlayers());
        if(!t.getPlayers().isEmpty()) this.currentPlayer = new Player(t.getCurrentPlayer());
        this.endingPlayerNick = t.endingPlayerNick;
        this.isLast = t.isLast;
    }

    /**
     * This method is used to sort the order. In this way, it is not guaranteed that the first player to join
     * will be also the first one to play.
     */
    public void sort(){
        Collections.shuffle(this.players);
        this.currentPlayer = players.getFirst();
    }

    /**
     * useful to know who is the first player.
     * @return the first Player's nickname
     */
    public String getFirstPlayerNick(){
        return players.getFirst().getNick();
    }

    /**
     * useful to know who is currently playing.
     * @return the current player's nickname
     */
    public String getCurrentPlayerNick(){
        return currentPlayer.getNick();
    }

    /**
     * useful to know who is the last to play
     * @return  the last player's nickname
     */
    public String getLastPlayerNick(){
        return players.getLast().getNick();
    }

    /**
     * this method makes the game goes on: after the call of this method the
     * currentPlayer = \old(currentPlayer).next()
     */
    public void goOn(){
        if((players.indexOf(this.currentPlayer) + 1) == players.size())
            this.currentPlayer = players.getFirst();
        else{
            int nextIndex = players.indexOf(this.currentPlayer) + 1;
            this.currentPlayer = players.get(nextIndex);
        }
    }

    /**
     * return all the playing players
     * @return a copy of the private attribute
     */
    public List<Player> getPlayers(){
        return new ArrayList<>(this.players);
    }

    /**
     * add all the player passed checking for homonyms.
     * @param l is a list of player.
     */
    public void setPlayers(List<Player> l){
        for(Player p: l) {
            if (!this.players.stream()
                    .map(Player::getNick)
                    .toList()
                    .contains(p.getNick())) {
                this.players.add(new Player(p));
            }
        }
    }

    /**
     * return the current player
     * @return a copy of the private attribute.
     */
    public Player getCurrentPlayer(){
        return new Player(this.currentPlayer);
    }
}
