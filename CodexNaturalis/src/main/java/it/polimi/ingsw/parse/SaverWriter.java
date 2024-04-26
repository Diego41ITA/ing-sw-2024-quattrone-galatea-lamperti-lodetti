package it.polimi.ingsw.parse;
import it.polimi.ingsw.model.gameDataManager.*;
import it.polimi.ingsw.model.card.Card;
import com.google.gson.*;
import java.io.FileWriter;
import java.io.IOException;

public class SaverWriter {
    public static boolean saveGame(Game game){
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Card.class, new CardTypeAdapter<>())
                .create();
        try(FileWriter writer = new FileWriter("resources/directory/file")){
            String serializedObject = gson.toJson(game);
            writer.write(serializedObject);
            return true;
        }catch(IOException e){
            return false;
        }
    }

    public static boolean savePlayer(Player player){
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Card.class, new CardTypeAdapter<>())
                .create();
        try(FileWriter writer = new FileWriter("resources/directory/file")){
            String serializedObject = gson.toJson(player);
            writer.write(serializedObject);
            return true;
        }catch(IOException e){
            return false;
        }
    }
}
