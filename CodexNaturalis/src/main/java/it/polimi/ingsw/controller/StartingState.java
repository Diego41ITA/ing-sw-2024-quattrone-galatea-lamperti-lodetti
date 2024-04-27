package it.polimi.ingsw.controller;

import java.io.*;
import java.util.Scanner;

public class StartingState extends State{

    @Override
    public String HandleInput(String input){
        //metodi per aggiunta player nel model (input è il nickname)
        return "nextState";
    }

    @Override
    public String start(){
        return "Enter NickName: ";
    }
}
