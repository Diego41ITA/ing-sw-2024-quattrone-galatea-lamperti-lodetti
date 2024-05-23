package it.polimi.ingsw.controller;

public class RoutineDelete extends Thread{

    ControllerOfMatches matches;

    public RoutineDelete(ControllerOfMatches matches){
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
