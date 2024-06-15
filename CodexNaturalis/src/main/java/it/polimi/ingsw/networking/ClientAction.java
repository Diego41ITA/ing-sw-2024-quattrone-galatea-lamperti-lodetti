package it.polimi.ingsw.networking;

import it.polimi.ingsw.model.card.Card;
import it.polimi.ingsw.model.card.GoalCard;
import it.polimi.ingsw.model.card.InitialCard;
import it.polimi.ingsw.model.card.PlayableCard;
import it.polimi.ingsw.model.exceptions.illegalOperationException;

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
    void rejoin(String nick, String idGame) throws IOException, InterruptedException;

    void playCard(PlayableCard playedCard, Point cord, String nick, boolean front) throws IOException, illegalOperationException;
    void chooseGoal(ArrayList<GoalCard> goals, int num, String nick) throws RemoteException;
    void goOn() throws IOException;
    void setColor(String color, String name) throws RemoteException;
    void drawPlayableCardFromTableOfDecks(String nick, String deck) throws IOException;
    void drawFromTable(String nick, Card card) throws IOException;
    void setGameStation(String nick, InitialCard card, boolean isFront) throws RemoteException;
    //questo metodo non so quanto senso possa avere (ci pensa il controller a capire quando inizializzare la partita)
    void initializeHandPlayer(String nick) throws IOException;

    //Questo metodo non ha proprio senso. Quando il controller si accorge che la partita sta finendo valuta gli ultimi
    //gli ultimi giocatori e successivamente calcola il vincitore notificandolo ai player.
    String calculateWinner() throws IOException;

    //prova
    void startGame() throws IOException;

    //messaggi nuovi per risolvere il problema socket
    void definePlayer(String nick) throws IOException;
    void initializeTurn(String nick) throws IOException;

}

