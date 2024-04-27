package it.polimi.ingsw.controller;

import java.io.*;
import java.util.Scanner;

public class StartingState extends State{

    @Override
    public void HandleInput(String input){
        switch(input){
            case "join":

                break;
        }
    }
    @Override
    public String start(){
        return "start!!!";
    }
}
