package it.polimi.ingsw.view;

import it.polimi.ingsw.model.GameView;
import it.polimi.ingsw.model.gameDataManager.Status;
import it.polimi.ingsw.networking.ClientAction;
import it.polimi.ingsw.observer.GameObserver;
import it.polimi.ingsw.view.statusActive.StateActive;
import it.polimi.ingsw.view.statusFinished.StateFinished;
import it.polimi.ingsw.view.statusSuspended.StateSuspended;
import it.polimi.ingsw.view.statusWaiting.StateWaiting;

public class GameFlow implements Runnable, /*ClientAction,*/ GameObserver {
    private ClientAction client;
    private String nickname;
    private final UI ui;
    private GameView view;

    //un attributo per uscire dal ciclo (viene settato dall'ultimo stato)
    boolean stay = true;

    //metto 4 attributi State
    private StateWaiting state1;
    private StateActive state2;
    private StateSuspended state3;
    private StateFinished state4;

    //tutto il flusso di gioco...

    //implementa i metodi di gameObserver per svolgere finalmente delle operazioni concrete in risposta alle notifiche
    //che arrivano dal model

    //implementa runnable perché il cuore starà dentro ad un thread

    //implementa clientAction perchè chiamerà quei metodi sull'oggetto client (Socket o RMI)

    public GameFlow(String nick, UI ui, ClientAction client){
        this.nickname = nick;
        this.ui = ui;
        this.client = client;
    }

    public void run(){
        boolean stay = true;

        while(stay) {
            if(view.getStatus() == Status.WAITING) {
                state1.execute();   //ad ogni ciclo farà qualcosa, quando lo status cambierà cambierà anche l'azione effettuata
            } else if (view.getStatus() == Status.ACTIVE) {
                state2.execute();
            } else if (view.getStatus() == Status.SUSPENDED) {
                state3.execute();
            } else if (view.getStatus() == Status.FINISHED) {
                state4.execute();
            }
        }
    }

    public void exit(){
        this.stay = false;
    }

    public void setGameView(GameView game){
        this.view = game;
    }

    //bisogna implementare le robe di GameObserver e bisogna capire se gestire con eventi che contengono il tipo di
    //notifica o che fanno operazioni dirette in teoria sarebbe meglio utilizzare una coda ordinata poichè in questo
    //modo ogni evento è gestito dallo stato specifico.
    //

}
