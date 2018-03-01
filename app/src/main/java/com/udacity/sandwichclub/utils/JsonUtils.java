package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String LOG_TAG = JsonUtils.class.getSimpleName();

    public static Sandwich parseSandwichJson(String json) {

        String mainName = "";
        String placeOfOrigin = "";
        String description = "";
        String imageUrl = "";
        List<String> alsoKnownAs = new ArrayList<>();
        List<String> ingredients = new ArrayList<>();

        if (json != null) {
            try{
                // get the whole JSONObject
                JSONObject sandwiches = new JSONObject(json);

                // get the "name" JSONObject
                JSONObject names = sandwiches.getJSONObject("name");

                // get the "mainName" from "name" JSONObject
                mainName = names.getString("mainName");

                // get the array of "alsoKnownAs" names from "name" JSONObject
                JSONArray alsoKnownAsArray = names.getJSONArray("alsoKnownAs");

                // parse elements of the "alsoKnownAs" JSONArray
                for (int i=0; i < alsoKnownAsArray.length(); i++) {
                    try{
                        alsoKnownAs.add(alsoKnownAsArray.getString(i));
                    } catch (JSONException e) {
                        Log.e(LOG_TAG, "Can't parse JSONArray", e);
                    }
                }

                // get the "placeOfOrigin" from "sandwiches" JSONObject
                placeOfOrigin = sandwiches.getString("placeOfOrigin");

                // get the "description" from "sandwiches" JSONObject
                description = sandwiches.getString("description");

                // get the "imageUrl" from "sandwiches" JSONObject
                imageUrl = sandwiches.getString("image");

                // get the array of "ingredientsList" from "sandwiches" JSONObject
                JSONArray ingredientsArray = sandwiches.getJSONArray("ingredients");

                // parse elements of the "ingredients" JSONArray
                for (int i=0; i < ingredientsArray.length(); i++) {
                    try{
                        ingredients.add(ingredientsArray.getString(i));
                    } catch (JSONException e) {
                        Log.e(LOG_TAG, "Can't parse JSONArray", e);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {
            Log.e(LOG_TAG, "Couldn't get json from server.");
        }

        return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, imageUrl, ingredients);
    }
}
