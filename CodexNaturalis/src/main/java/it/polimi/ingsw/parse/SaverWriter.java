package it.polimi.ingsw.parse;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.card.PlayableCard;
import it.polimi.ingsw.model.card.strategyPattern.CheckInterface;
import it.polimi.ingsw.model.gameDataManager.*;
import it.polimi.ingsw.model.card.Card;
import it.polimi.ingsw.parse.*;
import com.google.gson.*;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Optional;

/**
 * this class defines two static methods that write on the correct file the status of the player
 * and game objects.
 * @author Lodetti Alessandro
 */
public class SaverWriter {
    /**
     * this method save the object game on the correct file. It has the precondition that the directory
     * (where the game is going to be saved) should already exist (as for the file)
     * @param game the object you want to save
     * @return it returns true if and only if the object is correctly saved.
     */
    public static void saveGame(Game game){

        GsonBuilder gsonBuilder = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Card.class, new CardTypeAdapter<>())
                .registerTypeAdapter(CheckInterface.class, new InterfaceSerializer())
                .registerTypeAdapter(CheckInterface.class, new InterfaceDeserializer())
                .registerTypeAdapter(Point.class, new PointTypeAdapter());
        gsonBuilder.registerTypeAdapter(new TypeToken<Map<Point, PlayableCard>>(){}.getType(), new MapTypeAdapter(gsonBuilder.create()));

        Gson gson = gsonBuilder.create();

        String pathToFile = Crafter.getGameFilePath(game.getId());

        try(FileWriter writer = new FileWriter(pathToFile)){
            String serializedObject = gson.toJson(game);
            writer.write(serializedObject);
        }catch(IOException e){
            System.out.println("something went wrong during salvation process");
        }
    }

    /**
     * this method save the object player on the correct file. It has the precondition that the directory
     * (where the player is going to be saved) should already exists (as for the file)
     * @param player it's the object that you want to be saved
     * @return it returns true if and only if the object it's correctly saved
     */
    public static boolean savePlayer(Game game, Player player){
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Card.class, new CardTypeAdapter<>())
                .create();
        try(FileWriter writer = new FileWriter(Crafter.getPlayerFilePath(player.getNick(), game.getId()))){
            String serializedObject = gson.toJson(player);
            writer.write(serializedObject);
            return true;
        }catch(IOException e){
            return false;
        }
    }
}
