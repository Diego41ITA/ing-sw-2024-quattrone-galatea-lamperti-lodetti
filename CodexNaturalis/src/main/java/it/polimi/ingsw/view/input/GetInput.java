package it.polimi.ingsw.view.input;

public interface GetInput {
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


}
