package it.polimi.ingsw.view;

import it.polimi.ingsw.model.GameView;
import it.polimi.ingsw.observer.GameObserver;

import java.io.Serializable;
import java.rmi.RemoteException;

//implementa GameObserver e nel caso di RMI viene esportata in modo che quando si effettua una chiamata a metodi di
//GameObserver si comporta come un oggetto remoto => ha come effetto quello di chiamare metodi di flow di conseguenza
//notifica il flusso di gioco.

//funziona sia con socket che con Rmi
public class GameObserverHandlerClient implements GameObserver, Serializable {
    private GameFlow flow;
    public GameObserverHandlerClient(GameFlow flow){
        this.flow = flow;
    }

    @Override
    public void playerJoined(GameView model) throws RemoteException{
        flow.playerJoined(model);
    }

    //Override di tutti gli altri messaggi che il server può mandare al client
}
