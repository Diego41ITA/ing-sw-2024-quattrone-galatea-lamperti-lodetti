package it.polimi.ingsw.parse;

import com.google.gson.*;

import java.awt.*;
import java.lang.reflect.Type;

/**
 * this class explains how gson should serialize/deserialize a Point object
 * @author Lodetti Alessandro
 */
public class PointTypeAdapter implements JsonSerializer<Point>, JsonDeserializer<Point> {

    /**
     * this is the custom deserialization method used by gson
     * @param jsonElement the jsonElement that needs to be deserialized
     * @param type
     * @param jsonDeserializationContext
     * @return the deserialized object
     * @throws JsonParseException
     */
    @Override
    public Point deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        int x = jsonObject.get("x").getAsInt();
        int y = jsonObject.get("y").getAsInt();
        return new Point(x, y);
    }

    /**
     * this is the custom serialization method used by gson
     * @param src it is the object that needs to be serialized
     * @param type
     * @param jsonSerializationContext
     * @return the serialized object
     */
    @Override
    public JsonElement serialize(Point src, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("x", src.x);
        jsonObject.addProperty("y", src.y);
        return jsonObject;
    }
}
