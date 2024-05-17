package it.polimi.ingsw.view.GUI.controller;

import it.polimi.ingsw.view.GUI.Gui;
import javafx.event.ActionEvent;

public class CreateGameController extends Gui {
    public void exit(ActionEvent e){
        System.exit(-1);;
    }
    //mi fa vedere l'interfaccia relativa al setting dei giocatori
    public void createGame(ActionEvent e){
        //fin quando in multiplesResponses non abbiamo "GameCreated" allora il gameflow rimane nello stesso punto
        //nel gameflow dobbiamo avere un ciclo while che controlla l'ultimo valore di multipleResponses nella ui
        multipleResponses.add("GameCreated");
    }
}
