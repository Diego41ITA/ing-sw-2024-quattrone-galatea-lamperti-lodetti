package it.polimi.ingsw.view;

import it.polimi.ingsw.model.GameView;

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

    public void ask_PlayerNickName();

    public void ask_GameId();

    public void ask_numberOfPlayers();

    public void show_playerJoined(String playerNickName);

    public void show_playerLeft(String playerNickName);

    public void show_gameStarting(String id);

    public void show_isYourTurn(String playerNickName);

    public void show_playerHand(GameView ImmutableModel);

    public void show_gameStation(GameView ImmutableModel);

    public void show_invalidPlay();

    public void show_goalCard(GameView ImmutableModel);

    public void show_playableCard(GameView ImmutableModel);

    public void show_tableOfDecks(GameView ImmutableModel);//forse è meglio dividere in TableDecksCommonGoals e TableDecksCommonDrawCards

    public void show_lastTurn(); //indica che un giocatore ha raggiunto 20 punti ed è l'ultimo turno

    public void show_pointTable(GameView ImmutableModel);

    public void show_gameOver();
}
