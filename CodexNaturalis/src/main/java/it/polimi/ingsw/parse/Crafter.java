package it.polimi.ingsw.parse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.typeadapter.RuntimeTypeAdapterFactory;
import it.polimi.ingsw.model.card.GoldCard;
import  //libreria file

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

        HashMap> g;
        List >
        GoldCard gc = new GoldCard();

        //ora c'è il file e ci sono gli oggetti da metterci dentro, si utilizzando i comandi di Gson

    }

}
