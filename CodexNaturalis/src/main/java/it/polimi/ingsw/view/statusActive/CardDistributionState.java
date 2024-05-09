package it.polimi.ingsw.view.statusActive;

import it.polimi.ingsw.model.GameView;
import it.polimi.ingsw.networking.ClientAction;
import it.polimi.ingsw.view.GameFlow;
import it.polimi.ingsw.view.UI;

public class CardDistributionState extends StateActive{

    private GameFlow flow;
    private GameView view = flow.getView();
    private UI ui = flow.getUi();
    private ClientAction client = flow.getClient();
    private String nickName = flow.getNickname();

    protected CardDistributionState(GameFlow flow) {
        super(flow);
    }

    @Override
    public void execute() {
        client.initializeHandPlayer(nickName);
    }

    @Override
    public void nextState() {
        PlaceCardState.execute();
    }
}
