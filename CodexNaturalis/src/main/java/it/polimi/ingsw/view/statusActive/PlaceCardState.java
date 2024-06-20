package it.polimi.ingsw.view.statusActive;

import it.polimi.ingsw.GameView.GameView;
import it.polimi.ingsw.model.card.PlayableCard;
import it.polimi.ingsw.model.exceptions.illegalOperationException;
import it.polimi.ingsw.networking.ClientAction;
import it.polimi.ingsw.view.FsmGame;
import it.polimi.ingsw.view.UI;
import it.polimi.ingsw.view.input.InputParser;
import it.polimi.ingsw.view.statusWaiting.StateWaiting;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Optional;

public class PlaceCardState extends StateActive{

    private GameView view;
    private UI ui;
    private ClientAction client;
    private String nickName;

    public PlaceCardState(FsmGame flow, InputParser input) {
        super(flow, input);
        view = flow.getView();
        ui = flow.getUi();
        client = flow.getClient();
        nickName = flow.getNickname();
    }

    @Override
    public void execute() {
        Optional<PlayableCard> cardCheck;
        boolean isFrontOrBack = true;
        boolean isBooleanValid = false;

        ui.show_playerHand(view, nickName);
        ui.show_gameStation(view);

        boolean flag = false;

        while(!flag) {
            try {
                do {
                    ui.show_requestCardId();
                    int cardIdInput = inputGetter.getCardId();

                    cardCheck = view.getCurrentPlayer().showCard().stream()
                            .filter(card -> card.getCardId() == cardIdInput)
                            .findFirst();

                }while (!cardCheck.isPresent());

                do {
                    ui.show_requestSide();
                    try {
                        isFrontOrBack = inputGetter.getSideOfTheCard();
                        isBooleanValid = true;
                    }catch (InputMismatchException e){
                        isBooleanValid = false;}
                }while (!isBooleanValid);

                Point coord;
                do {
                    ui.show_requestCoordinates();
                    coord = inputGetter.getCoordinate();
                }while(!checkCoordinate(coord));

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
    public void nextState() {new DrawCardState(flow, StateWaiting.inputGetter).execute();
    }

    @Override
    public void setView(GameView view) {
        this.view = view;
    }

    private boolean checkCoordinate(Point coord){
        ArrayList<Point> freeCoords = (ArrayList<Point>) this.view.getMyGameStation(nickName).getFreeCords();
        return freeCoords.contains(coord);
    }
}