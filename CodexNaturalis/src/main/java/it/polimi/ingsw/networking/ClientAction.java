package it.polimi.ingsw.networking;

import it.polimi.ingsw.model.card.Card;
import it.polimi.ingsw.model.card.GoalCard;
import it.polimi.ingsw.model.card.PlayableCard;
import it.polimi.ingsw.model.exceptions.illegalOperationException;

import java.awt.*;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

//questa interfaccia indica tutte le azioni possibili effettuabili da un client
//notate che rappresenta il punto di contatto tra ClientRMI e ClientSocket perchè entrambi la estenderanno
public interface ClientAction {
    void createGame(String nick, int maxNumberOfPlayers) throws IOException, InterruptedException, NotBoundException;

    void leaveGame(String nick, String idGame) throws NotBoundException, IOException, InterruptedException;

    void joinRandomGame(String nick) throws IOException, InterruptedException, NotBoundException/*,NoAvailableGameToJoinException*/;
    void rejoin(String nick, String gameId) throws IOException, InterruptedException, NotBoundException;
    void playCard(PlayableCard playedCard, Point cord, String nick, boolean front) throws IOException, illegalOperationException;
    void chooseGoal(ArrayList<GoalCard> goals, int num, String nick) throws RemoteException;
    void goOn() throws IOException;
    void setColor(String color, String name) throws RemoteException;
    void drawPlayableCardFromTableOfDecks(String nick, String deck) throws IOException;
    void drawFromTable(String nick, Card card) throws IOException;

    //questo metodo non so quanto senso possa avere (ci pensa il controller a capire quando inizializzare la partita)
    void initializeHandPlayer(String nick) throws IOException;

    //Questo metodo non ha proprio senso. Quando il controller si accorge che la partita sta finendo valuta gli ultimi
    //gli ultimi giocatori e successivamente calcola il vincitore notificandolo ai player.
    String calculateWinner() throws IOException;

    //prova
    void startGame() throws IOException;

}

