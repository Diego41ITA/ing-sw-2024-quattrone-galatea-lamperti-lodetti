package it.polimi.ingsw.parse;

import it.polimi.ingsw.model.card.strategyPattern.CheckInterface;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

public class InterfaceSerializer implements JsonSerializer<CheckInterface> {
    @Override
    public JsonElement serialize(CheckInterface src, Type typeOfSrc, JsonSerializationContext context){
        JsonObject json = new JsonObject();
        json.addProperty("type", src.toString());
        return json;
    }
}
