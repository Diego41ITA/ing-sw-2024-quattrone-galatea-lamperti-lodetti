package it.polimi.ingsw.controller;

import java.util.Iterator;

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

    //******LEAVE THIS METHOD IN THIS CLASS**************************
    //you can use this method in this class(Routine delete) without invoke matches
    public synchronized void leaveGame(String gameID, String nick){
        Iterator<ControllerOfGame> iterator = matches.getActiveGames().iterator();
        if(matches.getActiveGames().isEmpty())
            return;
        while (iterator.hasNext()) {
            ControllerOfGame game = iterator.next();
            if (game.getGameId().equals(gameID)) {
                game.leave(nick);
                if (game.getNumOfOnlinePlayers() == 0) {
                    iterator.remove();
                    System.out.println("\t>Game " + gameID + " removed from activeGames");
                }else {
                    System.out.println("the player " + nick + " of " + gameID + " is out of the game");
                }

            }
        }
    }

}
