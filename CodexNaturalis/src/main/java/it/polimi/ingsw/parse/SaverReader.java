package it.polimi.ingsw.parse;
import com.google.gson.*;
import it.polimi.ingsw.model.card.strategyPattern.CheckInterface;
import it.polimi.ingsw.model.gameDataManager.*;
import it.polimi.ingsw.model.card.*;

import java.io.*;

/**
 * this class defines two static methods that read a specific object from a json file.
 * @author Lodetti Alessandro
 */
public class SaverReader {
    /**
     * this method read a game object from the file that should exist at the address
     * defined by the parameter filePath
     * @param filePath defines where the file is located inside this project
     * @return it returns the read Game object
     */
    public static Game gameReader(String filePath){
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Card.class, new CardTypeAdapter<>())
                .registerTypeAdapter(CheckInterface.class, new InterfaceSerializer())
                .registerTypeAdapter(CheckInterface.class, new InterfaceDeserializer())
                .create();
        try(FileReader reader = new FileReader(filePath)) {
            return gson.fromJson(reader, Game.class);
        }catch(IOException e){
            return null;
        }
    }

    /**
     * this method read a player object from the file that should exist at the address
     * defined by the parameter filePath
     * @param filePath defines where the file is located inside this project
     * @return it returns the read Player object
     */
    public static Player playerReader(String filePath){
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Card.class, new CardTypeAdapter<>())
                .create();
        try(FileReader reader = new FileReader(filePath)) {
            return gson.fromJson(reader, Player.class);
        }catch(IOException e){
            return null;
        }
    }
}
