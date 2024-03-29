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
        this.games = new ArrayList<>(matches);
    }

    /**
     * this method adds a game to the list, if the game is already present it does not add it. The controller can use
     * this method when only some game crashed, and it wants to restore them.
     * @param game it is the new game
     */
    public void addGame(Game game) throws IllegalStateException{

        if(games.contains(game))
            throw new IllegalStateException();
        else {
            games.add(game);
            number = number + 1;
        }
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

    /**
     * this method allows to join a random game
     * @param player is the only parameter and indicates the player that wants to play
     */
    public synchronized void joinRandomGame(Player player){
        Optional <Game> g = this.games.stream()
                .filter(game -> (!game.isStarted() && game.checkName(player.getNick())))
                .findFirst();

        if(g.isPresent())
            g.get().addPlayer(player);
        else{
            //a new game should be created.
            Game newGame = new Game();
            this.addGame(newGame);
            //define a new game id
            newGame.setId("game n°: " + number);
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
            this.games.stream().filter(g -> g.getId().equals(gameId))
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
