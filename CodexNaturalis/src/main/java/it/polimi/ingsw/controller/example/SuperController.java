package it.polimi.ingsw.controller.example;
import it.polimi.ingsw.model.gameDataManager.*;
import java.util.*;

import java.io.Serializable;

public class SuperController implements Serializable{
    List<GameController> currentGames;
    Matches games;

    public SuperController(){
        currentGames = new ArrayList<>();
        games = new Matches();
    }

    //functions to create game, to join a random game and to join a specific game.

    //functions to delete game and save to reconnect a player.
}
