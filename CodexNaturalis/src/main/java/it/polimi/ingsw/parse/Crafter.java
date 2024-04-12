package it.polimi.ingsw.parse;
import com.google.gson.Gson;
import it.polimi.ingsw.model.card.GoldCard;
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
        HashMap> g;
        List >
        GoldCard gc = new GoldCard();

        //carte risorsa

        //carte start

        //carte obiettivo

        //ora c'è il file e ci sono gli oggetti da metterci dentro, si utilizzando i comandi di Gson

    }

}
