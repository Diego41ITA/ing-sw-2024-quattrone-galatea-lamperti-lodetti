package it.polimi.ingsw.parse;
import com.google.gson.Gson;
import it.polimi.ingsw.model.card.*;
import it.polimi.ingsw.model.card.strategyPattern.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * @author Lodetti Alessandro
 * this class crafts the json file that contains all the card
 */
public class Crafter {
    public static void main(String[] arg)
    {

        //oppure si crea una directory nel robo di intellij e si crea il file direttamente da li
        File goldDeck = new File(/* path */);
        File resourceDeck = new File(/* path */);
        File startingDeck = new File(/* path */);
        File goalDeck = new File(/* path */);


        //ora il file è creato bisogna creare tutto quello che serve a riempirlo

        //carte oro
        HashMap<>g;
        List<> l;

        GoldCard gc = new GoldCard();

        //carte risorsa

        //carte start

        //carte obiettivo
        Object[][] matrix = {
                {TypeOfCard.MUSHROOM, false, 2, positionCheck1, null},
                {TypeOfCard.VEGETABLE, false, 2, positionCheck2, null},
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

        //ora c'è il file e ci sono gli oggetti da metterci dentro, si utilizzando i comandi di Gson

    }

}
