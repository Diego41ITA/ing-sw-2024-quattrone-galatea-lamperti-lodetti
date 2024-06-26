package it.polimi.ingsw.parse;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.card.PlayableCard;
import it.polimi.ingsw.model.card.strategyPattern.CheckInterface;
import it.polimi.ingsw.model.gameDataManager.*;
import it.polimi.ingsw.model.card.Card;
import it.polimi.ingsw.parse.*;
import com.google.gson.*;

import java.awt.*;
import java.io.File;
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

        String userHome = System.getProperty("user.home");
        String saveDirPath = userHome + File.separator + "SavedGames";
        File saveDir = new File(saveDirPath);
        if(!saveDir.exists()){
            boolean created = saveDir.mkdir();
            if(!created)
                System.err.println("the directory was not created");
        }

        try(FileWriter writer = new FileWriter(saveDirPath + File.separator + pathToFile)){
            String serializedObject = gson.toJson(game);
            writer.write(serializedObject);
            System.out.println("the current game (" + game.getId() + ") is saved in: " + saveDirPath + "/" + pathToFile);
        }catch(IOException e){
            System.out.println("something went wrong during salvation process");
            e.printStackTrace();
            e.getMessage();
        }
    }
}
