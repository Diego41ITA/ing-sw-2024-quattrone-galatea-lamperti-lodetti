package it.polimi.ingsw.controller;

public class StartingState extends State{
    public StartingState(FSM fsm){
        super(fsm);
    }
    //le classi implementano dei metodi che possono essere scatenati da determinati eventi
    @Override
    public String start(){
        return "start!!!";
    }
}
