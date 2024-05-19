package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.card.*;
import it.polimi.ingsw.model.exceptions.illegalOperationException;

import java.awt.*;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * this interface defines all the operation that are available on a GameController object.
 */
public interface GameControllerInterface extends Remote {

    /**
     * it is used to set the color for a specific player
     * @param color the color that the player wants
     * @param name the name of the player
     * @throws RemoteException
     */
    public void setColor(String color, String name) throws RemoteException;

    /**
     * it is used to place a card on a specific coordinate.
     * @param playedCard the card that the player wants to place.
     * @param nick the name of the player
     * @param front explain the observable side of the card once it gets placed
     * @param cord the coordinate where to play the card
     * @throws RemoteException
     * @throws illegalOperationException
     */
    void playCard(PlayableCard playedCard, String nick, boolean front, Point cord) throws RemoteException, illegalOperationException;

    /**
     * this method try to draw a card from the specified deck.
     * @param typo indicates the name of the deck (resource or gold)
     * @param nick it's the player's name.
     * @throws RemoteException
     */
    void drawPlayableCardFromTableOfDecks(String typo, String nick) throws RemoteException;

    /**
     * calculate players point and notify the winner???
     * @return the name of the winner.
     * @throws RemoteException
     */
    public String calculateWinner() throws RemoteException;

    /**
     * this method tries to select a goal card as the personal goal for the client.
     * @param goals these are the possible goals
     * @param num this indicates which goal card the player chose
     * @param nick this is the name of the client
     * @throws RemoteException
     */
    public void chooseGoal(ArrayList<GoalCard> goals, int num, String nick) throws RemoteException;

    /**
     * it's called by the current client when he ends his turn: it defines a new current player
     * @throws RemoteException
     */
    public void goOn() throws RemoteException;

    /**
     * it is used to initialize the hand of the player. (forse inutile perché viene chiamato da definePlayer() poi)
     * @param nick the client's name
     * @throws RemoteException
     */
    public void initializeHandPlayer(String nick) throws RemoteException;

    /**
     * it is used to draw two goal card and to make the player able to choose which one he wants.
     * @param nickname the player nickname.
     * @throws RemoteException
     */
    public void getPossibleGoals(String nickname) throws RemoteException;

    /**
     * it is used by the player to draw a card from the table
     * @param card the card that he wants
     * @param nick the player's name
     * @throws RemoteException
     */
    void drawFromTable(Card card, String nick) throws RemoteException;

    /**
     * it is used to initialize the Player's GameStation with the initial card
     * @param nick the name of the player
     * @param card the initial card
     * @param front the side
     * @throws RemoteException
     */
    void setGameStation(String nick, InitialCard card, boolean front) throws RemoteException;

    /**
     * initialize the player and draw the initial card
     * @param nick the player's name
     * @throws RemoteException
     */
    void initializePlayers(String nick) throws RemoteException;

    /**
     * it is called by the last client (the one is not waiting): it notifies the controller that the game can start.
     */
    void start_Game() throws RemoteException;

    /**
     * this method is used in response to the startingGame notification and it allows the controller to make all the
     * initialization.
     * @param nick the player that is ready to be initialized
     * @throws RemoteException
     */
    void definePlayer(String nick) throws RemoteException;

    /**
     * initialized the turn (useless)
     * @param nick player's nickname
     * @throws RemoteException
     */
    void initializeTurn(String nick) throws RemoteException;

}
