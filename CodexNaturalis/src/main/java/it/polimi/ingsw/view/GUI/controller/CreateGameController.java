package it.polimi.ingsw.view.GUI.controller;

import it.polimi.ingsw.view.FsmGame;
import javafx.event.ActionEvent;

public class CreateGameController extends AbstractController {
    public void exit(ActionEvent e){
        System.exit(-1);;
    }
    //mi fa vedere l'interfaccia relativa al setting dei giocatori
    public void createGame(ActionEvent e){
        //fin quando in multiplesResponses non abbiamo "GameCreated" allora il GUIinput rimane nello stesso punto
        //nel gGuiInput dobbiamo avere un ciclo while che controlla l'ultimo valore di multipleResponses.
        multipleResponses.add("A");
    }

    /**
     * this method set up additional attributes for the controller class if this is needed.
     *
     * @param updatedGame is the new version of the game
     */
    @Override
    public void setUpController(FsmGame updatedGame) {
        setGame(updatedGame);
    }
}
