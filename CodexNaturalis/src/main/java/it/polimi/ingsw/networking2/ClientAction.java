package it.polimi.ingsw.networking2;

//questa interfaccia indica tutte le azioni possibili effettuabili da un client
//notate che rappresenta il punto di contatto tra ClientRMI e ClientSocket perchè entrambi la estenderanno
public interface ClientAction {
    void createGame(String nick);
    void joinRandomGame(String nick);
    void joinGame(String nick, String gameId);

    //e tutte le altre azioni che può effettuare un client

    /*
    qui non c'è bisogno di passare una GameView perchè si vuole rendere la rete trasparente
    è poi il singolo Client (socket o rmi che risolve questo problema)
     */
}

