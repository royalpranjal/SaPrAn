package com.pranjal.customerapp.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.WindowManager;

import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.pranjal.customerapp.R;

public class Intro extends AppIntro2 {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        addSlide(AppIntroFragment.newInstance("Food Bot", "Discuss with our amazing new bot", R.drawable.chat_bot, Color.parseColor("#FFA500")));
        addSlide(AppIntroFragment.newInstance("Ingredients!", "Find out what to cook from the ingredients available!", R.drawable.cooking, Color.parseColor("#FFA500")));
        addSlide(AppIntroFragment.newInstance("Recipes by Image", "Upload a picture of ingredients & see what can be cooked!", R.drawable.picture, Color.parseColor("#FFA500")));

        // Hide Skip/Done button.
        showSkipButton(true);
        setProgressButtonEnabled(true);
    }
    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        Intent i = new Intent(Intro.this, Home.class);
        startActivity(i);
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        Intent i = new Intent(Intro.this, Home.class);
        startActivity(i);
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }

}
