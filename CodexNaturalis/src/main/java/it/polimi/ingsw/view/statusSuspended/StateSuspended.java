package it.polimi.ingsw.view.statusSuspended;

import it.polimi.ingsw.networking.ClientAction;
import it.polimi.ingsw.view.GameFlow;
import it.polimi.ingsw.view.UI;

public class StateSuspended {
    private GameFlow flow;
    private UI ui;
    private ClientAction client;

    public StateSuspended(GameFlow flow){
        this.flow = flow;
        this.ui = flow.getUi();
        this.client = flow.getClient();
    }

    /**
     * this method shows the GameStatus and shows a message.
     */
    public void execute(){
        ui.show_GameStatus(flow.getView());
        ui.show_message("this game is temporally suspended :(\n");
        ui.show_message("these are your cards, goal and game station.");
        ui.show_goalCard(flow.getView().getPlayer(flow.getNickname()).getGoal());
        ui.show_gameStation(flow.getView().getMyGameStation(flow.getNickname()));
        ui.show_playerHand(flow.getView());

        try {
            flow.wait();    //need to add some notify when the gameStatus change.
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
    }
}
