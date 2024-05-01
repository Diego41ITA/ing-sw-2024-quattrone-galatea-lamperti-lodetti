package it.polimi.ingsw.controller2;

import it.polimi.ingsw.observer.GameObserver;

import java.util.*;
import java.io.Serializable;

// questa classe rappresenta semplicemente il controller per la creazione ed eliminazione delle partite
public class MainController implements Serializable, MainControllerInterface /*, Runnable*/ {
    private List<GameController> activeGames;
    private static MainController mainController;

    //questa prima parte implementa il singleton pattern per avere una sola istanza di MainController
    private MainController(){
        this.activeGames = new ArrayList<>();
    }
    public synchronized static MainController getMainController(){
        if(mainController == null)
            mainController = new MainController();
        return mainController;
    }

    //ora bisogna overridare i metodi dell'interfaccia.
    @Override
    public synchronized GameControllerInterface createGame(GameObserver obs, String nick){
        //... qui l'implementazione si limita a definire un nuovo Game (o GameController).
        //quindi in questo punto non interessa più della rete perchè siamo ad un livello più alto.
    }

    //stessa cosa per tutti gli altri.

    //bisogna aggiungere anche i metodi per il salvataggio e l'eliminazione (questo conviene farlo con un thread).

}
