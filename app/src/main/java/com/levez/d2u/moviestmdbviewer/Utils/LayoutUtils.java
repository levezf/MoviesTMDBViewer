package com.levez.d2u.moviestmdbviewer.Utils;

import android.util.DisplayMetrics;
import android.util.TypedValue;

public class LayoutUtils {

    public static int getPixelsFromDPs(float dp, DisplayMetrics displayMetrics){
        return (int) (TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics));
    }
}
