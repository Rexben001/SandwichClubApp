package com.android.rexben.sandwichclubapp.utils;


import com.android.rexben.sandwichclubapp.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = new Sandwich();

        if (json != null) {

            String name;
            List<String> alsoKnownAs;
            String origin;
            String description;
            String image;
            List<String> ingredients;

            JSONObject jsonObject;
            try {
                jsonObject = new JSONObject(json);

                JSONObject nameJSON = jsonObject.getJSONObject("name");
                name = nameJSON.getString("mainName");
                sandwich.setMainName(name);

                alsoKnownAs = new ArrayList<>();

                JSONArray sandwichAliases = nameJSON.optJSONArray("alsoKnownAs");
                if (sandwichAliases.length() > 0) {
                    for (int i = 0; i < sandwichAliases.length(); i++) {
                        alsoKnownAs.add("* " + sandwichAliases.getString(i) + "\n");
                    }
                }
                sandwich.setAlsoKnownAs(alsoKnownAs);

                origin = jsonObject.getString("placeOfOrigin");
                sandwich.setPlaceOfOrigin(origin);

                description = jsonObject.getString("description");
                sandwich.setDescription(description);

                image = jsonObject.getString("image");
                sandwich.setImage(image);

                JSONArray ingredientsJSON = jsonObject.getJSONArray("ingredients");

                ingredients = new ArrayList<>();

                for (int i = 0; i < ingredientsJSON.length(); i++) {
                    ingredients.add("* " + ingredientsJSON.getString(i) + "\n");
                }

                sandwich.setIngredients(ingredients);

                return sandwich;

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return sandwich;
    }
}
