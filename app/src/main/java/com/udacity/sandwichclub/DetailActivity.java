package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private static final String UNDEFINED = "Undefined";

    private TextView alsoKnownAsTv, placeOfOriginTv, descriptionTv, ingredientsTv;
    private Sandwich sandwich;
    private ImageView ingredientsIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ingredientsIv = findViewById(R.id.image_iv);
        alsoKnownAsTv = findViewById(R.id.also_known_tv);
        placeOfOriginTv = findViewById(R.id.origin_tv);
        descriptionTv = findViewById(R.id.description_tv);
        ingredientsTv = findViewById(R.id.ingredients_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        if (!sandwich.getPlaceOfOrigin().isEmpty()){
            placeOfOriginTv.setText(sandwich.getPlaceOfOrigin());
        } else {
            placeOfOriginTv.setText(UNDEFINED);
        }

        if (!sandwich.getDescription().isEmpty()) {
            descriptionTv.setText(sandwich.getDescription());
        } else {
            descriptionTv.setText(UNDEFINED);
        }

        List<String> ingredientsList = sandwich.getIngredients();
        String ingredients = "";
        if (!ingredientsList.isEmpty()) {
            for (int i=0; i < ingredientsList.size(); i++) {
                ingredients += ingredientsList.get(i) + ";";
            }
            ingredientsTv.setText(ingredients);
        } else {
            ingredientsTv.setText(UNDEFINED);
        }

        List<String> alsoKnownAsList = sandwich.getAlsoKnownAs();
        String alsoKnownAs = "";
        if (!alsoKnownAsList.isEmpty()) {
            for (int i=0; i < alsoKnownAsList.size(); i++) {
                alsoKnownAs += alsoKnownAsList.get(i) + "; ";
            }
            alsoKnownAsTv.setText(alsoKnownAs);
        } else {
            alsoKnownAsTv.setText(UNDEFINED);
        }
    }
}