package it.polimi.ingsw.view.statusActive;

import it.polimi.ingsw.view.GameFlow;

public class PlaceCardState extends StateActive{
    protected PlaceCardState(GameFlow flow) {
        super(flow);
    }

    @Override
    public void execute() {

    }

    @Override
    public void nextState() {
        new DrawCardState(flow).execute();
    }
}
