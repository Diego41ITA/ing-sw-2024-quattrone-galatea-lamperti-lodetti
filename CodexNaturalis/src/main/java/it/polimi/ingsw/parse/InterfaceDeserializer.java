package it.polimi.ingsw.parse;

import com.google.gson.*;
import it.polimi.ingsw.model.card.strategyPattern.*;

import java.lang.reflect.Type;

class InterfaceDeserializer implements JsonDeserializer<CheckInterface>{
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