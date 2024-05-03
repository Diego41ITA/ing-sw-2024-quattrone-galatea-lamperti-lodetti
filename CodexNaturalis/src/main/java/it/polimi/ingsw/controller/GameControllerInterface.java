package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.card.*;
import it.polimi.ingsw.model.exceptions.illegalOperationException;
import it.polimi.ingsw.model.gameDataManager.Player;

import java.awt.*;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

//questa interfaccia contiene le azioni che un player può fare all'interno di una partita
//quando programmeremo la RMI vedremo quali di questi metodi servono davvero, quelli che non servono li toglliamo
public interface GameControllerInterface extends Remote {
    //in game controller ci sono anche i metodi per pescare/ottenere il giocatore attuale/vedere se si può iniziare la partita
    // questi metodi essendo synchronized non me li fa aggiungere qua


   // all'interno di questo metodo vengono aggiunti i player all'interno della partita e anche il numero massimo di giocatori
    public void setMaxNumberPlayers(String name, int max);
    //viene messo il colore nella pointtable e nel player
    public void setColor(String color, String name);
    //pgioca una carta sul tavolo da gioco
    public void playCard(PlayableCard card, Point cord, String nick) throws illegalOperationException;
    //cambia lo stato dei giocatori(connesso o non connesso)
    public void changePlayerStatus(String nick, Boolean value);
    //cambia il modo di piazzare la carta
    public Card cardIsFrontChanger(Card card, Boolean value);
    //inizializza il tavolo da gioco, compreso i giocatori nei turni
    public void initializeTable();

    public boolean notify20PointReached();
    //calcola i punti dei giocatori attraverso le carte obbiettivo, aggiorna la point table e ritorna  il giocatore con il punteggio più alto
    public String calculateWinner();

    public int calculateGoldPoints(GoldCard card, String nick);
    //somma ai punti del giocatore tot punti
    public void addPoints2Player(String nick, int point);

    public ArrayList<PlayableCard> showPlayerHand(String nick);

    public void chooseGoal(ArrayList<GoalCard> goals, int num, String nick);
    //passa al giocatore successivo cambiando il giocatore corrente
    public void goOn();
    //mette nella mano del giocatore 2 carte risorsa e 1 carta oro
    public void initializeHandPlayer(String nick);


    //ritorna la lista di goalcard che l'utente può scegliere
    public ArrayList<GoalCard> getPossibleGoals(); //va aggiunta una notify



 //aggiunge un giocatore alla partita(bisogna gestire il caso in cui non si possa aggiungere)
    public void addPlayer(Player p) throws IllegalStateException;
//metodo che ritorna i punti della carta risorsa
    public int getResourcePoint(ResourceCard card);
// metodo che ritorna id del game
    public String getGameId();
//METODO CHE ritorna i players
    public HashMap<Player, Boolean> getPlayers();


}
