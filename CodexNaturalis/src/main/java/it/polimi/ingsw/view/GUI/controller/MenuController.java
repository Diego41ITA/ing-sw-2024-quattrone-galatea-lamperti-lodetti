package it.polimi.ingsw.view.GUI.controller;

import it.polimi.ingsw.view.FsmGame;
import it.polimi.ingsw.view.GUI.controller.abstractControllers.AbstractController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuController extends AbstractController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void actionJoinRandomGame(ActionEvent e){ // quando viene premuto il bottone si salva
        //un valore all'interno di MultipleResponses
        multipleResponses.add("A");  // la successiva interfaccia verrò scelta dalla game flow dovo aver effettuato i controlli su l'input
    }
    public void actionReconnect(ActionEvent e){
        multipleResponses.add("B");
    }

    /**
     * this method set up additional attributes for the controller class if this is needed.
     *
     * @param updatedGame a new version of the game
     */
    @Override
    public void setUpController(FsmGame updatedGame) {
        setGame(updatedGame);
    }

}
