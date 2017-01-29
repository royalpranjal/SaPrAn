package com.pranjal.customerapp.Activity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
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

import com.pranjal.customerapp.Adapter.TalkToChatBoxAdapter;
import com.pranjal.customerapp.R;
import com.pranjal.customerapp.Response.TalkToChatBoxResponse;
import com.pranjal.customerapp.Service.TalkToChatBoxService;
import com.pranjal.customerapp.Utils.Constants;

import java.util.concurrent.ThreadLocalRandom;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class FoodBot extends AppCompatActivity {

    private String inputString;
    private EditText searchQuery;
    private TextView textView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_bot);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        textView = (TextView) findViewById(R.id.chatBoxTextView);
        searchQuery = (EditText) findViewById(R.id.chatBoxSearchQuery);
        progressBar = (ProgressBar) findViewById(R.id.foodBotSrogressBar);
        progressBar.setVisibility(View.INVISIBLE);
        progressBar.isIndeterminate();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void foodBotStart(View v){
        progressBar.setVisibility(View.VISIBLE);
        makeAPICall();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void makeAPICall() {
        inputString = searchQuery.getText().toString().trim();

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        RestAdapter adapter = new RestAdapter
                .Builder()
                .setEndpoint(Constants.BASE_URL)
                .build();

        TalkToChatBoxService api = adapter.create(TalkToChatBoxService.class);

//        Toast.makeText(FoodBot.this, inputString, Toast.LENGTH_SHORT).show();

        int number = ThreadLocalRandom.current().nextInt(1, 10000 + 1);

        api.getSuggestions(number + "", inputString, new Callback<TalkToChatBoxResponse>() {
            @Override
            public void success(TalkToChatBoxResponse talkToChatBoxResponse, Response response) {
//                Toast.makeText(FoodBot.this, "Success", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);

                textView.setText(talkToChatBoxResponse.getAnswerText());

                GridLayoutManager gridLayoutManager = new GridLayoutManager(FoodBot.this, 1);

                RecyclerView rView = (RecyclerView) findViewById(R.id.textBoxRecyclerView);
                rView.setLayoutManager(gridLayoutManager);

                TalkToChatBoxAdapter rAdapter = new TalkToChatBoxAdapter(FoodBot.this, talkToChatBoxResponse.getMedia());
                rView.setAdapter(rAdapter);

            }

            @Override
            public void failure(RetrofitError error) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(FoodBot.this, "Something went wrong. Please try again!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
