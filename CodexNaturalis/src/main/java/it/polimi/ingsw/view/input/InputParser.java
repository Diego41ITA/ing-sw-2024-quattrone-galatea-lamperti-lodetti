package it.polimi.ingsw.view.input;

import java.awt.*;

public interface InputParser {
    /**
     * it reads the nickname from the client
     * @return it returns the nickname
     */
    public String getNickName();


    public String getOption();

    /**
     * this gets the game id, for example when the client wants to rejoin a game
     * @return the string inserted by the client.
     */
    public String getGameId();

    /**
     * this gets the maximum number of player that can join a specific game.
     * @return the number of player.
     */
    public int getNumberOfPlayer();

    /**
     * to choose the side of the card
     * @return true if and only if its front side is visible, false otherwise.
     */
    public boolean getSideOfTheCard();

    /**
     * this method gets the id from the client
     * @return the id chosen by the client
     */
    public int getCardId();

    /**
     * this method gets the color from the user
     * @return the color chosen
     */
    public String getColor();

    /**
     * this method gets the type of the card that the client wants to draw
     * @return the type
     */
    public String getTypeOfCard();

    /**
     * this method gets A if the client wants to draw from a deck or B if he wants
     * to draw from the table.
     */
    public String getDrawFromDeckOrTable();

    /**
     * this method gets the coordinate where the client wants to place his card
     * @return a Point Object
     */
    public Point getCoordinate();
}
