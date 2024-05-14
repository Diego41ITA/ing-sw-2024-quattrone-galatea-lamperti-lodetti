package it.polimi.ingsw.view.GUI.controller;

import it.polimi.ingsw.view.GUI.Gui;
import it.polimi.ingsw.view.GUI.MultipleResponses;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuController extends MultipleResponses implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void actionJoinRandomGame(ActionEvent e){ // quando viene premuto il bottone si salva
        //un valore all'interno di MultipleResponses
        MultipleResponses.add("A");  // la successiva interfaccia verrò scelta dalla game flow dovo aver effettuato i controlli su l'input
    }
    public void actionReconnect(ActionEvent e){
        MultipleResponses.add("B");
    }

}
