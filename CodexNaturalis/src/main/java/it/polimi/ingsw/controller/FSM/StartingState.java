package it.polimi.ingsw.controller.FSM;

import it.polimi.ingsw.controller.MainController;

public class StartingState extends State {
    public StartingState(MainController mainController){
        super(mainController);
    }
    public StartingState() {
        super();
    }
    @Override
    public String handleInput() {
        return null;
    }
    @Override
    public void start(){

    }
    @Override
    public void goNextState() {
        gameController.setCurrentState(new PlayerConnected(gameController));
    }

    @Override
    public void goToSpecifiedState(State state) {

    }
}
