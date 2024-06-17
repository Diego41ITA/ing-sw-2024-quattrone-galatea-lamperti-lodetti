package it.polimi.ingsw.view.statusWaiting;

import it.polimi.ingsw.model.gameDataManager.Status;
import it.polimi.ingsw.networking.ClientAction;
import it.polimi.ingsw.view.FsmGame;
import it.polimi.ingsw.view.UI;
import it.polimi.ingsw.view.input.InputParser;

import java.io.IOException;
import java.rmi.NotBoundException;

public class StateMenu extends StateWaiting {
    private UI ui;
    private ClientAction client;
    private String nickName;

    public StateMenu(FsmGame flow, InputParser input) {
        super(flow, input);
        ui = flow.getUi();
        client = flow.getClient();
        nickName = flow.getNickname();
    }

    @Override
    public void execute() {
        Boolean validInput = false;
        Boolean exitRequest = false;
        String input, inputParsed;
        do {
            ui.show_startingMenu();
            input = inputGetter.getOption();
            inputParsed = input.toUpperCase();
            switch (inputParsed) {
                case "A":
                    validInput = true;
                    ui.show_joinRandomGame();
                    try {
                        client.joinRandomGame(nickName);
                    } catch (NotBoundException | IOException | InterruptedException e) {
                        ui.show_connectionError();
                        try {
                            StateWaiting.flow.wait(100); // non sono sicuro
                            StateWaiting.flow.exit();
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    break;
                case "B":
                    validInput = true;
                    ui.show_RequestGameId();
                    String gameId = inputGetter.getGameId();
                    try {
                        client.rejoin(gameId, nickName);
                    } catch (InterruptedException | IOException e) {
                        ui.show_connectionError();
                    }
                    break;
                default:
                    ui.show_invalidCommand();
                    break;
            }
        }while(!validInput);

        //if one of the two previous command ends up in nothing it asks the client if he wants to create a new game.
        if (StateWaiting.flow.inGame == false) {
            validInput = false;
            do {
                ui.show_noAvailableGames();
                input = inputGetter.getOption();
                inputParsed = input.toUpperCase();
                switch (inputParsed) {
                    case ("A"):
                        int numberOfPlayer;
                        validInput = true;
                        do {
                            ui.show_RequestNumberOfPlayers();
                            numberOfPlayer = inputGetter.getNumberOfPlayer();
                            if (numberOfPlayer != 0) { //per ora gestiamo anche numeri a caso (unico errore lo da per input mismatch)
                                try {
                                    client.createGame(StateWaiting.flow.getNickname(), numberOfPlayer);
                                } catch (NotBoundException | IOException | InterruptedException e) {
                                    ui.show_invalidInput();
                                    try {
                                        StateWaiting.flow.wait(100); // non sono sicuro
                                        StateWaiting.flow.exit();
                                    } catch (InterruptedException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                }
                            } else {
                                ui.show_invalidInput(); //problema da risolvere su ordine di stampa
                            }
                        } while (numberOfPlayer == 0);

                        break;
                    case ("B"):
                        validInput = true;
                        exitRequest = true;
                        StateWaiting.flow.exit();
                        break;
                    default:
                        ui.show_invalidCommand();
                        break;
                }
            } while (!validInput);
        }

        //if the game is SUSPENDED or ACTIVE it should not ask for a color (it already has one)
        if(!exitRequest && (flow.getView().getStatus() == Status.WAITING))
            nextState();
    }
    @Override
    public void nextState(){
        new StateColor(StateWaiting.flow, inputGetter).execute();
    }
}
