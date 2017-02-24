package com.ron.ctrlable.ctrlable.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.ron.ctrlable.ctrlable.R;

/**
 * Created by Android Developer on 2/13/2017.
 */

public class SplashActivity extends CustomActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            ImageView imageView = (ImageView) findViewById(R.id.img_background);
            imageView.setBackgroundResource(R.drawable.splash_landscape);
        }
        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the ControlPanel-Activity. */
                Intent mainIntent = new Intent(SplashActivity.this, ControlPanelActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }, 2000);
    }
}
