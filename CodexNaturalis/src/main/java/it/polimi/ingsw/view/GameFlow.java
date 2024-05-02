package it.polimi.ingsw.view;

import it.polimi.ingsw.networking.ClientAction;
import it.polimi.ingsw.observer.GameObserver;

public class GameFlow implements Runnable, ClientAction, GameObserver {
    private ClientAction client;
    private String nickname;
    private final Ui ui;

    //tutto il flusso di gioco....

    //implementa i metodi di gameObserver per svolgere finalmente delle operazioni concrete in risposta alle notifiche
    //che arrivano dal model

    //implementa runnable perché il cuore starà dentro ad un thread

    //implementa clientAction perchè chiamerà quei metodi sull'oggetto client (Socket o RMI)
}
