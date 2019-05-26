package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    /**
     * This method takes a JSON string, parses it and creates the corresponding Sandwich object
     * @param json JSON string
     * @return Sandwich object
     */
    public static Sandwich parseSandwichJson(String json) {

        final String JSON_NAME = "name";

        // "Main Name" and "Also Known As" are children of JSON_NAME
        final String JSON_MAIN_NAME = "mainName";
        final String JSON_ALSO_KNOWN_AS = "alsoKnownAs";

        final String JSON_PLACE_OF_ORIGIN = "placeOfOrigin";
        final String JSON_DESCRIPTION = "description";
        final String JSON_IMAGE = "image";
        final String JSON_INGREDIENTS = "ingredients";

        String mainName = null;
        List<String> alsoKnownAs = new ArrayList<>();
        String placeOfOrigin = null;
        String description = null;
        String image = null;
        List<String> ingredients = new ArrayList<>();

        try {
            JSONObject sandwichJson = new JSONObject(json);
            JSONObject name = sandwichJson.getJSONObject(JSON_NAME);

            mainName = name.getString(JSON_MAIN_NAME);

            JSONArray akaJsonArray = name.getJSONArray(JSON_ALSO_KNOWN_AS);
            for (int i = 0; i < akaJsonArray.length(); i++) {
                alsoKnownAs.add(akaJsonArray.getString(i));
            }

            placeOfOrigin = sandwichJson.getString(JSON_PLACE_OF_ORIGIN);
            description = sandwichJson.getString(JSON_DESCRIPTION);
            image = sandwichJson.getString(JSON_IMAGE);

            JSONArray ingredientsJsonArray = sandwichJson.getJSONArray(JSON_INGREDIENTS);
            for (int i = 0; i < ingredientsJsonArray.length(); i++) {
                ingredients.add(ingredientsJsonArray.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
    }
}
