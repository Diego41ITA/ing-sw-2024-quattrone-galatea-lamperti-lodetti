package it.polimi.ingsw.view.GUI.controller;

import it.polimi.ingsw.view.GUI.Gui;
import javafx.event.ActionEvent;

public class CreateGameController extends Gui {
    public void exit(ActionEvent e){
        System.exit(-1);;
    }
    //mi fa vedere l'interfaccia relativa al setting dei giocatori
    public void createGame(ActionEvent e){
        show_RequestNumberOfPlayers();
    }
}
