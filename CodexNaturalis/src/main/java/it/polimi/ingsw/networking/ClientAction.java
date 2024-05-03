package it.polimi.ingsw.networking;

import it.polimi.ingsw.model.card.PlayableCard;

import java.awt.*;
import java.util.List;

//questa interfaccia indica tutte le azioni possibili effettuabili da un client
//notate che rappresenta il punto di contatto tra ClientRMI e ClientSocket perchè entrambi la estenderanno
public interface ClientAction {
    void createGame(String nick);
    void joinRandomGame(String nick);
    void joinGame(String nick, String gameId);
    void rejoin(String nick, String gameId);
    void setMaxNumOfPlayer(String name, int max);
    void playCard(PlayableCard card, Point cord, String nick);
    void chooseGoal(List<PlayableCard> goals, int num, String nick); //pensare ad una notify che restituisca due goal card
    //void chooseGoal(int num, String nick);

    void pass(String nick); //dipende dalla logica di GameFlow
    void drawFromDeck(String nick, String typeOfDeck);
    void drawFromTable(String nick, int card);


    //e tutte le altre azioni che può effettuare un client

    /*
    qui non c'è bisogno di passare una GameView perchè si vuole rendere la rete trasparente
    è poi il singolo Client (socket o rmi che risolve questo problema)
     */
}

