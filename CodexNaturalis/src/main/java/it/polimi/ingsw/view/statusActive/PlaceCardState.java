package it.polimi.ingsw.view.statusActive;

import it.polimi.ingsw.model.GameView;
import it.polimi.ingsw.model.card.PlayableCard;
import it.polimi.ingsw.model.exceptions.illegalOperationException;
import it.polimi.ingsw.networking.ClientAction;
import it.polimi.ingsw.view.GameFlow;
import it.polimi.ingsw.view.UI;

import java.awt.*;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;

import static it.polimi.ingsw.view.statusWaiting.StateWaiting.inputGetter;

public class PlaceCardState extends StateActive{

    private GameView view;
    private UI ui;
    private ClientAction client;
    private String nickName;

    public PlaceCardState(GameFlow flow) {
        super(flow);
        view = flow.getView();
        ui = flow.getUi();
        client = flow.getClient();
        nickName = flow.getNickname();
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        Optional<PlayableCard> cardCheck;
        boolean isFrontOrBack = true;
        boolean isBooleanValid = false;

        ui.show_playerHand(view);
        ui.show_gameStation(view);

        boolean flag = false;

        while(!flag) {
            try {
                do {
                    ui.show_requestCardId();
                    int cardIdInput = scanner.nextInt();

                    cardCheck = view.getCurrentPlayer().showCard().stream()
                            .filter(card -> card.getCardId() == cardIdInput)
                            .findFirst();

                }while (!cardCheck.isPresent());

                do {
                    ui.show_requestSide();
                    try {
                        isFrontOrBack = scanner.nextBoolean();
                        isBooleanValid = true;
                    }catch (InputMismatchException e){
                        isBooleanValid = false;}
                }while (!isBooleanValid);

                ui.show_requestCoordinates();

                int x = scanner.nextInt();
                int y = scanner.nextInt();
                Point coord = new Point(x, y);
                client.playCard(cardCheck.get(), coord, nickName, isFrontOrBack);
                flag = true;
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (illegalOperationException e) {
                flag = false;
            }
        }
        this.nextState();
    }

    @Override
    public void nextState() {new DrawCardState(flow).execute();
    }

    @Override
    public void setView(GameView view) {
        this.view = view;
    }
}