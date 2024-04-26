package it.polimi.ingsw.parse;
import it.polimi.ingsw.model.card.*;
import com.google.gson.*;
import java.lang.reflect.*;

public class CardTypeAdapter <T extends Card> implements JsonSerializer<T>, JsonDeserializer<T>{
    @Override
    public JsonElement serialize(T src, Type type, JsonSerializationContext context){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", src.getClass().getName());
        return jsonObject;
    }

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
