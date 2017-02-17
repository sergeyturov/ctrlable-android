package com.ron.ctrlable.ctrlable;

import android.content.Context;
import android.content.res.Configuration;

/**
 * Created by Android Developer on 2/13/2017.
 */

class ConfigurationClass {

    static int rows = 0;
    static int columns = 0;

    static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}
