package it.polimi.ingsw.observer;

import it.polimi.ingsw.model.GameView;

import java.rmi.RemoteException;
import java.util.*;

//questa è una collezione di GameObserver
public class HandleObserver {
    private GameObserver observer;

    public HandleObserver(GameObserver obs){
        this.observer = obs;
    }

    public synchronized List<GameObserver> getObservers(){
        return observers;
    }

    //metodo per notificare tutti i player che un nuovo giocatore si è unito
    public synchronized void notifyNewPlayerJoined(GameView model){
        for(GameObserver obs : observers){
            try{
                obs.playerJoined(model);
            }catch(RemoteException e){
                //...
            }
        }
    }

    //stessa cosa per tutte le altre notifiche.
}
