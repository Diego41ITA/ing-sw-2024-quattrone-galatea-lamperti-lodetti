package it.polimi.ingsw.parse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;
import it.polimi.ingsw.model.card.*;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.model.card.strategyPattern.CheckInterface;

public class DeckReader<T extends Card> {
    private final Class<T> cardType;

    public DeckReader(Class<T> cardType) {
        this.cardType = cardType;
    }

    /**
     * Reads a JSON file containing cards and initializes a Deck with them.
     *
     * @return The Deck initialized with the cards read from the JSON file.
     */
    public Deck<T> readDeckFromJSON(InputStream inputStream) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(CheckInterface.class, new InterfaceSerializer())
                .registerTypeAdapter(CheckInterface.class, new InterfaceDeserializer())
                .create();

        try{
            InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader buffer = new BufferedReader(streamReader);
            Type listType = TypeToken.getParameterized(List.class, cardType).getType();
            List<T> cards = gson.fromJson(buffer, listType);

            // Log the number of cards read from the JSON
            System.out.println("Number of cards " + cards.getFirst().getClass().getName() + "read: " + cards.size());

            // Create a new deck with the list of cards loaded from JSON
            return new Deck<>(cards);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
