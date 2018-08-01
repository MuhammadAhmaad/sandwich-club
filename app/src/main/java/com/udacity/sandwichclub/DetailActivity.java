package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private TextView mAlsoKnownAsTv;
    private TextView mIngredientsTv;
    private TextView mOriginTv;
    private TextView mDescriptionTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mAlsoKnownAsTv = (TextView) findViewById(R.id.also_known_tv);
        mIngredientsTv = (TextView) findViewById(R.id.ingredients_tv);
        mOriginTv = (TextView) findViewById(R.id.origin_tv);
        mDescriptionTv = (TextView) findViewById(R.id.description_tv);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

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
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        if (!TextUtils.isEmpty(sandwich.getDescription())) {
            mDescriptionTv.setText(sandwich.getDescription());
        } else
            mDescriptionTv.setText("--not found--");
        if (!TextUtils.isEmpty(sandwich.getPlaceOfOrigin())) {
            mOriginTv.setText(sandwich.getPlaceOfOrigin());
        } else
            mOriginTv.setText("--not found--");
        if (sandwich.getAlsoKnownAs().size() != 0) {
            for (String i : sandwich.getAlsoKnownAs()) {
                mAlsoKnownAsTv.append(i + " , ");
            }
        } else
            mAlsoKnownAsTv.setText("--not found--");
        if (sandwich.getIngredients().size() != 0) {
            for (String i : sandwich.getIngredients()) {
                mIngredientsTv.append(i + " , ");
            }
        } else
            mIngredientsTv.setText("--not found--");
    }
}
