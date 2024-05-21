package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.gameDataManager.Game;
import it.polimi.ingsw.model.gameDataManager.Matches;
import it.polimi.ingsw.parse.SaverWriter;

public class RoutineDelete extends Thread{

    MainController matches;

    public RoutineDelete(MainController matches){
        this.matches = matches;
    }

    @Override
    public void run(){
        while(!Thread.interrupted()){
            //codice per la cancellazione di un game dove i giocatori sono inattivi

            try{
                //it waits for 30 seconds
                Thread.sleep(30000);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
