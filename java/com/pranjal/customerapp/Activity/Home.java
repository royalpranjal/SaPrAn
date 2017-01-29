package com.pranjal.customerapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.pranjal.customerapp.Adapter.HorizontalRecylerViewAdapter;
import com.pranjal.customerapp.Model.HorizontalItemObject;
import com.pranjal.customerapp.R;
import com.thefinestartist.finestwebview.FinestWebView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Home extends AppCompatActivity {

    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;

    private SliderLayout mDemoSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("SaPrAn");

        mDemoSlider = (SliderLayout)findViewById(R.id.home_slider);

        actionBar.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name){
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        setupNavigationView();

        startSlider();
        showHorizontalRecyclerView();
        showHorizontalRecyclerView1();
    }

    private void showHorizontalRecyclerView1() {
        List<HorizontalItemObject> rowListItem = getAllItems1();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewMain1);
        recyclerView.setLayoutManager(new LinearLayoutManager(Home.this, LinearLayoutManager.HORIZONTAL, false));
        HorizontalRecylerViewAdapter adapter = new HorizontalRecylerViewAdapter(Home.this, rowListItem);
        recyclerView.setAdapter(adapter);
    }

    private void startSlider() {
        final HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("https://spoonacular.com/recipes/crock-pot-beef-carnitas-tacos-668334",R.drawable.one);
        file_maps.put("https://spoonacular.com/recipes/mutton-stew-311127",R.drawable.two);
        file_maps.put("https://spoonacular.com/recipes/green-velvet-cheesecake-cake-517275",R.drawable.three);

        for(final String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);

            // initialize a SliderLayout
            textSliderView
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);

            mDemoSlider.addSlider(textSliderView);

            textSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(BaseSliderView slider) {
                    new FinestWebView.Builder(Home.this).show(name);
                }
            });
        }

        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);

    }

    private void showHorizontalRecyclerView() {
        List<HorizontalItemObject> rowListItem = getAllItems();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewMain);
        recyclerView.setLayoutManager(new LinearLayoutManager(Home.this, LinearLayoutManager.HORIZONTAL, false));
        HorizontalRecylerViewAdapter adapter = new HorizontalRecylerViewAdapter(Home.this, rowListItem);
        recyclerView.setAdapter(adapter);
    }

    public void setupNavigationView() {
        NavigationView view = (NavigationView) findViewById(R.id.navigation_view);

        view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.food_bot) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (isDrawerOpen()) {
                                mDrawerLayout.closeDrawers();
                            }
                            Intent intent = new Intent(Home.this, FoodBot.class);
                            startActivity(intent);
                        }
                    }, 200);
                } else if (menuItem.getItemId() == R.id.recipe) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (isDrawerOpen()) {
                                mDrawerLayout.closeDrawers();
                            }
                            Intent intent = new Intent(Home.this, Recipe.class);
                            startActivity(intent);

                        }
                    }, 200);
                }
                else if (menuItem.getItemId() == R.id.search_image) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (isDrawerOpen()) {
                                mDrawerLayout.closeDrawers();
                            }
                            Intent intent = new Intent(Home.this, SearchImage.class);
                            startActivity(intent);

                        }
                    }, 200);
                }
                else if (menuItem.getItemId() == R.id.nutrients) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (isDrawerOpen()) {
                                mDrawerLayout.closeDrawers();
                            }
                            Intent intent = new Intent(Home.this, Nutrients.class);
                            startActivity(intent);

                        }
                    }, 200);
                }
                else if (menuItem.getItemId() == R.id.location) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (isDrawerOpen()) {
                                mDrawerLayout.closeDrawers();
                            }
                            Intent intent = new Intent(Home.this, Location.class);
                            startActivity(intent);

                        }
                    }, 200);
                }
            return true;
            }
        });
    }

    public List<HorizontalItemObject> getAllItems() {
        List<HorizontalItemObject> allItems = new ArrayList<HorizontalItemObject>();
        allItems.add(new HorizontalItemObject("Crab", "https://spoonacular.com/recipes/crab-cake-poppers-and-friday-faves-532354", R.drawable.crab));
        allItems.add(new HorizontalItemObject("Dosa", "http://www.vegrecipesofindia.com/masala-dosa-recipe-how-to-make-masala-dosa-recipe/", R.drawable.dosa));
        allItems.add(new HorizontalItemObject("Fish", "https://spoonacular.com/recipes/thai-salmon-in-foil-793846", R.drawable.fish));
        allItems.add(new HorizontalItemObject("Paneer", "https://spoonacular.com/recipes/paneer-butter-masala-easy-paneer-s-no-onion-no-garlic-564670", R.drawable.paneer));
        allItems.add(new HorizontalItemObject("Chicken", "https://spoonacular.com/recipes/honey-soy-baked-chicken-thighs-840513", R.drawable.chicken));
        return allItems;
    }

    @Override
    public void onBackPressed(){
        if(isDrawerOpen()) {
            mDrawerLayout.closeDrawers();
        } else {
            super.onBackPressed();
        }
    }

    public boolean isDrawerOpen() {
        if(mDrawerLayout.isDrawerOpen(Gravity.LEFT))
            return true;

        return false;
    }

    public List<HorizontalItemObject> getAllItems1() {
        List<HorizontalItemObject> allItems1 = new ArrayList<HorizontalItemObject>();
        allItems1.add(new HorizontalItemObject("Lasagna", "https://spoonacular.com/recipes/lasagna-cups-587253", R.drawable.lasagna));
        allItems1.add(new HorizontalItemObject("Burger", "https://spoonacular.com/recipes/healthy-salmon-quinoa-burgers-690978", R.drawable.burger));
        allItems1.add(new HorizontalItemObject("Toast", "https://spoonacular.com/recipes/avocado-toast-with-eggs-spinach-and-tomatoes-818941", R.drawable.toast));
        allItems1.add(new HorizontalItemObject("Pizza", "https://spoonacular.com/recipes/greek-pita-pizzas-590115", R.drawable.pizza));
        allItems1.add(new HorizontalItemObject("Sandwich", "https://spoonacular.com/recipes/bacon-egg-muffin-cups-583480", R.drawable.sandwich));
        return allItems1;
    }
}
