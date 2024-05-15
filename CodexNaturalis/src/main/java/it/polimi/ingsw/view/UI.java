package it.polimi.ingsw.view;

import it.polimi.ingsw.model.GameView;
import it.polimi.ingsw.model.card.GoalCard;
import it.polimi.ingsw.model.card.InitialCard;
import it.polimi.ingsw.model.card.PlayableCard;
import it.polimi.ingsw.model.gameDataManager.GameStation;

/**
 * This is interface is implemented by the GUI and TUI
 * It shows all the possible outputs to the player about information or inputs required
 * @author Luca Lamperti
 */
public interface UI {

    public void show_message(String message);
    public void show_initialCard(InitialCard card);
    public void show_RequestPlayerNickName();

    public void show_startingMenu();

    public void show_joinRandomGame();

    public void show_requestPlayerColor(GameView gameView);

    public void show_playerColors(GameView gameView);

    public void show_noAvailableGames();

    public void show_invalidIdGame();

    public void show_NickAlreadyUsed(GameView gameView);

    public void show_createNewGame();

    public void show_RequestNumberOfPlayers();

    public void show_reconnectGame();

    public void show_RequestGameId();

    public void show_invalidNickToReconnect(String id);

    public void show_currentPlayersStatus(GameView gameView);

    public void show_GameStatus(GameView gameView);

    public void show_playerJoined(String id);

    public void show_playerLeft(String playerNickName);

    public void show_gameStarting(String id);

    public void show_isYourTurn(GameView immutableModel);

    public String show_goalCard(GoalCard card);

    public void show_playerHand(GameView immutableModel);

    public String show_playableCard(PlayableCard card);

    public void show_gameStation(GameView view);

    public void show_notEnoughResources();

    public void show_invalidPlay();

    public void show_tableOfDecks(GameView immutableModel);//forse è meglio dividere in TableDecksCommonGoals e TableDecksCommonDrawCards

    public void show_pointTable(GameView immutableModel);

    public void show_lastTurn(); //indica che un giocatore ha raggiunto 20 punti ed è l'ultimo turno

    public void show_gameOver();
}
