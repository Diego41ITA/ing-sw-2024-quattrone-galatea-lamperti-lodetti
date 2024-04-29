package it.polimi.ingsw.controller.FSM;

public class StartingState extends State {

    @Override
    public String handleInput(String input){
        //metodi per aggiunta player nel model (input è il nickname)
        return "nextState";
    }

    @Override
    public void start(){
    }
}
