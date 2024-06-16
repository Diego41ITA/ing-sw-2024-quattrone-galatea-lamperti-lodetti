package it.polimi.ingsw.parse;

import com.google.gson.*;
import it.polimi.ingsw.model.card.Card;
import it.polimi.ingsw.model.card.PlayableCard;
import it.polimi.ingsw.model.gameDataManager.Player;

import java.awt.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class MapTypeAdapter implements JsonSerializer<Map<Point, PlayableCard>>, JsonDeserializer<Map<Point, PlayableCard>> {

    private Gson gson;

    public MapTypeAdapter(Gson gson){
        this.gson = gson;
    }

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
