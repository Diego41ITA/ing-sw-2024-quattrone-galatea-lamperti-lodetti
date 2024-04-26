package it.polimi.ingsw.controller;
//questa potrebbe essere pure un'interfaccia.
abstract public class State {
    protected FSM fsm;
    public State(FSM fsm){
        this.fsm = fsm;
    }

    //now we should define all the methods.
    public abstract String start();
}
