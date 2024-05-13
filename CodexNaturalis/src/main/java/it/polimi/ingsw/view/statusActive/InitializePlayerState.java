package it.polimi.ingsw.view.statusActive;

import it.polimi.ingsw.model.GameView;
import it.polimi.ingsw.networking.ClientAction;
import it.polimi.ingsw.view.GameFlow;
import it.polimi.ingsw.view.UI;

public class InitializePlayerState extends StateActive{

    private GameView view = flow.getView();
    private UI ui = flow.getUi();
    private ClientAction client = flow.getClient();
    private String nickName = flow.getNickname();

    protected InitializePlayerState(GameFlow flow) {
        super(flow);
        view = flow.getView();
        ui = flow.getUi();
        client = flow.getClient();
        nickName = flow.getNickname();
    }

    @Override
    public void execute() {

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
