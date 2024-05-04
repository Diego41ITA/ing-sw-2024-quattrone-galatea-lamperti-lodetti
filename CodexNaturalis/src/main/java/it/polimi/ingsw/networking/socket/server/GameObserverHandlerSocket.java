package it.polimi.ingsw.networking.socket.server;

import it.polimi.ingsw.model.GameView;
import it.polimi.ingsw.observer.GameObserver;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.rmi.RemoteException;

public class GameObserverHandlerSocket implements GameObserver, Serializable {
    private final ObjectOutputStream out;
    public GameObserverHandlerSocket(ObjectOutputStream o){
        this.out = o;
    }

    //Bisogna fare l'override dei metodi di gameObserver, come per GameObserverHandlerClient bisgnerà inviare un
    //messaggio questo verrà scritto sull'OutputStream
    @Override
    public void playerJoined(GameView game) throws RemoteException{
        try{
            out.writeObject(new msgPlayerJoined(game));
        }catch(IOException e){
            //
        }
    }

    //ecc...
}
