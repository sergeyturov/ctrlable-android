package com.ron.ctrlable.ctrlable.classes;

import android.content.Context;
import android.content.res.Configuration;

/**
 * Created by Android Developer on 2/13/2017.
 */

public class ConfigurationClass {

    public static int rows = 0;
    public static int columns = 0;

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}
