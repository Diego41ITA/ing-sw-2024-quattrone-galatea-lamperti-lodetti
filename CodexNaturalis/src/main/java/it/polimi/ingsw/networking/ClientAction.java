package it.polimi.ingsw.networking;

import it.polimi.ingsw.model.card.GoalCard;
import it.polimi.ingsw.model.card.PlayableCard;
import it.polimi.ingsw.model.exceptions.NoAvailableGameToJoinException;
import it.polimi.ingsw.model.exceptions.illegalOperationException;

import java.awt.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

//questa interfaccia indica tutte le azioni possibili effettuabili da un client
//notate che rappresenta il punto di contatto tra ClientRMI e ClientSocket perchè entrambi la estenderanno
public interface ClientAction {
    void createGame(String nick, int maxNumberOfPlayers) throws RemoteException, NotBoundException;

    void leaveGame(String nick, String idGame) throws NotBoundException, RemoteException;

    void joinRandomGame(String nick) throws RemoteException, NotBoundException/*,NoAvailableGameToJoinException*/;
    void rejoin(String nick, String gameId) throws RemoteException, NotBoundException;
    void playCard(int card, Point cord, String nick,boolean front) throws illegalOperationException;
    void chooseGoal(ArrayList<GoalCard> goals, int num, String nick);
    void goOn();
    void setColor(String color, String name);
    void drawPlayableCardFromTableOfDecks(String nick, String deck);
    void drawFromTable(String nick, int card);

    //questo metodo non so quanto senso possa avere (ci pensa il controller a capire quando inizializzare la partita)
    void initializeHandPlayer(String nick);

    //Questo metodo non ha proprio senso. Quando il controller si accorge che la partita sta finendo valuta gli ultimi
    //gli ultimi giocatori e successivamente calcola il vincitore notificandolo ai player.
    String calculateWinner();

}

