package it.polimi.ingsw.parse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import it.polimi.ingsw.model.card.*;

public class DeckReader<T extends Card> {
    private final Class<T> cardType;

    public DeckReader(Class<T> cardType) {
        this.cardType = cardType;
    }

    /**
     * Reads a JSON file containing cards and initializes a Deck with them.
     *
     * @param filePath The path to the JSON file.
     * @return The Deck initialized with the cards read from the JSON file.
     */
    public Deck<T> readDeckFromJSON(String filePath) {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(filePath)) {
            Type listType = TypeToken.getParameterized(List.class, cardType).getType();
            List<T> cards = gson.fromJson(reader, listType);

            // Create a new deck with the list of cards loaded from JSON
            return new Deck<>(cards);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
