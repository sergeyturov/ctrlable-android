package com.ron.ctrlable.ctrlable.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;

import com.ron.ctrlable.ctrlable.classes.ConfigurationClass;
import com.ron.ctrlable.ctrlable.R;
import com.ron.ctrlable.ctrlable.adapters.SettingsAdapter;

/**
 * Created by Android Developer on 2/13/2017.
 */

public class SettingsActivity extends CustomActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_settings);

        setButtonCollectionView();
    }

    public void setButtonCollectionView() {

        RelativeLayout collectionView = (RelativeLayout) this.findViewById(R.id.collection_view);

        RecyclerView buttonsRecyclerView = (RecyclerView) this.findViewById(R.id.buttons_recycler_view);
        RecyclerView.LayoutManager layoutManage = new GridLayoutManager(this, 4);

        if (ConfigurationClass.isTablet(this)) {
            collectionView.getLayoutParams().height = 100;
            int row_number = getResources().getDisplayMetrics().widthPixels / 150;
            layoutManage = new GridLayoutManager(this, row_number);
        }
        buttonsRecyclerView.setLayoutManager(layoutManage);

        SettingsAdapter settingsAdapter = new SettingsAdapter(this);
        buttonsRecyclerView.setAdapter(settingsAdapter);
    }

    // General Button clicked.
    public void onGeneral(View view) {

        RelativeLayout settingView = (RelativeLayout) this.findViewById(R.id.setting_view);
        settingView.setX(-settingView.getWidth());
        settingView.setVisibility(View.VISIBLE);
        settingView.setAlpha(0.0f);

        // Start the animation
        settingView.animate()
                .translationX(0)
                .alpha(1.0f);
    }

    // Ctrlable Button clicked.
    public void onVera(View view) {

        RelativeLayout settingView = (RelativeLayout) this.findViewById(R.id.setting_view);
        settingView.setX(-settingView.getWidth());
        settingView.setVisibility(View.VISIBLE);
        settingView.setAlpha(0.0f);

        // Start the animation
        settingView.animate()
                .translationX(0)
                .alpha(1.0f);
    }

    // Network button clicked.
    public void onNetwork(View view) {

        RelativeLayout settingView = (RelativeLayout) this.findViewById(R.id.setting_view);
        settingView.setX(-settingView.getWidth());
        settingView.setVisibility(View.VISIBLE);
        settingView.setAlpha(0.0f);

        // Start the animation
        settingView.animate()
                .translationX(0)
                .alpha(1.0f);
    }

    // Files button clicked.
    public void onFiles(View view) {

        RelativeLayout settingView = (RelativeLayout) this.findViewById(R.id.setting_view);
        settingView.setX(-settingView.getWidth());
        settingView.setVisibility(View.VISIBLE);
        settingView.setAlpha(0.0f);

        // Start the animation
        settingView.animate()
                .translationX(0)
                .alpha(1.0f);
    }

    // Colors button clicked.
    public void onColors(View view) {

        RelativeLayout settingView = (RelativeLayout) this.findViewById(R.id.setting_view);
        settingView.setX(-settingView.getWidth());
        settingView.setVisibility(View.VISIBLE);
        settingView.setAlpha(0.0f);

        // Start the animation
        settingView.animate()
                .translationX(0)
                .alpha(1.0f);
    }

    // Media Buttton clicked.
    public void onMedia(View view) {

        RelativeLayout settingView = (RelativeLayout) this.findViewById(R.id.setting_view);
        settingView.setX(-settingView.getWidth());
        settingView.setVisibility(View.VISIBLE);
        settingView.setAlpha(0.0f);

        // Start the animation
        settingView.animate()
                .translationX(0)
                .alpha(1.0f);
    }

    // Info button clicked.
    public void onInfo(View view) {

        RelativeLayout settingView = (RelativeLayout) this.findViewById(R.id.setting_view);
        settingView.setX(-settingView.getWidth());
        settingView.setVisibility(View.VISIBLE);
        settingView.setAlpha(0.0f);

        // Start the animation
        settingView.animate()
                .translationX(0)
                .alpha(1.0f);
    }

    // Close button clicked.
    public void onClose(View view) {
        this.finish();
        overridePendingTransition(R.animator.pull_out_bottom, R.animator.pull_out_bottom);
    }
}