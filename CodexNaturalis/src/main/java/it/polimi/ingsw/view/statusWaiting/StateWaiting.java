package it.polimi.ingsw.view.statusWaiting;

import it.polimi.ingsw.model.GameView;
import it.polimi.ingsw.networking.ClientAction;
import it.polimi.ingsw.view.GameFlow;
import it.polimi.ingsw.view.UI;

import java.rmi.RemoteException;
import java.util.Scanner;

public class StateWaiting {
    private GameFlow flow;
    private GameView view = flow.getView();
    private UI ui = flow.getUi();
    private ClientAction client = flow.getClient();
    private String nickName = flow.getNickname();

    public StateWaiting() {}

    public void setFlow(GameFlow flow) {
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
                }catch (RemoteException e){
                    //exception da gestire
                }
                break;
            case "B":
                ui.show_RequestGameId();
                String gameID = sc.nextLine();
                try {
                    client.rejoin(nickName, gameID);
                }catch (RemoteException e){
                    //exception da gestire
                }
        }
    }
}
