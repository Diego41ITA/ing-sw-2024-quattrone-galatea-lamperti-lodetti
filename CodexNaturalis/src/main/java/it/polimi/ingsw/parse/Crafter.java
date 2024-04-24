package it.polimi.ingsw.parse;
import com.google.gson.Gson;
import it.polimi.ingsw.model.card.*;
import it.polimi.ingsw.model.card.strategyPattern.*;
//le librerie si possono togliere
import java.io.*;
import java.util.*;
/**
 * this class contains static methods useful to save the JSON file
 * needed for the FA "resilienza alle disconnessioni" and "persistenza alle disconnessioni"
 */
public class Crafter {
    public static void createGameFile(String id)
    {
        //create a path to the directory
        String directoryPath = "src/main/resources/JsonMatches/Game" + id;
        File directory = new File(directoryPath);

        if(!directory.exists()){
            //the directory should be created
            directory.mkdirs();
        }

        //now the directory surly exists
        String gameFilePath = "src/main/resources/JsonMatches/Game" + id + "/Game" + id + ".json";
        File gameFile = new File(gameFilePath);

        if(!gameFile.exists()){
            //the file should be created
            try{
                gameFile.createNewFile();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    public static String getGameFilePath(String id){
        return "src/main/resources/JsonMatches/Game" + id + "/Game" + id + ".json";
    }

    public static void createPlayerFile(String nick, String id){
        String playerFilePath = "src/main/resources/JsonMatches/Game" + id  + "/" + nick + ".json";
        File playerFile = new File(playerFilePath);

        if(!playerFile.exists()){
            //the player file should be created
            try{
                playerFile.createNewFile();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    public static String getPlayerFilePath(String nick, String id){
        return "src/main/resources/JsonMatches/Game" + id  + "/" + nick + ".json";
    }

}
