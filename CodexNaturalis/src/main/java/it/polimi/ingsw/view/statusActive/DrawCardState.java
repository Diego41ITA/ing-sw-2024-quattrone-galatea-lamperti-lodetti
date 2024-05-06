package it.polimi.ingsw.view.statusActive;

import it.polimi.ingsw.view.GameFlow;

public class DrawCardState extends StateActive{
    public DrawCardState(GameFlow game){
        super(game);
    }

    @Override
    public void execute(){
        //here it does its logic
    }

    @Override
    public void nextState(){
        game.setActiveState(new PlaceCardState(StateActive.game));
    }

    //other method that helps to implement the logic
}
