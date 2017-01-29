package com.pranjal.customerapp.Activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.pranjal.customerapp.R;
import com.thefinestartist.finestwebview.FinestWebView;

public class Location extends AppCompatActivity {

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        editText = (EditText) findViewById(R.id.locationSearchQuery);
    }

    public void locationStart(View v){
        String search = "http://www.mytaste.in/search/?q=" + editText.getText().toString().trim();
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        new FinestWebView.Builder(Location.this).show(search);
    }
}
