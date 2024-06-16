package it.polimi.ingsw.parse;

import com.google.gson.*;

import java.awt.*;
import java.lang.reflect.Type;

public class PointTypeAdapter implements JsonSerializer<Point>, JsonDeserializer<Point> {

    @Override
    public Point deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        int x = jsonObject.get("x").getAsInt();
        int y = jsonObject.get("y").getAsInt();
        return new Point(x, y);
    }

    @Override
    public JsonElement serialize(Point src, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("x", src.x);
        jsonObject.addProperty("y", src.y);
        return jsonObject;
    }
}
