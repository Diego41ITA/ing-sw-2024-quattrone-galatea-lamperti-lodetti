package it.polimi.ingsw.parse;
import com.google.gson.*;
import it.polimi.ingsw.model.gameDataManager.*;
import it.polimi.ingsw.model.card.*;

import java.io.*;

public class SaverReader {
    public static Game gameReader(String filePath){
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Card.class, new CardTypeAdapter<>())
                .create();
        try(FileReader reader = new FileReader(filePath)) {
            return gson.fromJson(reader, Game.class);
        }catch(IOException e){
            return null;
        }
    }

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
