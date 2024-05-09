package it.polimi.ingsw.view.statusActive;

import it.polimi.ingsw.model.GameView;
import it.polimi.ingsw.networking.ClientAction;
import it.polimi.ingsw.view.GameFlow;
import it.polimi.ingsw.view.UI;

public class DrawCardState extends StateActive{

    private GameFlow flow;
    private GameView view = flow.getView();
    private UI ui = flow.getUi();
    private ClientAction client = flow.getClient();
    private String nickName = flow.getNickname();

    public DrawCardState(GameFlow flow){
        super(flow);
    }

    @Override
    public void execute(){
        //here it does its logic
    }

    @Override
    public void nextState(){

    }

    //other method that helps to implement the logic
}
