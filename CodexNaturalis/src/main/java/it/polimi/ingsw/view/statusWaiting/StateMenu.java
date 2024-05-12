package it.polimi.ingsw.view.statusWaiting;

import it.polimi.ingsw.networking.ClientAction;
import it.polimi.ingsw.view.GameFlow;
import it.polimi.ingsw.view.UI;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class StateMenu extends StateWaiting {
    private UI ui;
    private ClientAction client;
    private String nickName;

    public StateMenu(GameFlow flow) {
        super(flow);
        ui = flow.getUi();
        client = flow.getClient();
        nickName = flow.getNickname();
    }

    @Override
    public void execute() {
        Scanner sc = new Scanner(System.in);
        String input, inputParsed;

        ui.show_startingMenu();
        input = sc.nextLine();
        inputParsed = input.toUpperCase();
        switch (inputParsed) {
            case "A":
                ui.show_joinRandomGame();
                try {
                    client.joinRandomGame(nickName);
                } catch (RemoteException | NotBoundException e) {
                    ui.show_message("CONNECTION ERROR, GAME OVER...");
                    try {
                        StateWaiting.flow.wait(100); // non sono sicuro
                        StateWaiting.flow.exit();
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                break;
            case "B":
                ui.show_RequestGameId();
                String gameID = sc.nextLine();
                try {
                    client.rejoin(nickName, gameID);
                } catch (RemoteException | NotBoundException e) {
                    ui.show_message("CONNECTION ERROR, GAME OVER...");
                    try {
                        StateWaiting.flow.wait(100); // non sono sicuro
                        StateWaiting.flow.exit();
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                break;
            default:
                ui.show_message("INVALID COMMAND\n");
                break;
        }

        if (StateWaiting.flow.inGame == false) {
            ui.show_noAvailableGames();
            Scanner scanner = new Scanner(System.in);
            input = scanner.nextLine();
            inputParsed = input.toUpperCase();
            switch (inputParsed) {
                case ("A"):
                    ui.show_RequestNumberOfPlayers();
                    int numberOfPlayer = scanner.nextInt();
                    try {
                        client.createGame(StateWaiting.flow.getNickname(), numberOfPlayer);
                    } catch (NotBoundException | RemoteException e) {
                        ui.show_message("CONNECTION ERROR, GAME OVER...");
                        try {
                            StateWaiting.flow.wait(100); // non sono sicuro
                            StateWaiting.flow.exit();
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    break;
                case ("B"):
                    StateWaiting.flow.exit();
                    break;
            }
        }

        nextState();
    }
    @Override
    public void nextState(){
        new StateColor(StateWaiting.flow).execute();
    }
}
