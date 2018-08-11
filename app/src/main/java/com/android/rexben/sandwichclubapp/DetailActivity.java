package com.android.rexben.sandwichclubapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.rexben.sandwichclubapp.model.Sandwich;
import com.android.rexben.sandwichclubapp.utils.JsonUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String TAG = "Detail Activity.java";
    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    public ImageView image;
    public TextView mAlsoKnownAs;
    public TextView mOrigin;
    public TextView mDescription;
    public TextView mIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


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
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);

        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }


        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(image);

        setTitle(sandwich.getMainName());


    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        //TODO populate the text views!

        image = findViewById(R.id.image_iv);

        mAlsoKnownAs = findViewById(R.id.also_known_tv);
        String aliasString = "";
        if (sandwich.getAlsoKnownAs().isEmpty()) {
            mAlsoKnownAs.setText(R.string.no_info);
        } else {
            List<String> aliases = sandwich.getAlsoKnownAs();
            for (String alsoKnown : aliases) {
                aliasString = aliasString + alsoKnown;
                mAlsoKnownAs.setText(aliasString);
            }
        }

        mOrigin = findViewById(R.id.origin_tv);
        if (sandwich.getPlaceOfOrigin().isEmpty()) {
            mOrigin.setText(R.string.no_info);
        } else {
            mOrigin.setText(sandwich.getPlaceOfOrigin());
        }
        mDescription = findViewById(R.id.description_tv);
        mDescription.setText(sandwich.getDescription());

        mIngredients = findViewById(R.id.ingredients_tv);
        StringBuilder ingredientsString = new StringBuilder();
        List<String> ingredientsList = sandwich.getIngredients();
        for (String ingredient : ingredientsList) {
            ingredientsString.append(ingredient);
        }


        mIngredients.setText(ingredientsString.toString());


    }


}