package com.levez.d2u.moviestmdbviewer.View;

import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import android.view.MenuItem;

import com.levez.d2u.moviestmdbviewer.Models.api.Constant;
import com.levez.d2u.moviestmdbviewer.R;

import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem menuItem) {
                //non-refresh
            }
        });

        if (savedInstanceState == null) {
            inflateFragment(CinematographicFragment.newInstance(Constant.TAG_TYPE_MOVIE));
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id = menuItem.getItemId();

        switch (id){

            case R.id.action_search:
                inflateFragment(SearchFragment.newInstance());
                break;

            case R.id.action_movies:
                inflateFragment(CinematographicFragment.newInstance(Constant.TAG_TYPE_MOVIE));
                break;

            case R.id.action_series:
                inflateFragment(CinematographicFragment.newInstance(Constant.TAG_TYPE_TV_SERIES));
                break;

            case R.id.action_favorites:
                break;
        }

        return true;
    }


    private void inflateFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commitNow();
    }
}
