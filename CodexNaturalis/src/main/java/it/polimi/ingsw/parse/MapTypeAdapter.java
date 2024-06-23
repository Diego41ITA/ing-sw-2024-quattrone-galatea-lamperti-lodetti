package it.polimi.ingsw.parse;

import com.google.gson.*;
import it.polimi.ingsw.model.card.Card;
import it.polimi.ingsw.model.card.PlayableCard;
import it.polimi.ingsw.model.gameDataManager.Player;

import java.awt.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * this class allows custom serialization and deserialization of Map type; especially for {@link Map<Point, PlayableCard>}.
 * It uses other two adapter: the point, and the playableCard ones.
 * @author Lodetti Alessandro
 */
public class MapTypeAdapter implements JsonSerializer<Map<Point, PlayableCard>>, JsonDeserializer<Map<Point, PlayableCard>> {

    /**
     * it needs this to make a custom serialization of the key/value of the map
     */
    private Gson gson;

    /**
     * this is the custom construct that allows to initialize the Gson object
     * @param gson
     */
    public MapTypeAdapter(Gson gson){
        this.gson = gson;
    }

    /**
     * deserializes a json element into the corresponding Map<>
     * @param json the json element that needs to be deserialized
     * @param type
     * @param jsonDeserializationContext
     * @return the deserialized map
     * @throws JsonParseException
     */
    @Override
    public Map<Point, PlayableCard> deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        Map<Point, PlayableCard> playerMap = new HashMap<>();
        for(Map.Entry<String, JsonElement> entry : jsonObject.entrySet()){

            JsonElement keyElement = JsonParser.parseString(entry.getKey());
            Point point = gson.fromJson(keyElement, Point.class);
            PlayableCard value = (PlayableCard) gson.fromJson(entry.getValue(), Card.class);
            playerMap.put(point, value);
        }
        return playerMap;
    }

    /**
     * This method serializes a Map<\Point, PlayableCard> by using the this.gson to use custom serialization for the elements
     * of the map
     * @param playerBooleanMap the map that needs to be serialized
     * @param type
     * @param jsonSerializationContext
     * @return the serialized map
     */
    @Override
    public JsonElement serialize(Map<Point, PlayableCard> playerBooleanMap, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        for(Map.Entry<Point, PlayableCard> entry : playerBooleanMap.entrySet()){
            JsonElement keyElement = gson.toJsonTree(entry.getKey(), Point.class);
            JsonElement valueElement = gson.toJsonTree(entry.getValue(), Card.class);

            jsonObject.add(keyElement.toString(), valueElement);
        }

        return jsonObject;
    }
}
