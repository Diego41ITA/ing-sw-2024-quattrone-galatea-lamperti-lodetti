package it.polimi.ingsw.parse;

import com.google.gson.*;
import it.polimi.ingsw.model.card.strategyPattern.*;

import java.lang.reflect.Type;

/**
 * This class implements serialize method, useful to register a correct type adapter for
 * CheckInterface.
 * @author Lodetti Alessandro
 */
class InterfaceDeserializer implements JsonDeserializer<CheckInterface>{
    /**
     * this method defines how to deserialize the CheckInterface in a correct way: it replaces the
     * string with a concrete implementation, to do that it use a simple switch statement.
     * @param json it's the JsonElement read from the Json file.
     * @param typeOfT we do not use this parameter
     * @param context we do not use this parameter.
     * @return it returns a concrete implementation of CheckInterface.
     */
    @Override
    public CheckInterface deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context){
        JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.get("type").getAsString();

        //now we need to implement the logic for a correct implementation.
        return switch (type) {
            case "DiagonalAnimalCheck" -> new DiagonalAnimalCheck();
            case "DiagonalInsectCheck" -> new DiagonalInsectCheck();
            case "DiagonalMushroomCheck" -> new DiagonalMushroomCheck();
            case "DiagonalVegetableCheck" -> new DiagonalVegetableCheck();
            case "ItemCheck" -> new ItemCheck();
            case "LMushroomVegetableCheck" -> new LMushroomVegetableCheck();
            case "ReverseLVegetableInsectCheck" -> new ReverseLVegetableInsectCheck();
            case "UpsideDownLAnimalMushroomCheck" -> new UpsideDownLAnimalMushroomCheck();
            case "UpsideDownReverseLInsectAnimalCheck" -> new UpsideDownReverseLInsectAnimalCheck();
            default -> null;
        };
    }
}