package com.cbfacademy.apiassessment.utility;

import org.springframework.stereotype.Component;

import com.cbfacademy.apiassessment.model.Investment;
import com.google.gson.*;

@Component
public class InvestmentSerialiser implements JsonSerializer<Investment> {

    @Override
    public JsonElement serialize(Investment investment, java.lang.reflect.Type typeOfSrc,
            JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        // jsonObject.addProperty("type", investment.getType());
        jsonObject.addProperty("id", investment.getId().toString());
        jsonObject.addProperty("symbol", investment.getSymbol());
        jsonObject.addProperty("name", investment.getName());
        jsonObject.addProperty("sharesQuantity", investment.getSharesQuantity());
        jsonObject.addProperty("purchasePrice", investment.getPurchasePrice());
        jsonObject.addProperty("currentValue", investment.getCurrentValue());
        return jsonObject;
    }
}
