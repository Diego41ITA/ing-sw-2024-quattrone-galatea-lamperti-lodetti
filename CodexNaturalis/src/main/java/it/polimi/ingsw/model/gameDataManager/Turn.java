package it.polimi.ingsw.model.gameDataManager;
import java.util.*;

/**
 * @author Lodetti Alessandro
 * This class defines the object turn, and it has the list of player and another attribute to store the current player.
 * With all the methods defined the attribut turnNumber is totaly useless.
 */
public class Turn {

    //it is possible to do this with nicknames only.
    private final List<Player> players;
    private Player currentPlayer;


    public Turn(){
        this.players = new LinkedList<Player>();
        this.currentPlayer = new Player();
    }

    /**
     * this is the only constructor current player it is initialized as null.
     * @param players it is the list of player that joined a game.
     */
    public Turn(List<Player> players){
        this.players = new LinkedList<>(players);
        this.currentPlayer = null;
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
}
