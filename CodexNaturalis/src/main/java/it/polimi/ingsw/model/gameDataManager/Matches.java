package it.polimi.ingsw.model.gameDataManager;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This is the class that manages all the game (ongoing, suspended and ended). The user should be able to join a game
 * only by using this class, instruction like Game.join() should be avoided: this class often returns a list of
 * gameID that the client can use to join or to operate on that particular game.
 * @author Lodetti Alessandro
 */
public class Matches {
    private final List<Game> games;

    /**
     * it builds an empty ArrayList
     */
    public Matches() {
        this.games = new ArrayList<>();
    }

    /**
     * a constructor to restore the list of game in case the server crashes.
     * @param matches it's the list of the saved ongoing/suspended game.
     */
    public Matches(List<Game> matches){
        this.games = new ArrayList<>(matches);
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

    private void addNewGame(){
        Game g = new Game();
        games.add(g);
    }


    /**
     * a method that helps to know which games are waiting for players.
     * @return a List of String: the gameID.
     */
    public List<String> getGamesNotStartedYet(){
        return games.stream().filter(game -> !game.isStarted())
                .map(Game::getId)
                .collect(Collectors.toList());
    }

    /**use to find all the game where there is only one active player
     * @return a list of Game.
     */
    public List<String> getSuspendedGames(){
        return games.stream().filter(Game::isSuspended)
                .map(Game::getId)
                .collect(Collectors.toList());
    }

    public synchronized void joinRandomGame(Player player){
        this.games.stream().filter(game -> !game.isStarted())
                .findFirst()
                .ifPresent(game -> game.addPlayer(player));


    }
    public void joinGame(String gameId){

    }

    public void joinSuspendedGame(String gameId, String name){

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
