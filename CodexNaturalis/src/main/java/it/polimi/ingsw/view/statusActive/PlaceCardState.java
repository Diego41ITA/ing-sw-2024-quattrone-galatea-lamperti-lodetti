package it.polimi.ingsw.view.statusActive;

import it.polimi.ingsw.model.GameView;
import it.polimi.ingsw.model.exceptions.illegalOperationException;
import it.polimi.ingsw.networking.ClientAction;
import it.polimi.ingsw.view.GameFlow;
import it.polimi.ingsw.view.UI;

import java.awt.*;
import java.util.Scanner;

public class PlaceCardState extends StateActive{

    private GameFlow flow;
    private GameView view = flow.getView();
    private UI ui = flow.getUi();
    private ClientAction client = flow.getClient();
    private String nickName = flow.getNickname();

    public PlaceCardState(GameFlow flow) {
        super(flow);
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);

        ui.show_playerHand(view);
        ui.show_gameStation(view.getMyGameStation(nickName));
        ui.show_message("""
                CHOOSE A CARD ID:
                """);
        int cardId = scanner.nextInt();
        ui.show_message("""
                YOU WANNA PLAY IT FRONT OR BACK:
                """);
        Boolean isFrontOrBack = scanner.nextBoolean();
        ui.show_message("""
                CHOOSE A COORD:
                
                ENTER X COORDINATE THAN Y COORDINATE:
                """);
        int x = scanner.nextInt();
        int y = scanner.nextInt();
        Point coord = new Point(x, y);
        try {
            client.playCard(cardId, coord, nickName, isFrontOrBack);
        } catch (illegalOperationException e) {
            //non dovrebbe arrivare qui l'exception o sbaglio? (dovrebbe esser gestita con le notify)
        }
    }

    @Override
    public void nextState() {
        new DrawCardState(flow).execute();
    }
}
