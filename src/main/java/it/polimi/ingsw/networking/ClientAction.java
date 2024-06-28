package it.polimi.ingsw.networking;

import it.polimi.ingsw.model.card.Card;
import it.polimi.ingsw.model.card.GoalCard;
import it.polimi.ingsw.model.card.InitialCard;
import it.polimi.ingsw.model.card.PlayableCard;
import it.polimi.ingsw.model.exceptions.illegalOperationException;
import it.polimi.ingsw.model.gameDataManager.TableOfDecks;

import java.awt.*;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * this interface defines all the possible actions that a client can perform on the server.
 */
public interface ClientAction {
    /**
     * this method asks to create a new game
     * @param nick the name of the client
     * @param maxNumberOfPlayers the number max number of player that can join this game.
     * @throws IOException
     * @throws InterruptedException
     * @throws NotBoundException
     */
    void createGame(String nick, int maxNumberOfPlayers) throws IOException, InterruptedException, NotBoundException;

    /**
     * this method asks to leave the game
     * @param nick the name of the client
     * @param idGame the id of the game that the client wants to leave
     * @throws NotBoundException
     * @throws IOException
     * @throws InterruptedException
     */
    void leaveGame(String nick, String idGame) throws NotBoundException, IOException, InterruptedException;

    /**
     * this method asks to join a random game
     * @param nick the name of the client
     * @throws IOException
     * @throws InterruptedException
     * @throws NotBoundException
     */
    void joinRandomGame(String nick) throws IOException, InterruptedException, NotBoundException;

    /**
     * this method allows the player to rejoin a game
     * @param nick the nickname of the player
     * @param idGame the id of the game that he wants to rejoin
     * @throws IOException
     * @throws InterruptedException
     */
    void rejoin(String idGame, String nick) throws IOException, InterruptedException;

    /**
     * This method allows the player to play a card
     * @param playedCard the chosen card to play
     * @param cord the coordinate on the GameStation to play the card
     * @param nick the nickName of the player
     * @param front the side of the card to play
     * @throws IOException
     */
    void playCard(PlayableCard playedCard, Point cord, String nick, boolean front) throws IOException;

    /**
     * This method allows a player to choose its personal secret GoalCard
     * @param goals the 2 possible GoalCards to choose
     * @param num the position in the array of the chosen card
     * @param nick the nickName of the player
     * @throws RemoteException
     */
    void chooseGoal(ArrayList<GoalCard> goals, int num, String nick) throws RemoteException;

    /**
     * This method is called after drawing a card and makes the Turn go on
     * @throws IOException
     */
    void goOn() throws IOException;

    /**
     * This method set the personal player's color
     * @param color the chosen color to set
     * @param name the nickName of the player
     * @throws RemoteException
     */
    void setColor(String color, String name) throws RemoteException;

    /**
     * This method draws a card from the two decks (either resources or golds) and puts it in the
     * player's hand
     * @param nick the nickname of the player
     * @param deck the textual representation of the chosen deck
     * @throws IOException
     */
    void drawPlayableCardFromTableOfDecks(String nick, String deck) throws IOException;

    /**
     * This method draws a {@link Card} from the face-up cards in {@link TableOfDecks}
     * @param card The card drawn.
     * @param nick The nickname of the Player that draws the card.
     */
    void drawFromTable(String nick, Card card) throws IOException;

    /**
     * This method allows a player to choose the initial card (the side where he likes to place it);
     * and it sets the card on the player's GameStation
     * @param nick it is the name of the client
     * @param card it is the initial card to place
     * @param isFront indicates if the card needs to be displaced on its front or its back.
     */
    void setGameStation(String nick, InitialCard card, boolean isFront) throws RemoteException;

    void initializeHandPlayer(String nick) throws IOException;

    /**
     * This method periodically pings the server, to detect disconnection
     * @param nick
     * @throws IOException
     */
    void ping(String nick) throws IOException;

    /**
     * This method starts the game
     * @throws IOException
     */
    void startGame() throws IOException;

    /**
     * this method is used to initialize the player object to avoid NullPointerException.
     * @param nick it's the name of the player.
     */
    void definePlayer(String nick) throws IOException;

    void initializeTurn(String nick) throws IOException;

}

