package com.cbfacademy.apiassessment.utility;

import org.springframework.stereotype.Component;

import com.cbfacademy.apiassessment.model.ETF;
import com.cbfacademy.apiassessment.model.Investment;
import com.cbfacademy.apiassessment.model.Stock;
import com.google.gson.*;

@Component
public class InvestmentDeserialiser implements JsonDeserializer<Investment> {

    @Override
    public Investment deserialize(JsonElement json, java.lang.reflect.Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonPrimitive investmentType = jsonObject.getAsJsonPrimitive("type");

        if (investmentType == null) {
            throw new JsonParseException("Missing 'investmentType' property");
        }

        String type = investmentType.getAsString();

        // deseralise investment based on "type" field
        if ("Stock".equalsIgnoreCase(type)) {
            return context.deserialize(jsonObject, Stock.class);
        } else if ("ETF".equalsIgnoreCase(type)) {
            return context.deserialize(jsonObject, ETF.class);
        } else {
            throw new JsonParseException("Unknown investment type: " + type);
        }
    }
}
