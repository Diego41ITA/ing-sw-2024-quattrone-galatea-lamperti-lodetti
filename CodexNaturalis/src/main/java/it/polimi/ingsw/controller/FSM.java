package it.polimi.ingsw.controller;

//qui ci sta tutta la roba del controller: quindi gli stati del gioco processa le richieste del client
// e aggiorna il model

public class FSM {
    private State state;

    public FSM(){
        state = new StartingState(this);
    }

    public void setState(State state){
        this.state = state;
    }

    //now we should add a description of the functionality this class has.
}
