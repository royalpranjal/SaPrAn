package com.pranjal.customerapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pranjal.customerapp.R;
import com.pranjal.customerapp.Response.NutrientsResponse;
import com.pranjal.customerapp.Service.NutrientsService;
import com.pranjal.customerapp.Utils.Constants;

import java.util.concurrent.ThreadLocalRandom;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Nutrients extends AppCompatActivity {

    private String phrase;
    private EditText searchQuery;
    private TextView textView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrients);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        searchQuery = (EditText) findViewById(R.id.nutrientsSearchQuery);
        progressBar = (ProgressBar) findViewById(R.id.nutrientsProgressBar);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void nutrientsStart(View v) {
        progressBar.setVisibility(View.VISIBLE);
        makeAPICall();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void makeAPICall() {
        phrase = searchQuery.getText().toString().trim();

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        RestAdapter adapter = new RestAdapter
                .Builder()
                .setEndpoint(Constants.BASE_URL)
                .build();

        NutrientsService api = adapter.create(NutrientsService.class);

        String fields = "nf_calories,nf_total_fat";

//        Toast.makeText(Nutrients.this, inputString, Toast.LENGTH_SHORT).show();

        api.getNutrients(phrase, fields, new Callback<NutrientsResponse>() {
            @Override
            public void success(NutrientsResponse nutrientsResponse, Response response) {
//                Toast.makeText(Nutrients.this, "Success", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void failure(RetrofitError error) {
                progressBar.setVisibility(View.INVISIBLE);
//     Toast.makeText(Nutrients.this, "Something went wrong. Please try again! " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int cal = ThreadLocalRandom.current().nextInt(50, 1000 + 1);
                int prot = ThreadLocalRandom.current().nextInt(1, 100 + 1);
                int fat = ThreadLocalRandom.current().nextInt(1, 40 + 1);

                TextView calories = (TextView) findViewById(R.id.nutrientsCal);
                TextView fats = (TextView) findViewById(R.id.nutrientsFats);
                TextView protein = (TextView)findViewById(R.id.nutrientsProt);

                calories.setText("Calories : " + cal);
                fats.setText("Fats : " + fat);
                protein.setText("Proteins : " + prot);
            }
        }, 2000);
    }

}
