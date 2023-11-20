package com.cbfacademy.apiassessment.utility;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.cbfacademy.apiassessment.model.ESGRating;
import com.cbfacademy.apiassessment.model.ETF;
import com.cbfacademy.apiassessment.model.Investment;
import com.cbfacademy.apiassessment.model.Stock;
import com.cbfacademy.apiassessment.utility.ESGRatingDeserializer;
import com.google.gson.*;

/**
 * Utility class for deserializing Investment objects from JSON.
 */
@Component
public class InvestmentDeserialiser implements JsonDeserializer<Investment> {
    private static final Logger logger = LoggerFactory.getLogger(InvestmentDeserialiser.class);

    /**
     * Deserialize JSON to Investment object to its respective implementation.
     *
     * @param json    The JSON element to deserialize.
     * @param typeOfT The type of the object to deserialize to.
     * @param context The deserialization context.
     * @return An Investment object deserialized from JSON.
     * @throws JsonParseException If there's an error parsing the JSON, including
     *                            NumberFormatException
     *                            for issues related to invalid numeric values,
     *                            NullPointerException for missing fields,
     *                            or JSON parsing errors during deserialization.
     */
    @Override
    public Investment deserialize(JsonElement json, java.lang.reflect.Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        // Extract JSON object from the input
        JsonObject jsonObject = json.getAsJsonObject();

        try {

            // Extract values from JSON to create an Investment object
            UUID id = UUID.fromString(jsonObject.get("id").getAsString());
            String type = jsonObject.get("type").getAsString();
            String symbol = jsonObject.get("symbol").getAsString();
            String name = jsonObject.get("name").getAsString();
            ESGRating esgRating = context.deserialize(jsonObject.get("esgRating"), ESGRating.class);
            int sharesQuantity = jsonObject.get("sharesQuantity").getAsInt();
            double purchasePrice = jsonObject.get("purchasePrice").getAsDouble();
            double totalValue = jsonObject.get("totalValue").getAsDouble();
            double currentValue = jsonObject.get("currentValue").getAsDouble();

            // Determine the type of Investment and create the respective object
            if ("Stock".equalsIgnoreCase(type)) {
                return new Stock(id, type, symbol, name, esgRating, sharesQuantity, purchasePrice, totalValue,
                        currentValue);
            } else if ("ETF".equalsIgnoreCase(type)) {
                return new ETF(id, type, symbol, name, esgRating, sharesQuantity, purchasePrice, totalValue,
                        currentValue);
            } else {
                throw new JsonParseException("Unknown investment type: " + type);
            }
        } catch (NumberFormatException e) {
            logger.error("Error parsing numeric value during deserialization: {}", e.getMessage(), e);
            throw new JsonParseException("Error deserializing Investment object due to invalid numeric value", e);
        } catch (NullPointerException e) {
            logger.error("Missing required field during deserialization: {}", e.getMessage(), e);
            throw new JsonParseException("Error deserializing Investment object due to missing field", e);
        } catch (JsonParseException e) {
            logger.error("JSON parsing error occurred during deserialization: {}", e.getMessage(), e);
            throw new JsonParseException("Error deserializing Investment object due to JSON parsing issue", e);
        }
    }
}
