package com.ron.ctrlable.ctrlable.views;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Android Developer on 2/18/2017.
 */

public class ZControlView extends View {
    public ZControlView(Context context) {
        super(context);
    }

    public ZControlView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ZControlView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ZControlView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
