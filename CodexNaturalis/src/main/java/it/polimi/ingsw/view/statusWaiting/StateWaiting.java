package it.polimi.ingsw.view.statusWaiting;

import it.polimi.ingsw.model.GameView;
import it.polimi.ingsw.model.exceptions.NoAvailableGameToJoinException;
import it.polimi.ingsw.networking.ClientAction;
import it.polimi.ingsw.view.GameFlow;
import it.polimi.ingsw.view.UI;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class StateWaiting {
    private GameFlow flow;
    private UI ui = flow.getUi();
    private ClientAction client = flow.getClient();
    private String nickName = flow.getNickname();

    public StateWaiting(GameFlow flow) {
        this.flow = flow;
    }

    public void execute(){
        Scanner sc = new Scanner(System.in);
        String input;

        ui.show_startingMenu();
        input = sc.nextLine();
        switch(input){
            case "A":
                ui.show_joinRandomGame();
                try {
                    client.joinRandomGame(nickName);
                }catch (RemoteException | NotBoundException e){
                    ui.show_message("CONNECTION ERROR, GAME OVER...");
                    try {
                        flow.wait(100); // non sono sicuro
                        flow.exit();
                    }catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                break;
            case "B":
                ui.show_RequestGameId();
                String gameID = sc.nextLine();
                try {
                    client.rejoin(nickName, gameID);
                }catch (RemoteException | NotBoundException e){
                    ui.show_message("CONNECTION ERROR, GAME OVER...");
                    try {
                        flow.wait(100); // non sono sicuro
                        flow.exit();
                    }catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
        }
    }
}
