package it.polimi.ingsw.view;

import it.polimi.ingsw.model.GameView;
import it.polimi.ingsw.model.card.GoalCard;
import it.polimi.ingsw.model.card.GoldCard;
import it.polimi.ingsw.model.card.PlayableCard;

/**
 * This is interface is implemented by the GUI and TUI
 * It shows all the possible outputs to the player about information or inputs required
 * @author Luca Lamperti
 */
public interface UI {

    public void show_startingMenu();

    public void show_joinRandomGame();

    public void show_noAvailableGames();

    public void show_createNewGame();

    public void show_reconnectGame();

    public void show_RequestPlayerNickName();

    public void show_RequestGameId();

    public void show_RequestNumberOfPlayers();

    public void show_playerJoined(String playerNickName);

    public void show_playerLeft(String playerNickName);

    public void show_gameStarting(String id);

    public void show_isYourTurn(GameView immutableModel);

    public void show_playerHand(GameView immutableModel);

    public void show_gameStation(GameView immutableModel);

    public void show_invalidPlay();

    public void show_goalCard(GoalCard card);

    public void show_playableCard(PlayableCard card);

    public void show_tableOfDecks(GameView immutableModel);//forse è meglio dividere in TableDecksCommonGoals e TableDecksCommonDrawCards

    public void show_lastTurn(); //indica che un giocatore ha raggiunto 20 punti ed è l'ultimo turno

    public void show_pointTable(GameView immutableModel);

    public void show_gameOver();
}
