package it.polimi.ingsw.view;

import it.polimi.ingsw.GameView.GameView;
import it.polimi.ingsw.model.card.GoalCard;
import it.polimi.ingsw.model.card.InitialCard;
import it.polimi.ingsw.model.card.PlayableCard;
import it.polimi.ingsw.model.gameDataManager.Color;
import it.polimi.ingsw.model.gameDataManager.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is interface is implemented by the GUI and TUI
 * It shows all the possible outputs to the player about information or inputs required
 * @author Luca Lamperti
 */
public interface UI {

    /**
     * It shows a generic message
     * @param message the shown String
     */
    public void show_message(String message);

    /**
     * it shows the initial card
     * @param card the shown card
     */
    public void show_initialCard(InitialCard card);

    /**
     * it shows a request for a nickname
     */
    public void show_RequestPlayerNickName();

    /**
     * it shows the options in the menu
     */
    public void show_startingMenu();

    /**
     * it shows that the client is joining a game
     */
    public void show_joinRandomGame();

    /**
     * it asks for a color
     * @param gameView the view that contains all the available colors.
     */
    public void show_requestPlayerColor(GameView gameView);

    /**
     * the player nicks and his chosen color
     * @param gameView the view that contains all the information
     */
    public void show_playerColors(GameView gameView);

    /**
     * it shows that there are no available games and it shows some option
     */
    public void show_noAvailableGames();

    /**
     * it shows that there is not a game with the written id
     */
    public void show_invalidIdGame();

    /**
     * it asks to type the number of player
     */
    public void show_RequestNumberOfPlayers();

    /**
     * it asks to type the id of the game
     */
    public void show_RequestGameId();

    /**
     * it says that the chosen nick was not playing in the selected game
     * @param id the selected nick
     */
    public void show_invalidNickToReconnect(String id);

    /**
     * it shows if the current player is active or not (the current player is the client that calls this method)
     * @param gameView the view that contains all the information
     */
    public void show_currentPlayersStatus(GameView gameView);

    /**
     * it shows the status of the game
     * @param gameView the view that contains all the information
     */
    public void show_GameStatus(GameView gameView);

    /**
     * it shows that a player has joined the game
     * @param id the nick of the new player.
     */
    public void show_playerJoined(String id);

    /**
     * it shows that the game is about to start
     * @param id the id of the game
     */
    public void show_gameStarting(String id);

    public void show_playingScene(GameView immutableModel);

    /**
     * this method shows whom is the current player
     * @param immutableModel the view that contains all the information
     */
    public void show_isYourTurn(GameView immutableModel);

    /**
     * it shows a goal card
     * @param card the gaol cart
     * @return a description of the card.
     */
    public String show_goalCard(GoalCard card);

    /**
     * it shows all the cards in the hand of nick
     * @param immutableModel the view that contains all the information
     * @param nickname the nickname of the player.
     */
    public void show_playerHand(GameView immutableModel, String nickname);

    /**
     * it shows a single playable card
     * @param card the card
     * @return a description of the card
     */
    public String show_playableCard(PlayableCard card);

    /**
     * it shows the game station of all the player
     * @param view the view that contains all the information
     */
    public void show_gameStation(GameView view);

    /**
     * it shows that the card was placed in forbidden place.
     */
    public void show_invalidPlay();

    /**
     * it shows the table of dacks
     * @param immutableModel the view that contains all the information
     */
    public void show_tableOfDecks(GameView immutableModel);

    /**
     * it shows the point table
     * @param immutableModel the view that contains all the information
     */
    public void show_pointTable(GameView immutableModel);

    /**
     * it shows that it is the last turn
     */
    public void show_lastTurn();

    /**
     * it shows that the game is over
     */
    public void show_gameOver();

    /**
     * it shows that this client won
     */
    public void show_youWin();

    /**
     * it shows that this client lost
     */
    public void show_youLose();

    /**
     * it shows all the possible goal cards
     * @param cards the goal cards
     */
    public void show_requestGoalCard(ArrayList<GoalCard> cards);

    /**
     * it asks to insert the card id
     */
    public void show_requestCardId();

    /**
     * it shows the names of all the winners
     * @param name all the names
     */
    public void show_winner(List<String> name);

    /**
     * it asks which type the player wants to draw
     */
    public void show_requestTypeToDraw();

    /**
     * it asks where the player wants to draw
     */
    public void show_drawFromWhere();

    /**
     * it shows that it is an invalid choice
     */
    public void show_invalidChoice();

    /**
     * it asks to choose a side for the card
     */
    public void show_requestSide();

    /**
     * it asks to insert a pair of coordinates
     */
    public void show_requestCoordinates();

    /**
     * it displays a connection error and game over
     */
    public void show_connectionError();

    /**
     * it shows invalid input message
     */
    public void show_invalidInput();

    /**
     * it shows that the command is invalid
     */
    public void show_invalidCommand();

    /**
     * it shows that we are waiting for other player to join the game
     */
    public void show_waitingOtherPlayers();

    /**
     * it asks if the player wants to leave
     */
    public void show_requestToLeave();

    /**
     * it shows that the game is aborted due to some irreversible errors
     */
    public void show_abortGame();

    /**
     * this static method calculate all the available color
     * @param gameView the gameView that contains the information about the colors
     * @return the list of all available colors
     */
    public static ArrayList<Color> freeColors(GameView gameView){
        ArrayList<Color> freeColors = new ArrayList<>(Arrays.asList(Color.YELLOW, Color.RED, Color.BLUE, Color.GREEN));
        if(gameView != null) {
            for (Player p : gameView.getPlayers()) {
                freeColors.remove(p.getColor());
            }
        }
        return freeColors;
    }
}
