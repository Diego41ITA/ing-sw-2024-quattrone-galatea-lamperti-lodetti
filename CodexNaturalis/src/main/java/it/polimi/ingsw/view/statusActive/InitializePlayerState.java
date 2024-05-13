package it.polimi.ingsw.view.statusActive;

import it.polimi.ingsw.model.GameView;
import it.polimi.ingsw.model.card.PlayableCard;
import it.polimi.ingsw.networking.ClientAction;
import it.polimi.ingsw.view.GameFlow;
import it.polimi.ingsw.view.UI;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;

public class InitializePlayerState extends StateActive{

    private GameView view = flow.getView();
    private UI ui = flow.getUi();
    private ClientAction client = flow.getClient();
    private String nickName = flow.getNickname();

    public InitializePlayerState(GameFlow flow) {
        super(flow);
        view = flow.getView();
        ui = flow.getUi();
        client = flow.getClient();
        nickName = flow.getNickname();
    }

    @Override
    public void execute() {
        try {
            client.initializePlayers(nickName);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        Scanner scanner = new Scanner(System.in);
        Boolean isFrontOrBack = true;
        boolean isBooleanValid = false;

        ui.show_message("""
                THIS IS YOUR INITIAL CARD
                
                """);

        ui.show_playerHand(view);

        do {
            ui.show_message("""
                DO YOU WANNA PLAY IT FRONT OR BACK:
                
                ENTER TRUE TO PLAY IF FRONT, FALSE TO PLAY IF BACK
                """);
            try {
                isFrontOrBack = scanner.nextBoolean();
                isBooleanValid = true;
            }catch (InputMismatchException e){
                isBooleanValid = false;
            }
        }while (!isBooleanValid);

        try {
            client.setGameStation(nickName, isFrontOrBack);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void nextState() {
        new PlaceCardState(flow).execute();
    }

    @Override
    public void setView(GameView view) {
        this.view = view;
    }
}
