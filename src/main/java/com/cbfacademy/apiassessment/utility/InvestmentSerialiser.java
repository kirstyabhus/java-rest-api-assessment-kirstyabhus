package com.cbfacademy.apiassessment.utility;

import com.cbfacademy.apiassessment.model.Investment;
import com.google.gson.*;

public class InvestmentSerialiser implements JsonSerializer<Investment> {

    @Override
    public JsonElement serialize(Investment investment, java.lang.reflect.Type typeOfSrc,
            JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", investment.getType());
        jsonObject.addProperty("id", investment.getId().toString());
        jsonObject.addProperty("symbol", investment.getSymbol());
        jsonObject.addProperty("name", investment.getName());
        jsonObject.addProperty("value", investment.getValue());
        // Add any additional properties specific to each type of investment

        return jsonObject;
    }
}
