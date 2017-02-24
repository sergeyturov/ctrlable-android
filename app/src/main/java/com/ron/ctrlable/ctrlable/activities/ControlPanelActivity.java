package com.ron.ctrlable.ctrlable.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import android.icu.text.LocaleDisplayNames;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ron.ctrlable.ctrlable.adapters.ControlPanelPageAdapter;
import com.ron.ctrlable.ctrlable.classes.ConfigurationClass;
import com.ron.ctrlable.ctrlable.R;
import com.ron.ctrlable.ctrlable.interfaces.ChildViewListener;
import com.ron.ctrlable.ctrlable.views.ControlPanelView;
import com.ron.ctrlable.ctrlable.adapters.ControlPanelViewAdapter;
import com.ron.ctrlable.ctrlable.adapters.ZSideControlViewAdapter;
import com.ron.ctrlable.ctrlable.views.ControlScreenView;
import com.ron.ctrlable.ctrlable.views.ViewPagerCustomDuration;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.Objects;

import static com.ron.ctrlable.ctrlable.classes.ConfigurationClass.SCREEN_FORMAT_ARRAY;
import static com.ron.ctrlable.ctrlable.classes.ConfigurationClass.controlsObject;
import static com.ron.ctrlable.ctrlable.classes.ConfigurationClass.currentScreenIndex;
import static com.ron.ctrlable.ctrlable.classes.ConfigurationClass.current_view;
import static com.ron.ctrlable.ctrlable.classes.ConfigurationClass.getStringSharedPreferences;
import static com.ron.ctrlable.ctrlable.classes.ConfigurationClass.initializeControlsJson;
import static com.ron.ctrlable.ctrlable.classes.ConfigurationClass.itemRects;
import static com.ron.ctrlable.ctrlable.classes.ConfigurationClass.pcm;
import static com.ron.ctrlable.ctrlable.classes.ConfigurationClass.selectedItemsIndex;
import static com.ron.ctrlable.ctrlable.classes.ConfigurationClass.setStringSharedPreferences;
import static com.ron.ctrlable.ctrlable.views.ControlPanelView.UserInteractionMode.UserInteractionLayout;

public class ControlPanelActivity extends CustomActivity {

    public Button setupControlButton;
    public Button gridButton;
    public Button upButton;
    public Button linkButton;
    public Button deleteButton;
    public Button setupButton;
    public Button helpButton;
    public Button settingsButton;
    public Button cancelButton;
    public Button saveButton;
    public Button rightButton;
    public Button leftButton;
    public Button networkIdicatorButton;
    public TextView titleTextView;
    public RelativeLayout controlView;
    public RelativeLayout sideView;
    public ViewPager controlPanelPager;
    public ViewPagerCustomDuration vp;
    public ControlPanelViewAdapter adapter;
    public ZSideControlViewAdapter sideAdapter;
    public ControlPanelPageAdapter pageAdapter;

    ControlPanelView zControlView;
    ControlPanelView zSideControlView;
    ArrayList<Integer> selectedViewList;
    ArrayList<Integer> selectedSideViewList;
    static Context context;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    int device_width;
    private int beginPosX = 0;
    private int beginPosY = 0;
    private int endPosX = 0;
    private int endPosY = 0;
    private Rect[] sideItemRects;
    private int screenIndex = 0; // If 0 sideview, else parent/subscreen control panel.

    public int grid_rows;
    public int grid_columns;

//    private Animation toLandAnim, toPortAnim;
//    private OrientationListener orientationListener;

//    private ControlPanelView.UserInteractionMode pcm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_control_panel);

        pcm = ControlPanelView.UserInteractionMode.UserInteractionDisabled;
        currentScreenIndex = 0;

        setPanelControlMode();
        resetGrid();
        setChildViewListener();

        // set the view pager for each Control Panels.
        setControlPanelPagerAdapter();

        Log.d("Tablet Mode:", String.valueOf(ConfigurationClass.isTablet(this)));

//        toLandAnim= AnimationUtils.loadAnimatthis, R.anim.ion(this, R.anim.menubutton_to_landscape);
//        toPortAnim= AnimationUtils.loadAnimation(menubutton_to_portrait);
    }

    @Override
    protected void onResume() {
        super.onResume();
        resetGrid();
        setControlPanelPagerAdapter();
    }

    public void setChildViewListener() {
        ControlScreenView controlScreenView = new ControlScreenView(this);
        controlScreenView.setControlScreenViewListener(new ChildViewListener() {
            @Override
            public void onViewTapped() {
                Log.d("Tapped", "OK");
            }
        });
    }

    // set the view pager for each Control Panels.
    public void setControlPanelPagerAdapter() {

        vp = (ViewPagerCustomDuration) findViewById(R.id.control_panel_pager);
        pageAdapter = new ControlPanelPageAdapter((ControlPanelActivity.this));
        vp.setAdapter(pageAdapter);
        vp.setScrollDurationFactor(5);
        setNavigateButtons();

        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d("Scrolled ", String.valueOf(position));
            }

            @Override
            public void onPageSelected(int position) {
                Log.d("Selected ", String.valueOf(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.d("Scroll state changed ", String.valueOf(state));
            }
        });
    }

    public void setPanelControlMode() {

        setupControlButton = (Button) findViewById(R.id.setup_control_button);
        setupControlButton.setBackgroundResource(R.drawable.settings);
        gridButton = (Button) findViewById(R.id.grid_button);
        upButton = (Button) findViewById(R.id.up_button);
        linkButton = (Button) findViewById(R.id.link_button);
        deleteButton = (Button) findViewById(R.id.delete_button);
        setupButton = (Button) findViewById(R.id.setup_button);
        helpButton = (Button) findViewById(R.id.help_button);
        settingsButton = (Button) findViewById(R.id.settings_button);
        cancelButton = (Button) findViewById(R.id.cancel_button);
        saveButton = (Button) findViewById(R.id.save_button);
        rightButton = (Button) findViewById(R.id.right_button);
        leftButton = (Button) findViewById(R.id.left_button);
        networkIdicatorButton = (Button) findViewById(R.id.network_indicator);
        networkIdicatorButton.setBackgroundResource(R.drawable.mios);
        titleTextView = (TextView) findViewById(R.id.title_text);

        sideView = (RelativeLayout) findViewById(R.id.side_view);
        if (!ConfigurationClass.isTablet(this)) {
            sideView.setVisibility(View.GONE);
        }

        switch (pcm) {
            case UserInteractionEnabled:
            case UserInteractionDisabled:
                titleTextView.setText("Control Screen");
                titleTextView.setVisibility(View.VISIBLE);
                saveButton.setVisibility(View.INVISIBLE);
                cancelButton.setVisibility(View.INVISIBLE);
                gridButton.setVisibility(View.INVISIBLE);
                gridButton.setEnabled(true);
                gridButton.setAlpha(1.0f);
                linkButton.setVisibility(View.INVISIBLE);
                linkButton.setEnabled(true);
                linkButton.setAlpha(1.0f);
                deleteButton.setVisibility(View.INVISIBLE);
                deleteButton.setEnabled(true);
                deleteButton.setAlpha(1.0f);
                setupControlButton.setVisibility(View.VISIBLE);
                setupControlButton.setEnabled(true);
                setupControlButton.setAlpha(1.0f);
                setupButton.setVisibility(View.INVISIBLE);
                helpButton.setVisibility(View.INVISIBLE);
                leftButton.setVisibility(View.INVISIBLE);
                rightButton.setVisibility(View.INVISIBLE);
                upButton.setVisibility(View.INVISIBLE);
                networkIdicatorButton.setVisibility(View.VISIBLE);
                settingsButton.setVisibility(View.VISIBLE);
                break;

            case UserInteractionDemo:        // Add button enabled state
//                titleTextView.setVisibility(View.INVISIBLE);
                titleTextView.setVisibility(View.VISIBLE);
                saveButton.setVisibility(View.VISIBLE);
                cancelButton.setVisibility(View.VISIBLE);
                gridButton.setVisibility(View.VISIBLE);
                gridButton.setAlpha(1.0f);
                linkButton.setVisibility(View.VISIBLE);
                linkButton.setEnabled(false);
                linkButton.setAlpha(0.5f);
                deleteButton.setVisibility(View.VISIBLE);
                deleteButton.setEnabled(false);
                deleteButton.setAlpha(0.5f);
                setupControlButton.setVisibility(View.VISIBLE);
                setupControlButton.setBackgroundResource(R.drawable.add);
                setupControlButton.setAlpha(1.0f);
                setupButton.setVisibility(View.VISIBLE);
                setupButton.setEnabled(false);
                helpButton.setVisibility(View.INVISIBLE);
                leftButton.setVisibility(View.INVISIBLE);
                rightButton.setVisibility(View.INVISIBLE);
                upButton.setVisibility(View.INVISIBLE);
                networkIdicatorButton.setVisibility(View.VISIBLE);
                settingsButton.setVisibility(View.INVISIBLE);
                break;

            case UserInteractionLayout:
//                titleTextView.setVisibility(View.INVISIBLE);
                titleTextView.setVisibility(View.VISIBLE);
                saveButton.setVisibility(View.VISIBLE);
                cancelButton.setVisibility(View.VISIBLE);
                gridButton.setVisibility(View.VISIBLE);
                gridButton.setEnabled(false);
                gridButton.setAlpha(0.5f);
                linkButton.setVisibility(View.VISIBLE);
                linkButton.setEnabled(false);
                linkButton.setAlpha(0.5f);
                deleteButton.setVisibility(View.VISIBLE);
                deleteButton.setEnabled(false);
                deleteButton.setAlpha(0.5f);
                setupControlButton.setVisibility(View.VISIBLE);
                setupControlButton.setEnabled(false);
                setupControlButton.setAlpha(0.5f);
                setupButton.setVisibility(View.VISIBLE);
                setupButton.setEnabled(false);
                helpButton.setVisibility(View.INVISIBLE);
                leftButton.setVisibility(View.INVISIBLE);
                rightButton.setVisibility(View.INVISIBLE);
                upButton.setVisibility(View.INVISIBLE);
                networkIdicatorButton.setVisibility(View.VISIBLE);
                settingsButton.setVisibility(View.INVISIBLE);
                break;
        }
    }

    public void resetGrid() {

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        device_width = displaymetrics.widthPixels;

        if (ConfigurationClass.isTablet(this)) {
            grid_rows = getResources().getInteger(R.integer.tablet_grid_rows);
            grid_columns = getResources().getInteger(R.integer.tablet_grid_columns);
        } else {
            grid_rows = getResources().getInteger(R.integer.phone_grid_rows);
            grid_columns = getResources().getInteger(R.integer.phone_grid_columns);
        }
        ConfigurationClass.rows = grid_rows;
        ConfigurationClass.columns = grid_columns;

        initializeControlsJson(ControlPanelActivity.this);

        controlView = (RelativeLayout) findViewById(R.id.control_view);
        controlView.post(new Runnable() {
            @Override
            public void run() {

                JSONArray allControls = (JSONArray) controlsObject.get(getString(R.string.control_panel_view));
                if (pcm == UserInteractionLayout) {
                    leftButton.setVisibility(View.INVISIBLE);
                    rightButton.setVisibility(View.INVISIBLE);
                }

                zControlView = (ControlPanelView) findViewById(R.id.control_recycler_view);
//                zControlView.setLayoutManager(ConfigurationClass.rows);
                adapter = new ControlPanelViewAdapter(ControlPanelActivity.this, controlView.getMeasuredHeight(), pcm);
//                zControlView.setAdapter(adapter, getApplicationContext());

                zSideControlView = (ControlPanelView) findViewById(R.id.side_recycler_view);
                zSideControlView.setLayoutManager(1);

                sideAdapter = new ZSideControlViewAdapter(ControlPanelActivity.this, controlView.getMeasuredHeight(), pcm);
                zSideControlView.setSideViewAdapter(sideAdapter, getApplicationContext());

                // Get the Rects of the each recyclerview items.
                setItemRects();
                setSideItemRects();

                zSideControlView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                // touch down code
                                beginPosX = (int) event.getX();
                                beginPosY = (int) event.getY();
                                Rect rect = new Rect(beginPosX, beginPosY, beginPosX + 1, beginPosY + 1);
                                selectedViewList = new ArrayList<>();
                                selectedSideViewList = new ArrayList<>();
                                for (int i = 0; i < 4; i++) {
                                    if (Rect.intersects(rect, sideItemRects[i])) {
                                        selectedSideViewList.add(i);
                                    }
                                }
                                if (selectedSideViewList.size() > 0 && pcm == UserInteractionLayout) {
                                    adapter.selectMultiControlViews(selectedViewList);
                                    sideAdapter.selectMultiControlViews(selectedSideViewList);

                                    setAddControlsMode(selectedSideViewList, 0, getString(R.string.side_view));
                                }
                                break;

                            case MotionEvent.ACTION_MOVE:
                                // touch move code
                                endPosX = (int) event.getX();
                                endPosY = (int) event.getY();
                                Log.d("Touch move position", String.valueOf(endPosX));
                                Log.d("Touch move position", String.valueOf(endPosY));

                                rect = new Rect(Math.min(beginPosX, endPosX),
                                        Math.min(beginPosY, endPosY),
                                        Math.max(beginPosX, endPosX),
                                        Math.max(beginPosY, endPosY));

                                selectedViewList = new ArrayList<>();
                                selectedSideViewList = new ArrayList<>();
                                for (int i = 0; i < 4; i++) {
                                    if (Rect.intersects(rect, sideItemRects[i])) {
                                        Log.d("Selected", String.valueOf(i + 1));
                                        selectedSideViewList.add(i);
                                    }
                                }

                                if (selectedSideViewList.size() > 0 && pcm == UserInteractionLayout) {
                                    adapter.selectMultiControlViews(selectedViewList);
                                    sideAdapter.selectMultiControlViews(selectedSideViewList);

                                    setAddControlsMode(selectedSideViewList, 0, getString(R.string.side_view));
                                }
                                break;

                            case MotionEvent.ACTION_UP:
                                // touch up code
                                break;
                        }
                        return false;
                    }
                });
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void goToNextControlPanel() {
        JSONArray allControls = (JSONArray) controlsObject.get(getString(R.string.control_panel_view));
        if (currentScreenIndex < allControls.size() - 1) {
            currentScreenIndex += 1;
        }
        vp.setAdapter(pageAdapter);
        vp.setCurrentItem(currentScreenIndex);
        setNavigateButtons();
    }

    // set the Add/Edit button from the selected controls.
    public void setAddControlsMode(ArrayList<Integer> selectedViews, int screenIndex, String currentViewMode) {

        selectedItemsIndex = selectedViews;
//        currentScreenIndex = screenIndex;
        current_view = currentViewMode;

        JSONArray allControls = (JSONArray) controlsObject.get(current_view);
        JSONArray screenControls = (JSONArray) allControls.get(currentScreenIndex);
        boolean temp_flag = false;
        for (int i = 0; i < selectedItemsIndex.size(); i++) {
            JSONObject itemObj = (JSONObject) screenControls.get(selectedItemsIndex.get(i));
            if (itemObj.size() > 0) {
                temp_flag = true;
                break;
            }
        }

        if (temp_flag) {
            setupControlButton.setEnabled(false);
            setupControlButton.setAlpha(0.5f);
            setupControlButton.setBackgroundResource(R.drawable.settings);
            gridButton.setEnabled(true);
            gridButton.setAlpha(1.0f);

        } else {
            setupControlButton.setEnabled(true);
            setupControlButton.setAlpha(1.0f);
            setupControlButton.setBackgroundResource(R.drawable.add);
            gridButton.setEnabled(true);
            gridButton.setAlpha(1.0f);
        }
    }

    public void onSetupControl(View view) {

        if (pcm == ControlPanelView.UserInteractionMode.UserInteractionDisabled) {
            pcm = UserInteractionLayout;
            setPanelControlMode();
            resetGrid();
            setControlPanelPagerAdapter();

        } else {
            Intent intent = new Intent(ControlPanelActivity.this, TypeSelectorActivity.class);
            startActivity(intent);
        }
    }

    public void setGridSize(View view) {
    }

    public void onSetupLink(View view) {
    }

    public void onDelete(View view) {
    }

    public void onSetup(View view) {
    }

    public void showSettings(View view) {

        Intent intent = new Intent(ControlPanelActivity.this, SettingsActivity.class);
        startActivity(intent);
        overridePendingTransition(R.animator.pull_in_bottom, R.animator.pull_in_bottom);
    }

    public void onSetupCancel(View view) {

        String json = getStringSharedPreferences(context, SCREEN_FORMAT_ARRAY);
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = (JSONObject) parser.parse(json);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        controlsObject = jsonObject;

        pcm = ControlPanelView.UserInteractionMode.UserInteractionDisabled;
        setPanelControlMode();
        resetGrid();
        setControlPanelPagerAdapter();
    }

    public void onSetupDone(View view) {

        setStringSharedPreferences(getApplicationContext(), SCREEN_FORMAT_ARRAY, controlsObject.toString());

        pcm = ControlPanelView.UserInteractionMode.UserInteractionDisabled;
        setPanelControlMode();
        resetGrid();
        setControlPanelPagerAdapter();
    }

    public void onReconnect(View view) {
        networkIdicatorButton.setBackgroundResource(R.drawable.wifi);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onNavigate(View view) {
        JSONArray allControls = (JSONArray) controlsObject.get(getString(R.string.control_panel_view));

        if (Objects.equals(view.getTag().toString(), "1")) {            // Up button
            currentScreenIndex = 0;

        } else if (Objects.equals(view.getTag().toString(), "2")) {     // Left button
            if (currentScreenIndex > 0) {
                currentScreenIndex -= 1;
            }

        } else if (Objects.equals(view.getTag().toString(), "3")) {     // Right button
            if (currentScreenIndex < allControls.size() - 1) {
                currentScreenIndex += 1;
            }
        }

        vp.setAdapter(pageAdapter);
        vp.setCurrentItem(currentScreenIndex);
        setNavigateButtons();
    }

    public void setNavigateButtons() {

        JSONArray allControls = (JSONArray) controlsObject.get(getString(R.string.control_panel_view));

        if (currentScreenIndex == 0) {
            rightButton.setVisibility(View.VISIBLE);
            leftButton.setVisibility(View.INVISIBLE);
            upButton.setVisibility(View.INVISIBLE);
        } else if (currentScreenIndex == allControls.size()-1) {
            rightButton.setVisibility(View.INVISIBLE);
            leftButton.setVisibility(View.VISIBLE);
            upButton.setVisibility(View.VISIBLE);
        } else {
            rightButton.setVisibility(View.VISIBLE);
            leftButton.setVisibility(View.VISIBLE);
            upButton.setVisibility(View.VISIBLE);
        }

    }

    // Side-by-side button clicked
    public void onSideButtonClicked(View view) {
        Log.d("Touched", "  Side Button");
        zSideControlView = (ControlPanelView) findViewById(R.id.side_recycler_view);
        ViewGroup.LayoutParams params = zSideControlView.getLayoutParams();
        if (params.width == 0) {
            params.width = device_width / 4;
        } else {
            params.width = 0;
        }
        zSideControlView.setLayoutParams(params);
    }

    // Initialize the Rect of the each Recyclerview Items.
    private void setItemRects() {
        itemRects = new Rect[grid_rows * grid_columns];
        for (int i = 0; i < grid_rows * grid_columns; i++) {
            itemRects[i] = new Rect((i % grid_rows) * device_width / grid_rows,
                    (i / grid_rows) * zControlView.getHeight() / grid_columns,
                    (i % grid_rows + 1) * device_width / grid_rows,
                    (i / grid_rows + 1) * zControlView.getHeight() / grid_columns);
        }
    }

    private void setSideItemRects() {
        sideItemRects = new Rect[4];
        for (int i = 0; i < 4; i++) {
            sideItemRects[i] = new Rect(0,
                    i * zControlView.getHeight() / 4,
                    device_width / 2,
                    (i + 1) * zControlView.getHeight() / 4);
        }
    }

//    private class OrientationListener extends OrientationEventListener {
//        final int ROTATION_O    = 1;
//        final int ROTATION_90   = 2;
//        final int ROTATION_180  = 3;
//        final int ROTATION_270  = 4;
//
//        private int rotation = 0;
//        public OrientationListener(Context context) { super(context); }
//
//        @Override public void onOrientationChanged(int orientation) {
//            if( (orientation < 35 || orientation > 325) && rotation!= ROTATION_O){ // PORTRAIT
//                rotation = ROTATION_O;
//
//            }
//            else if( orientation > 145 && orientation < 215 && rotation!=ROTATION_180){ // REVERSE PORTRAIT
//                rotation = ROTATION_180;
//                menuButton.startAnimation(toPortAnim);
//            }
//            else if(orientation > 55 && orientation < 125 && rotation!=ROTATION_270){ // REVERSE LANDSCAPE
//                rotation = ROTATION_270;
//                menuButton.startAnimation(toLandAnim);
//            }
//            else if(orientation > 235 && orientation < 305 && rotation!=ROTATION_90){ //LANDSCAPE
//                rotation = ROTATION_90;
//                menuButton.startAnimation(toLandAnim);
//            }
//        }
//    }
}