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
        String mainName = null;
        List<String> alsoKnownAs = new ArrayList<>();
        String placeOfOrigin = null;
        String description = null;
        String image = null;
        List<String> ingredients = new ArrayList<>();

        try {
            JSONObject sandwichJson = new JSONObject(json);
            JSONObject name = sandwichJson.getJSONObject("name");

            mainName = name.getString("mainName");

            JSONArray akaJsonArray = name.getJSONArray("alsoKnownAs");
            for (int i = 0; i < akaJsonArray.length(); i++) {
                alsoKnownAs.add(akaJsonArray.getString(i));
            }

            placeOfOrigin = sandwichJson.getString("placeOfOrigin");
            description = sandwichJson.getString("description");
            image = sandwichJson.getString("image");

            JSONArray ingredientsJsonArray = sandwichJson.getJSONArray("ingredients");
            for (int i = 0; i < ingredientsJsonArray.length(); i++) {
                ingredients.add(ingredientsJsonArray.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
    }
}
