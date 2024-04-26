package it.polimi.ingsw.parse;
import it.polimi.ingsw.model.card.*;
import com.google.gson.*;
import java.lang.reflect.*;

/**
 * This class define a type adapter for the abstract object Card, in this case it implements two
 * method that serializes and deserializes the Card object in its concrete type
 * @param <T> is a sub type of Card
 */
public class CardTypeAdapter <T extends Card> implements JsonSerializer<T>, JsonDeserializer<T>{
    /**
     * This method serializes the Card into its concrete type. It's a standard implementation and
     * it works autonomously with other high-level Gson methods.
     * @param src it's the concrete run-time object
     * @param type it's a parameter that defines something useless for us
     * @param context it's a parameter useful for serializing the concrete object
     * @return it returns a JsonElement, this will be used only by Gson functions
     */
    @Override
    public JsonElement serialize(T src, Type type, JsonSerializationContext context){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", src.getClass().getName());
        jsonObject.add("data", context.serialize(src));
        return jsonObject;
    }

    /**
     * This method serializes the Card into its concrete type. It's a standard implementation and
     * it works autonomously with other high-level Gson methods.
     * @param element it's the JsonElement read from a json file
     * @param type we do not use this parameter
     * @param context it helps to deserialize the json object into the correct concrete object
     * @return it returns the concrete object
     */
    @Override
    public T deserialize(JsonElement element, Type type, JsonDeserializationContext context){
        try{
            JsonObject object = element.getAsJsonObject();
            String name = object.get("type").getAsString();
            Class<?> card = Class.forName(name);
            return context.deserialize(object.get("data"), card);

        }catch(ClassNotFoundException e){
            return null;
        }
    }
}
