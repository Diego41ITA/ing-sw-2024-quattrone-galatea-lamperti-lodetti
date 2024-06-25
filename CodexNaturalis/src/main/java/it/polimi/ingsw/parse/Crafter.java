package it.polimi.ingsw.parse;
import com.google.gson.Gson;
import it.polimi.ingsw.main.Main;
import it.polimi.ingsw.model.card.*;
import it.polimi.ingsw.model.card.strategyPattern.*;
//le librerie si possono togliere
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
/**
 * this class contains static methods useful to save the JSON file
 * needed for the FA "resilienza alle disconnessioni" and "persistenza alle disconnessioni"
 * @author Diego Quattrone
 */
public class Crafter {
    /**
     * this method gets the path to a specific file.
     * @param id it's the ID of the game
     * @return it returns a string which is the path to file where the game is saved
     */
    public static String getGameFilePath(String id){

        try {
            return id + ".json";
        }catch(NullPointerException e){
            e.printStackTrace();
        }
        return null;
    }
}
