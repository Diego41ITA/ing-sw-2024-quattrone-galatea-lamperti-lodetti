package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.card.*;
import it.polimi.ingsw.model.exceptions.illegalOperationException;

import java.awt.*;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/*

       QUI CI STANNO ******SOLO****** I METODI CHE INTERESSANO AL CLIENT!!!!

*/
//questa interfaccia contiene le azioni che un player può fare all'interno di una partita
//quando programmeremo la RMI vedremo quali di questi metodi servono davvero, quelli che non servono li toglliamo
public interface GameControllerInterface extends Remote {
    //in game controller ci sono anche i metodi per pescare/ottenere il giocatore attuale/vedere se si può iniziare la partita
    // questi metodi essendo synchronized non me li fa aggiungere qua

    //viene messo il colore nella point table e nel player
    public void setColor(String color, String name) throws RemoteException;

    //pgioca una carta sul tavolo da gioco
    void playCard(PlayableCard playedCard, String nick, boolean front, Point cord) throws RemoteException, illegalOperationException;
    void drawPlayableCardFromTableOfDecks(String typo, String nick) throws RemoteException;

    //calcola i punti dei giocatori attraverso le carte obbiettivo, aggiorna la point table e ritorna  il giocatore con il punteggio più alto
    public String calculateWinner() throws RemoteException;

    public void chooseGoal(ArrayList<GoalCard> goals, int num, String nick) throws RemoteException;
    //passa al giocatore successivo cambiando il giocatore corrente
    public void goOn() throws RemoteException;
    //mette nella mano del giocatore 2 carte risorsa e 1 carta oro
    public void initializeHandPlayer(String nick) throws RemoteException;

    public void getPossibleGoals(String nickname) throws RemoteException;

    void drawFromTable(Card card, String nick) throws RemoteException;
    void setGameStation(String nick, InitialCard card, boolean front) throws RemoteException;

    void initializePlayers(String nick) throws RemoteException;

    //prova
    void start_Game() throws RemoteException;

    //metodi aggiunti per risolvere socket
    void definePlayer(String nick) throws RemoteException;
    void initializeTurn(String nick) throws RemoteException;

}
