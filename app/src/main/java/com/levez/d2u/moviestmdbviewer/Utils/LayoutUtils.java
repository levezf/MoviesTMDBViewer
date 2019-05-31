package com.levez.d2u.moviestmdbviewer.Utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.levez.d2u.moviestmdbviewer.R;

public class LayoutUtils {

    public static int getPixelsFromDPs(float dp, DisplayMetrics displayMetrics){
        return (int) (TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics));
    }

    public static void markFavorite(Context context, FloatingActionButton fab){
        fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_favorite_white_24dp));
    }

    public static void clearFavorite(Context context, FloatingActionButton fab) {
        fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_favorite_border_white_24dp));
    }
}
