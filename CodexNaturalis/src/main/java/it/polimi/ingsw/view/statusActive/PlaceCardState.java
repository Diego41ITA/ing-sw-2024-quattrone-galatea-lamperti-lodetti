package it.polimi.ingsw.view.statusActive;

import it.polimi.ingsw.GameView.GameView;
import it.polimi.ingsw.model.card.PlayableCard;
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

/**
 * this state allows the player to play the cards wherever he wants: in respect of the forms and the limits of the
 * constitution.
 */
public class PlaceCardState extends StateActive{

    private GameView view;
    private UI ui;
    private ClientAction client;
    private String nickName;

    /**
     * this builds an instance of the class
     * @param flow the corresponding FsmGame
     * @param input the corresponding inputGetter
     */
    public PlaceCardState(FsmGame flow, InputParser input) {
        super(flow, input);
        view = flow.getView();
        ui = flow.getUi();
        client = flow.getClient();
        nickName = flow.getNickname();
    }

    /**
     * this method contains the logic to ask for information and play the card
     */
    @Override
    public void execute() {
        Optional<PlayableCard> cardCheck;
        boolean isFrontOrBack = true;
        boolean isBooleanValid = false;

        ui.show_playerHand(view, nickName);
        ui.show_gameStation(view);
        ui.show_playingScene(view);

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
            }
        }
        this.nextState();
    }

    /**
     * this method allows to go to the next state which is DrawCardState
     */
    @Override
    public void nextState() {new DrawCardState(flow, StateWaiting.inputGetter).execute();
    }

    /**
     * this method sets the View
     * @param view the view that's needed to be set.
     */
    @Override
    public void setView(GameView view) {
        this.view = view;
    }

    /**
     * this method checks if the coord is available
     * @param coord the coord that needs to be checked
     * @return true if and only if the coord is available
     */
    private boolean checkCoordinate(Point coord){
        ArrayList<Point> freeCoords = (ArrayList<Point>) this.view.getMyGameStation(nickName).getFreeCords();
        return freeCoords.contains(coord);
    }
}