package com.pranjal.customerapp.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.pranjal.customerapp.Adapter.FindItemsByIngredientsAdapter;
import com.pranjal.customerapp.R;
import com.pranjal.customerapp.Response.FindByIngredientsResponse;
import com.pranjal.customerapp.Service.FindByIngredientsService;
import com.pranjal.customerapp.Utils.Constants;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Recipe extends AppCompatActivity {

    private EditText searchQuery;
    private String input;
    private TextView outputText;
    private ProgressBar progessBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        searchQuery = (EditText) findViewById(R.id.recipeSearchQuery);
        outputText = (TextView) findViewById(R.id.recipeTextView);
        progessBar = (ProgressBar) findViewById(R.id.recipeProgressBar);

        progessBar.setVisibility(View.INVISIBLE);
        progessBar.isIndeterminate();

        if(getIntent().getStringExtra("Image_URL") != null){
            input = getIntent().getStringExtra("Image_URL");

//            Toast.makeText(Recipe.this, input, Toast.LENGTH_SHORT).show();
            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }

            makeAPICall();
        }
    }

    public void recipeStart(View v){
        input = searchQuery.getText().toString().trim();
        progessBar.setVisibility(View.VISIBLE);
        makeAPICall();
    }

    private void makeAPICall() {
        RestAdapter adapter = new RestAdapter
                .Builder()
                .setEndpoint(Constants.BASE_URL)
                .build();

        FindByIngredientsService api = adapter.create(FindByIngredientsService.class);

        api.getRecipeList(false, input, false, 25, 1, new Callback<List<FindByIngredientsResponse>>() {
            @Override
            public void success(List<FindByIngredientsResponse> findByIngredientsResponse, Response response) {
                progessBar.setVisibility(View.INVISIBLE);
//                Toast.makeText(Recipe.this, "Success", Toast.LENGTH_SHORT).show();

                if(findByIngredientsResponse.size() == 0){
                    outputText.setText("Something went wrong. Please try again!");
                }
                else{
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(Recipe.this, 1);

                    RecyclerView rView = (RecyclerView) findViewById(R.id.recyclerView);
                    rView.setLayoutManager(gridLayoutManager);
                    FindItemsByIngredientsAdapter rAdapter = new FindItemsByIngredientsAdapter(Recipe.this, findByIngredientsResponse);
                    rView.setAdapter(rAdapter);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                progessBar.setVisibility(View.INVISIBLE);
                Toast.makeText(Recipe.this, "Something went wrong. Please try again!", Toast.LENGTH_SHORT).show();
                outputText.setText("Something went wrong. Please try again!");
            }
        });

    }
}
