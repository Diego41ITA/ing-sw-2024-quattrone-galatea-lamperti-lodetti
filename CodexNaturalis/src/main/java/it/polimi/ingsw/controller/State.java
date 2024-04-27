package it.polimi.ingsw.controller;

abstract public class State {

    public abstract void HandleInput(String input);
    //now we should define all the methods.
    public abstract String start();
}
