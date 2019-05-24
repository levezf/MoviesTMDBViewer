package com.levez.d2u.moviestmdbviewer.Utils;

import java.text.NumberFormat;
import java.util.Locale;

public class FormatNumberUtils {

    public static <T extends Number> String format(T d){
        return NumberFormat.getInstance(Locale.US).format(d);
    }


}
