package it.polimi.ingsw.parse;

import it.polimi.ingsw.model.card.strategyPattern.CheckInterface;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

/**
 * This class implements serialize method, useful to register a correct type adapter for
 * CheckInterface.
 * @author Lodetti Alessandro
 */
public class InterfaceSerializer implements JsonSerializer<CheckInterface> {

    /**
     * this method defines how to serialize the CheckInterface in a correct way: it replaces the
     * object with a sting that describes which concrete implementation should be created.
     * @param src it's the concrete implementation
     * @param typeOfSrc we do not use this parameter
     * @param context we do not use this parameter
     * @return it returns a JsonElement with a field "type" with the description of the interface
     */
    @Override
    public JsonElement serialize(CheckInterface src, Type typeOfSrc, JsonSerializationContext context){
        JsonObject json = new JsonObject();
        json.addProperty("type", src.toString());
        return json;
    }
}
