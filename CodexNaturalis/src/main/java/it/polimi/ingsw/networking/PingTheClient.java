package it.polimi.ingsw.networking;

import it.polimi.ingsw.controller.ControllerOfGame;
import it.polimi.ingsw.controller.ControllerOfMatches;
import it.polimi.ingsw.model.gameDataManager.Game;
import it.polimi.ingsw.model.gameDataManager.Player;
import it.polimi.ingsw.observer.HandleObserver;
import it.polimi.ingsw.parse.SaverReader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.util.Map;

/**
 * this class pings the client constantly and to do so it extends thread class.
 */
public class PingTheClient extends Thread{

    ControllerOfGame controller;

    /**
     * it only needs the controller that controls the game where the client is playing
     * @param controller the controller of the game
     */
    public PingTheClient(ControllerOfGame controller){
        this.controller = controller;
    }

    /**
     * it overrides run of thread in order not to slow down the controller class. It manages the consequences of the
     * possible disconnection: who is the new current player? what happen if the disconnected player was the current
     * player?
     * So the user should just provide all the methods to insert/remove an observer from the controller and to goOn.
     */
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
                Thread.sleep(1000);

            }catch(IOException | InterruptedException e){
                System.out.println("impossible to reach the client" + nick);
                //here we need to manage the disconnection logic
                Player player = controller.returnGame().getPlayerByNick(nick);
                controller.removeObserver(player);
                Map<String, Boolean> activity = controller.returnGame().getActivity();
                activity.put(nick, false);
                controller.returnGame().setActivity(activity);

                //the disconnected player could be also the CURRENT PLAYER.
                if(controller.checkTurn && controller.returnGame().getTurn().getCurrentPlayerNick().equals(nick)){
                    //it reloads the last saving of this game
                    reloadGame(controller.getGameId());
                    //the show must go on
                    controller.goOn();
                }else if(!controller.checkTurn){
                    ControllerOfMatches matches = null;
                    try {
                        matches = ControllerOfMatches.getMainController();
                        matches.removeGame(this.controller.getGameId());
                    } catch (RemoteException ex) {
                        System.err.println("this point should have not been reached");
                    } catch(NullPointerException ex){
                        //it gently manages the case that there is no match with the same id.
                    }

                }
            }
        }
    }

    /**
     * if the current player leaves the match it reloads the last saving: in this way it deletes the actions that the ex
     * current player has done and the game is going to be surely in a consistent status.
     * @param gameId the game that needs to be fixed
     */
    private void reloadGame(String gameId){
        //codice simile a quello di main controller.
        String gamePath = "CodexNaturalis/SavedGames/" + gameId + ".json";
        File f = new File(Paths.get("").toAbsolutePath().toString(), gamePath);
        Game game = SaverReader.gameReader(f.getPath());
        //at this point we need to set the game in the controller
        controller.setGame(game);
    }
}
