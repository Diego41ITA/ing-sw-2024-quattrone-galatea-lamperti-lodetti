package it.polimi.ingsw.networking;

import it.polimi.ingsw.controller.ControllerOfGame;
import it.polimi.ingsw.model.gameDataManager.Game;
import it.polimi.ingsw.model.gameDataManager.Player;
import it.polimi.ingsw.model.gameDataManager.Status;
import it.polimi.ingsw.observer.HandleObserver;
import it.polimi.ingsw.parse.SaverReader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;

public class PingTheClient extends Thread{

    ControllerOfGame controller;

    public PingTheClient(ControllerOfGame controller){
        this.controller = controller;
    }

    @Override
    public void run(){
        while(!this.isInterrupted()){

            String nick = "";
            try{
                Map<String, HandleObserver> observerMap = controller.getObservers();
                if(observerMap != null){
                    for(String key : observerMap.keySet()){
                        nick = key;
                        observerMap.get(key).notify_PingClient(controller.returnGame());
                    }
                }
                Thread.sleep(3000);

            }catch(IOException | InterruptedException e){
                System.out.println("impossible to reach the client" + nick);
                //here we need to manage the disconnection logic
                Player player = controller.returnGame().getPlayerByNick(nick);
                controller.removeObserver(player);
                Map<String, Boolean> activity = controller.returnGame().getActivity();
                activity.put(nick, false);
                controller.returnGame().setActivity(activity);

                //the disconnected player could be also the CURRENT PLAYER.
                if(controller.checkTurn /*some controls on the current player*/){
                    //it reloads the last saving of this game
                    reloadGame(controller.getGameId());
                    //the show must go on
                    controller.goOn();
                }
            }
        }
    }

    private void reloadGame(String gameId){
        //codice simile a quello di main controller.
        String gamePath = "CodexNaturalis/SavedGames/" + gameId + ".json";
        File f = new File(Paths.get("").toAbsolutePath().toString(), gamePath);
        Game game = SaverReader.gameReader(f.getPath());
        //at this point we need to set the game in the controller
        controller.setGame(game);
    }
}
