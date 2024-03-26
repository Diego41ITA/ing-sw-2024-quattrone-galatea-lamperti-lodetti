/**
 * This is the class that manages all the game (ongoing, suspended and ended)
 * @author Lodetti Alessandro
 */
package it.polimi.ingsw.model.gameDataManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Matches {
    private List<Game> games;

    /**
     * it builds an empty ArrayList
     */
    public Matches() {
        this.games = new ArrayList<>();
    }

    /**
     * this method adds a game to the list, if the game is already present it does not add it.
     * @param game it is the new game
     * @return returns a boolean: true if the game is correctly added otherwise false.
     */
    public boolean addGame(Game game){

        if(games.contains(game))
            return false;
        else {
            return games.add(game);
        }
    }

    /**
     * a method that helps to know which games are waiting for players.
     * @return a List of game.
     */
    public List<Game> getGamesNotStartedYet(){
        return games.stream().filter(game -> !game.isStarted())
                .collect(Collectors.toList());
    }

    /**
     * metodo da discutere perchè hanno detto che il nome deve essere diverso all'interno della partita e
     * non di tutte le partite. Questo controllo lo può fare Game.
     * @param name the name to check.
     * @return true only if the name is free.
     */
    public boolean checkName(String name){
        return false;
    }

    public boolean insertName(String name){
        return true;
    }

    public void findSavedGame(String id){

    }
}
