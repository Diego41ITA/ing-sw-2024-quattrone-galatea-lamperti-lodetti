package it.polimi.ingsw.view.statusWaiting;

import it.polimi.ingsw.networking.ClientAction;
import it.polimi.ingsw.view.GameFlow;
import it.polimi.ingsw.view.UI;

import java.rmi.RemoteException;
import java.util.Scanner;

public class StateColor extends StateWaiting {
    private UI ui;
    private ClientAction client;
    private String nick;

    public StateColor(GameFlow flow){
        super(flow);
        this.ui = flow.getUi();
        this.client = flow.getClient();
        this.nick = flow.getNickname();
    }

    @Override
    public void execute(){
        ui.show_requestPlayerColor(StateWaiting.flow.getView());
        Scanner scanner = new Scanner(System.in);
        String color = scanner.nextLine();
        String colorParsed = color.toLowerCase();
        try {
            client.setColor(colorParsed, StateWaiting.flow.getNickname());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        StateWaiting.flow.waitingForNewPlayers = true;
    }

    @Override
    public void nextState(){
        //it does nothing
    }
}
