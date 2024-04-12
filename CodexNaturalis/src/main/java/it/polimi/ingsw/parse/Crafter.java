package it.polimi.ingsw.parse;
import com.google.gson.Gson;
import it.polimi.ingsw.model.card.*;
import it.polimi.ingsw.model.card.strategyPattern.*;

import java.io.*;
import java.util.*;

/**
 * @author Lodetti Alessandro
 * this class crafts the json file that contains all the card
 */
public class Crafter {
    public static void main(String[] arg)
    {

        //oppure si crea una directory nel robo di intellij e si crea il file direttamente da li
        File resourceDeck = new File(/* path */);
        File startingDeck = new File(/* path */);


        //ora il file è creato bisogna creare tutto quello che serve a riempirlo

        //carte oro
        HashMap<>g;
        List<> l;

        GoldCard gc = new GoldCard();

        //carte risorsa

        //carte start

        //carte obiettivo
        Object[][] matrix = {
                {TypeOfCard.MUSHROOM, false, 2, new PositionCheck1(), new HashMap<Item, Integer>()},
                {TypeOfCard.VEGETABLE, false, 2, new PositionCheck2(), null},
                //fino a qui ok
                {TypeOfCard.MUSHROOM, false, 2, positionCheck3, null},
                {TypeOfCard.MUSHROOM, false, 2, positionCheck4, null},
                {TypeOfCard.MUSHROOM, false, 2, positionCheck5, null},
                {TypeOfCard.MUSHROOM, false, 2, positionCheck6, null},
                {TypeOfCard.MUSHROOM, false, 2, positionCheck7, null},
                {TypeOfCard.MUSHROOM, false, 2, positionCheck8, null},
                {TypeOfCard.MUSHROOM, false, 2,null, null},
                {TypeOfCard.MUSHROOM, false, 2, positionCheck, null},
                {TypeOfCard.MUSHROOM, false, 2, positionCheck1, null},
                {TypeOfCard.MUSHROOM, false, 2, positionCheck1, null},
                {TypeOfCard.MUSHROOM, false, 2, positionCheck1, null},
                {TypeOfCard.MUSHROOM, false, 2, positionCheck1, null},
                {TypeOfCard.MUSHROOM, false, 2, positionCheck1, null},
                {TypeOfCard.MUSHROOM, false, 2, positionCheck1, null},
        };

        Deck goalDeck = new Deck();
        for(int i = 0; i <= 15; i++)
        {
            GoalCard c = new GoalCard((TypeOfCard) matrix[i][0], (boolean) matrix[i][1], (int) matrix[i][2],
                    (CheckInterface) matrix[i][3], (HashMap<Item, Integer>) matrix[i][4]);
            goalDeck.addCard(c);
        }

        //ora c'è il file e ci sono gli oggetti da metterci dentro, si utilizzando i comandi di Gson

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

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

    }

}
