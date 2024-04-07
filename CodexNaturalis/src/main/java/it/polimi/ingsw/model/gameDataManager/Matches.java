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
    private static int number = 0;

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
        this.games = new ArrayList<>();
        setMatches(matches);    //set the attribute at correct state
    }

    /**
     * this is a private method used to instantiate the private attribute games when the client wants to restore
     * a particular situation
     * @param matches it's a list of all the game that needs to be restored.
     */
    private void setMatches(List<Game> matches){
        for(Game g: matches){
            this.addGame(g);
        }
    }

    /**
     * this method adds a game to the list, if the game is already present it does not add it. The controller can use
     * this method when only some game crashed, and it wants to restore them.
     * @param game it is the new game
     */
    public void addGame(Game game){
        if(!games.contains(game))
        {
            games.add(new Game(game));
        }
    }

    /**
     * when a game ends need to be removed
     * @param gameId the id of the finished game
     * @throws IllegalStateException this is thrown when the gameId does not match.
     */
    public void removeGame(String gameId) throws IllegalStateException {
        Optional<Game> g = this.games.stream()
                .filter(game -> game.getId().equals(gameId))
                .findFirst();

        if(g.isPresent()){
            this.games.remove(g.get());
        }
        else{
            throw new IllegalStateException();
        }
    }

    /**
     * a method that helps to know which games are waiting for players.
     * @return a List of String: the gameID.
     */
    public List<String> getGamesNotStartedYet(){
        return games.stream().filter(game -> game.getStatus() == Status.WAITING)
                .map(Game::getId)
                .collect(Collectors.toList());
    }

    /**use to find all the game where there is only one active player
     * @return a list of Game.
     */
    public List<String> getSuspendedGames(){
        return games.stream().filter(game -> game.getStatus() == Status.SUSPENDED)
                .map(Game::getId)
                .collect(Collectors.toList());
    }

    /**
     * provides all the games that have being created
     * @return an ArrayList.
     */
    public List<Game> getMatches(){
        return new ArrayList<>(this.games);
    }

    /**
     * this method allows to join a random game
     * @param player is the only parameter and indicates the player that wants to play
     */
    public synchronized void joinRandomGame(Player player){
        Optional <Game> g = this.games.stream()
                .filter(game -> (game.getStatus() == Status.WAITING && game.checkName(player.getNick())))
                .findFirst();

        if(g.isPresent())
            g.get().addPlayer(player);
        else{
            //a new game should be created.
            Game newGame = new Game("game n°: " + number);
            number = number + 1;
            this.addGame(newGame);
            newGame.addPlayer(player);
        }
    }

    /**
     * this method allows to join a specific game; it has a try-catch block to catch the eventual IllegalStateException
     * thrown by addPlayer().
     * Maybe the try-catch could be added to the controller and there the joinRandomGame() could be called.
     * @param gameId it's the key of the game
     * @param player it's the player that wants to join the game.
     */
    public synchronized void joinGame(String gameId, Player player) {
        try {
            this.games.stream()
                    .filter(g -> g.getId().equals(gameId) && g.checkName(player.getNick()))
                    .findFirst()
                    .ifPresent(g -> g.addPlayer(player));
            System.out.println("you successfully joined the desired lobby!");
        } catch (IllegalStateException e) {
            System.out.println("4 player are currently playing in the required lobby.\n" +
                    " You'll be added to a random lobby.");
            joinRandomGame(player);
        }
    }
}
