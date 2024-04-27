package it.polimi.ingsw.controller;

public class StartingState extends State{
    public StartingState(FSM fsm){}
    //le classi implementano dei metodi che possono essere scatenati da determinati eventi
    @Override
    public void HandleInput(String input){}
    @Override
    public String start(){
        return "start!!!";
    }
}
