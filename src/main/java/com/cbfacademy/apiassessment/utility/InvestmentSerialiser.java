package com.cbfacademy.apiassessment.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.cbfacademy.apiassessment.model.Investment;
import com.google.gson.*;

/**
 * Utility class for serializing Investment objects to JSON.
 */
@Component
public class InvestmentSerialiser implements JsonSerializer<Investment> {
    private static final Logger logger = LoggerFactory.getLogger(InvestmentSerialiser.class);

    /**
     * Serializes an Investment object to JSON.
     *
     * @param investment The Investment object to serialize.
     * @param typeOfSrc  The type of the source object.
     * @param context    The serialization context.
     * @return The serialized JSON element representing the Investment.
     * @throws JsonParseException If an error occurs during JSON serialization.
     */
    @Override
    public JsonElement serialize(Investment investment, java.lang.reflect.Type typeOfSrc,
            JsonSerializationContext context) throws JsonParseException {
        try {

            // Create a JSON object to represent the Investment
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("type", investment.getType());
            jsonObject.addProperty("id", investment.getId().toString());
            jsonObject.addProperty("symbol", investment.getSymbol());
            jsonObject.addProperty("name", investment.getName());
            jsonObject.addProperty("esgRating", investment.getESGRating().toString());
            jsonObject.addProperty("sharesQuantity", investment.getSharesQuantity());
            jsonObject.addProperty("purchasePrice", investment.getPurchasePrice());
            jsonObject.addProperty("totalValue", investment.getTotalValue());
            jsonObject.addProperty("currentValue", investment.getCurrentValue());

            return jsonObject;
        } catch (Exception e) {
            // Log the exception and throw it again
            logger.error("Error occurred during serialization: {}", e.getMessage(), e);
            throw new JsonParseException("Error deserializing Investment object", e);
        }
    }
}
