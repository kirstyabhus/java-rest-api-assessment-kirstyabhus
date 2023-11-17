package com.cbfacademy.apiassessment.utility;

import java.util.UUID;

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

        UUID id = UUID.fromString(jsonObject.get("id").getAsString());
        String type = jsonObject.get("type").getAsString();
        String symbol = jsonObject.get("symbol").getAsString();
        String name = jsonObject.get("name").getAsString();
        int sharesQuantity = jsonObject.get("sharesQuantity").getAsInt();
        double purchasePrice = jsonObject.get("purchasePrice").getAsDouble();
        double totalValue = jsonObject.get("totalValue").getAsDouble();
        double currentValue = jsonObject.get("currentValue").getAsDouble();

        if ("Stock".equalsIgnoreCase(type)) {
            return new Stock(id, type, symbol, name, sharesQuantity, purchasePrice, totalValue, currentValue);
            // return new Stock();
        } else if ("ETF".equalsIgnoreCase(type)) {
            // return new Stock();
            return new ETF(id, type, symbol, name, sharesQuantity, purchasePrice, totalValue, currentValue);
        } else {
            throw new JsonParseException("Unknown investment type: " + type);
        }
    }
}
