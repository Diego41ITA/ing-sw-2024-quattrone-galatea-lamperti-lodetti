package it.polimi.ingsw.controller;


import it.polimi.ingsw.controller.FSM.StartingState;
import it.polimi.ingsw.controller.FSM.State;
import it.polimi.ingsw.model.gameDataManager.*;

public class GameController {
    final Game game;
    private State currentState;

    public GameController() {

        this.game = new Game("123");
        currentState = new StartingState();

    }

    //metodi per modificare game e getter informazioni per notificare cambiamenti

}