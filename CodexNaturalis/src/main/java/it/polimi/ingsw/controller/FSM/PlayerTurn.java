package it.polimi.ingsw.controller.FSM;

public class PlayerTurn extends State {
    @Override
    public void HandleInput(String input) {
        // Logica per gestire l'input durante il turno del giocatore
        if (input.equals("disconnect")) {

            //logica per gestire il turno del giocatore
        } else{
//            FSM.getInstance().changeState(UpdateGame);
        }
    }

    @Override
    public String start() {
        return null;
    }
}
