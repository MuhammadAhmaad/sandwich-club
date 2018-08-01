package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        final String NAME  = "name";
        final String MAIN_NAME= "mainName";
        final String ALSO_KNOWN_AS = "alsoKnownAs";
        final String PLACE_OF_ORIGIN = "placeOfOrigin";
        final String DESCRIPTION = "description";
        final String IMAGE_URL = "image";
        final String INGREDIENTS = "ingredients";

        Sandwich sandwich = null;
        JSONObject jasonSandwich = null;
        try {
            jasonSandwich = new JSONObject(json);
            sandwich = new Sandwich();

            JSONObject jsonSandwichName = jasonSandwich.getJSONObject(NAME);
            sandwich.setMainName(jsonSandwichName.getString(MAIN_NAME));

            JSONArray jsonSandwichKnownAsArray= jsonSandwichName.getJSONArray(ALSO_KNOWN_AS);
            sandwich.setAlsoKnownAs(new ArrayList<String>());
            for(int i=0;i<jsonSandwichKnownAsArray.length();i++)
            {
                String knownAsElementI = jsonSandwichKnownAsArray.getString(i);
                sandwich.getAlsoKnownAs().add(knownAsElementI);
            }

            sandwich.setPlaceOfOrigin(jasonSandwich.getString(PLACE_OF_ORIGIN));

            sandwich.setDescription(jasonSandwich.getString(DESCRIPTION));

            sandwich.setImage(jasonSandwich.getString(IMAGE_URL));

            JSONArray jsonSandwichIngredientsArray= jasonSandwich.getJSONArray(INGREDIENTS);
            sandwich.setIngredients(new ArrayList<String>());
            for(int i=0;i<jsonSandwichIngredientsArray.length();i++)
            {
                String ingredientI = jsonSandwichIngredientsArray.getString(i);
                sandwich.getIngredients().add(ingredientI);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sandwich;
    }
}
