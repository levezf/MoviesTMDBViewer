package com.levez.d2u.moviestmdbviewer.View;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.MenuItem;

import com.levez.d2u.moviestmdbviewer.Models.api.Constant;
import com.levez.d2u.moviestmdbviewer.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dagger.android.support.DaggerAppCompatActivity;

import static com.levez.d2u.moviestmdbviewer.Models.api.Constant.TAG_FRAG_FAVORITES;
import static com.levez.d2u.moviestmdbviewer.Models.api.Constant.TAG_FRAG_MOVIE;
import static com.levez.d2u.moviestmdbviewer.Models.api.Constant.TAG_FRAG_SEARCH;
import static com.levez.d2u.moviestmdbviewer.Models.api.Constant.TAG_FRAG_TV_SERIES;

public class MainActivity extends DaggerAppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{



    private Bundle mSavedInstanceState;
    private final static int REQUEST_CODE_ASK_PERMISSIONS = 1;
    private static final String[] REQUIRED_SDK_PERMISSIONS = new String[] {Manifest.permission.INTERNET};
    private String mTagFrag;
    private static final String EXTRA_TAG_FRAG = "tag_frag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        this.mSavedInstanceState=savedInstanceState;
        checkPermissions();

    }


    private void checkPermissions() {
        final List<String> missingPermissions = new ArrayList<>();
        for (final String permission : REQUIRED_SDK_PERMISSIONS) {
            final int result = ContextCompat.checkSelfPermission(this, permission);
            if (result != PackageManager.PERMISSION_GRANTED) {
                missingPermissions.add(permission);
            }
        }
        if (!missingPermissions.isEmpty()) {
            final String[] permissions = missingPermissions
                    .toArray(new String[0]);
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_ASK_PERMISSIONS);
        } else {
            final int[] grantResults = new int[REQUIRED_SDK_PERMISSIONS.length];
            Arrays.fill(grantResults, PackageManager.PERMISSION_GRANTED);
            onRequestPermissionsResult(REQUEST_CODE_ASK_PERMISSIONS, REQUIRED_SDK_PERMISSIONS,
                    grantResults);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                for (int index = permissions.length - 1; index >= 0; --index) {
                    if (grantResults[index] != PackageManager.PERMISSION_GRANTED) {
                        finish();
                        return;
                    }
                }
                setupOnCreate();
                break;
        }
    }

    private void setupOnCreate() {

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setOnNavigationItemReselectedListener(menuItem -> {
            //non-refresh
        });

        if (mSavedInstanceState == null) {
            inflateFragment(CinematographicFragment.newInstance(Constant.TAG_TYPE_MOVIE),TAG_FRAG_MOVIE);
        }else{
            mTagFrag = mSavedInstanceState.getString(EXTRA_TAG_FRAG);
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(EXTRA_TAG_FRAG, mTagFrag);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        Fragment fragment = null;
        String tag = "";

        switch (menuItem.getItemId()){

            case R.id.action_search:
                fragment = SearchFragment.newInstance();
                tag = TAG_FRAG_SEARCH;
                break;

            case R.id.action_movies:
                fragment = CinematographicFragment.newInstance(Constant.TAG_TYPE_MOVIE);
                tag = TAG_FRAG_MOVIE;

                break;

            case R.id.action_series:
                fragment = CinematographicFragment.newInstance(Constant.TAG_TYPE_TV_SERIES);
                tag = TAG_FRAG_TV_SERIES;
                break;

            case R.id.action_favorites:
                fragment = new Fragment();
                tag = TAG_FRAG_FAVORITES;
                break;
        }

        inflateFragment(fragment, tag);

        return true;
    }


    public void inflateFragment(Fragment fragment, String tag){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment);
        if(!(fragment instanceof CinematographicFragment)){

            transaction.addToBackStack(mTagFrag);
        }


        transaction.commit();

        mTagFrag = tag;


    }
}
