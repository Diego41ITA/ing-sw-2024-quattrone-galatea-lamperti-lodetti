package it.polimi.ingsw.parse;
import com.google.gson.Gson;
import it.polimi.ingsw.model.card.*;
import it.polimi.ingsw.model.card.strategyPattern.*;

import java.io.*;
import java.util.*;

/**
 * @author Lodetti Alessandro
 * this class crafts the json file that contains all the card
 *
 * non serve più
 */
public class Crafter {
    public static void main(String[] arg)
    {

        Gson gson = new Gson();
        ArrayList<GoldCard> goals = new ArrayList<>();
        try(FileWriter writer = new FileWriter("goalDeck.json")){
            gson.toJson(goals, writer);
            System.out.println("written successfully");
        }
        catch(IOException e){
            e.printStackTrace();
        }
        /*
        //per il deck goal
        String jsonGoal = gson.toJson(goalDeck);

        try(FileWriter write = new FileWriter("nome file")){
            write.write(json);
            System.out.println("scrittura ok");
        }
        catch(IOException e){
            e.printStackTrace();
        }

        //per il deck gold
        String jsonGold = gson.toJson(goalDeck);

        try(FileWriter write = new FileWriter("nome file")){
            write.write(json);
            System.out.println("scrittura ok");
        }
        catch(IOException e){
            e.printStackTrace();
        }


        //per il deck resources
        String jsonGoal = gson.toJson(goalDeck);

        try(FileWriter write = new FileWriter("nome file")){
            write.write(json);
            System.out.println("scrittura ok");
        }
        catch(IOException e){
            e.printStackTrace();
        }

        //per il deck start
        String jsonGoal = gson.toJson(goalDeck);

        try(FileWriter write = new FileWriter("nome file")){
            write.write(json);
            System.out.println("scrittura ok");
        }
        catch(IOException e){
            e.printStackTrace();
        }
        */
    }

}
