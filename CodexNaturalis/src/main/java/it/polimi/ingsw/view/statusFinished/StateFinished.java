package it.polimi.ingsw.view.statusFinished;

import it.polimi.ingsw.networking.ClientAction;
import it.polimi.ingsw.view.GameFlow;
import it.polimi.ingsw.view.UI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;

public class StateFinished {
    private GameFlow flow;
    private final UI ui;
    private final ClientAction client;

    public StateFinished(GameFlow flow){
        this.flow = flow;
        this.ui = flow.getUi();
        this.client = flow.getClient();
    }

    public void execute(){
        ui.show_GameStatus(flow.getView());
        ui.show_gameOver();
        while(flow.getWinner() == null) {
            try {
                flow.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        ui.show_message("the winner is: " + flow.getWinner());
        if(flow.getWinner().equals(flow.getNickname()))
            ui.show_message("congrats you won");
        else
            ui.show_message("loser");
        askToLeave();
    }

    private void askToLeave(){
        ui.show_message("press any button to leave the game");
        //Bisogna verificare che la ui sia una cli
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            char character = (char) reader.read();
            client.leaveGame(flow.getNickname(), flow.getView().getId());
        } catch (IOException | NotBoundException e) {
            throw new RuntimeException(e);
        }
    }
}

