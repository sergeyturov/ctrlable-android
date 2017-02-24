package com.ron.ctrlable.ctrlable.views;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.ron.ctrlable.ctrlable.R;
import com.ron.ctrlable.ctrlable.activities.ControlPanelActivity;
import com.ron.ctrlable.ctrlable.classes.ChildViewListener;

import static com.ron.ctrlable.ctrlable.classes.ConfigurationClass.device_rotation;
import static com.ron.ctrlable.ctrlable.classes.ConfigurationClass.pcm;

/**
 * Created by Android Developer on 2/22/2017.
 */

public class ControlScreenView extends RelativeLayout {

    Context context;

    private ChildViewListener listener;

    public void setControlScreenViewListener(ChildViewListener listener) {
        this.listener = listener;
    }

    public ControlScreenView(Context context) {
        super(context);
        init(context);
    }

    public ControlScreenView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ControlScreenView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ControlScreenView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(final Context context) {
        this.context = context;
        inflate(getContext(), R.layout.item_control_screen, this);


        final RelativeLayout mainView = (RelativeLayout) findViewById(R.id.view_control_screen);
        mainView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (pcm == ControlPanelView.UserInteractionMode.UserInteractionDisabled) {
                    Log.d("Touched", String.valueOf(pcm));
                    if (context instanceof ControlPanelActivity) {
                        ((ControlPanelActivity) context).goToNextControlPanel();
                    }
                }
                return false;
            }
        });
    }
}
