package com.cbfacademy.apiassessment.utility;

import com.cbfacademy.apiassessment.model.ESGRating;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class ESGRatingDeserializer implements JsonDeserializer<ESGRating> {
    @Override
    public ESGRating deserialize(JsonElement json, java.lang.reflect.Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        String ratingString = json.getAsString();
        // Check if the ratingString is blank or absent in the JSON
        if (ratingString == null || ratingString.trim().isEmpty()) {
            // Set a default value
            return ESGRating.UNSPECIFIED; // You can set any default value you want
        }

        // If not blank, parse the ESGRating normally
        return ESGRating.valueOf(ratingString);
    }
}
