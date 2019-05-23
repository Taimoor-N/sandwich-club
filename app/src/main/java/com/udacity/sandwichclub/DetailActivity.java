package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView mAlsoKnownAsTextView;
    private TextView mPlaceOfOriginTextView;
    private TextView mDescriptionTextView;
    private TextView mIngredientsTextView;

    private LinearLayout mAlsoKnownAsLinearLayout;
    private LinearLayout mPlaceOfOriginLinearLayout;
    private LinearLayout mDescriptionLinearLayout;
    private LinearLayout mIngredientsLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView mSandwichImage = findViewById(R.id.image_iv);

        mAlsoKnownAsTextView = findViewById(R.id.also_known_tv);
        mPlaceOfOriginTextView = findViewById(R.id.origin_tv);
        mDescriptionTextView = findViewById(R.id.description_tv);
        mIngredientsTextView = findViewById(R.id.ingredients_tv);

        mAlsoKnownAsLinearLayout = findViewById(R.id.also_known_ll);
        mPlaceOfOriginLinearLayout = findViewById(R.id.origin_ll);
        mDescriptionLinearLayout = findViewById(R.id.description_ll);
        mIngredientsLinearLayout = findViewById(R.id.ingredients_ll);

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
                .into(mSandwichImage);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        String placeOfOrigin = sandwich.getPlaceOfOrigin();
        String description = sandwich.getDescription();
        List<String> alsoKnown = sandwich.getAlsoKnownAs();
        List<String> ingredients = sandwich.getIngredients();

        // Adding Place of Origin
        if (placeOfOrigin != null && !placeOfOrigin.equals("")) {
            mPlaceOfOriginLinearLayout.setVisibility(View.VISIBLE);
            mPlaceOfOriginTextView.setText(placeOfOrigin);
        } else {
            mPlaceOfOriginLinearLayout.setVisibility(View.GONE);
        }

        // Adding Description
        if (description != null && !description.equals("")) {
            mDescriptionLinearLayout.setVisibility(View.VISIBLE);
            mDescriptionTextView.setText(description);
        } else {
            mDescriptionLinearLayout.setVisibility(View.GONE);
        }

        mAlsoKnownAsTextView.setText("");
        mIngredientsTextView.setText("");

        // Adding Also Known As
        if (alsoKnown.size() > 0) {
            mAlsoKnownAsLinearLayout.setVisibility(View.VISIBLE);

            for (int i = 0; i < alsoKnown.size(); i++) {
                mAlsoKnownAsTextView.append(alsoKnown.get(i));
                if (i < (alsoKnown.size() - 1)) {
                    mAlsoKnownAsTextView.append("\n");
                }
            }
        } else {
            mAlsoKnownAsLinearLayout.setVisibility(View.GONE);
        }

        // Adding Ingredients
        if (ingredients.size() > 0) {
            mIngredientsLinearLayout.setVisibility(View.VISIBLE);

            for (int i = 0; i < ingredients.size(); i++) {
                mIngredientsTextView.append(ingredients.get(i));
                if (i < (ingredients.size() - 1)) {
                    mIngredientsTextView.append("\n");
                }
            }
        } else {
            mIngredientsLinearLayout.setVisibility(View.GONE);
        }
    }
}
