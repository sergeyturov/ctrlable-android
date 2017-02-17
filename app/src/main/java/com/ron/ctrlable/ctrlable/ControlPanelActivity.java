package com.ron.ctrlable.ctrlable;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ControlPanelActivity extends Activity {

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

    ZControlView zControlView;
    ZControlView zSideControlView;
    ArrayList<Integer> selectedViewList;
    ArrayList<Integer> selectedSideViewList;

    int device_width;
    private int beginPosX = 0;
    private int beginPosY = 0;
    private int endPosX = 0;
    private int endPosY = 0;
    private Rect[] itemRects;
    private Rect[] sideItemRects;
    public int grid_rows;
    public int grid_columns;

    private ZControlView.UserInteractionMode pcm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_control_panel);

        pcm = ZControlView.UserInteractionMode.UserInteractionDisabled;

        setPanelControlMode();
        resetGrid();

        Log.d("Tablet Mode:", String.valueOf(ConfigurationClass.isTablet(this)));
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

        controlView = (RelativeLayout) findViewById(R.id.control_view);

        controlView.post(new Runnable() {
            @Override
            public void run() {

                zControlView = (ZControlView) findViewById(R.id.control_recycler_view);
                zControlView.setLayoutManager(ConfigurationClass.rows);

                final ZControlViewAdapter adapter = new ZControlViewAdapter(getApplicationContext(), controlView.getMeasuredHeight(), pcm);
                zControlView.setAdapter(adapter, getApplicationContext());

                zSideControlView = (ZControlView) findViewById(R.id.side_recycler_view);
                zSideControlView.setLayoutManager(1);

                final ZSideControlViewAdapter sideAdapter = new ZSideControlViewAdapter(getApplicationContext(), controlView.getMeasuredHeight(), pcm);
                zSideControlView.setSideViewAdapter(sideAdapter, getApplicationContext());

                // Get the Rects of the each recyclerview items.
                setItemRects();
                setSideItemRects();

                zControlView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent e) {
                        switch (e.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                // touch down code
                                beginPosX = (int) e.getX();
                                beginPosY = (int) e.getY();
                                Rect rect = new Rect(beginPosX, beginPosY, beginPosX + 1, beginPosY + 1);
                                selectedViewList = new ArrayList<>();
                                selectedSideViewList = new ArrayList<>();
                                for (int i = 0; i < grid_rows * grid_columns; i++) {
                                    if (Rect.intersects(rect, itemRects[i])) {
                                        selectedViewList.add(i);
                                    }
                                }
                                if (selectedViewList.size() > 0 && pcm == ZControlView.UserInteractionMode.UserInteractionLayout) {
                                    adapter.selectMultiControlViews(selectedViewList);
                                    sideAdapter.selectMultiControlViews(selectedSideViewList);
                                    setupControlButton.setEnabled(true);
                                    setupControlButton.setAlpha(1.0f);
                                    setupControlButton.setBackgroundResource(R.drawable.add);
                                    gridButton.setEnabled(true);
                                    gridButton.setAlpha(1.0f);
                                }
                                break;

                            case MotionEvent.ACTION_MOVE:
                                // touch move code
                                endPosX = (int) e.getX();
                                endPosY = (int) e.getY();
                                Log.d("Touch move position", String.valueOf(endPosX));
                                Log.d("Touch move position", String.valueOf(endPosY));

                                rect = new Rect(Math.min(beginPosX, endPosX),
                                        Math.min(beginPosY, endPosY),
                                        Math.max(beginPosX, endPosX),
                                        Math.max(beginPosY, endPosY));

                                selectedViewList = new ArrayList<>();
                                selectedSideViewList = new ArrayList<>();
                                for (int i = 0; i < grid_rows * grid_columns; i++) {
                                    if (Rect.intersects(rect, itemRects[i])) {
                                        Log.d("Selected", String.valueOf(i + 1));
                                        selectedViewList.add(i);
                                    }
                                }
                                if (selectedViewList.size() > 0 && pcm == ZControlView.UserInteractionMode.UserInteractionLayout) {
                                    adapter.selectMultiControlViews(selectedViewList);
                                    sideAdapter.selectMultiControlViews(selectedSideViewList);
                                    setupControlButton.setEnabled(true);
                                    setupControlButton.setEnabled(true);
                                    setupControlButton.setAlpha(1.0f);
                                    setupControlButton.setBackgroundResource(R.drawable.add);
                                    gridButton.setEnabled(true);
                                    gridButton.setAlpha(1.0f);
                                }
                                break;

                            case MotionEvent.ACTION_UP:
                                // touch up code
                                break;
                        }
                        return false;
                    }
                });

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
                                if (selectedSideViewList.size() > 0 && pcm == ZControlView.UserInteractionMode.UserInteractionLayout) {
                                    adapter.selectMultiControlViews(selectedViewList);
                                    sideAdapter.selectMultiControlViews(selectedSideViewList);
                                    setupControlButton.setEnabled(true);
                                    setupControlButton.setAlpha(1.0f);
                                    setupControlButton.setBackgroundResource(R.drawable.add);
                                    gridButton.setEnabled(true);
                                    gridButton.setAlpha(1.0f);
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

                                if (selectedSideViewList.size() > 0 && pcm == ZControlView.UserInteractionMode.UserInteractionLayout) {
                                    adapter.selectMultiControlViews(selectedViewList);
                                    sideAdapter.selectMultiControlViews(selectedSideViewList);
                                    setupControlButton.setEnabled(true);
                                    setupControlButton.setAlpha(1.0f);
                                    setupControlButton.setBackgroundResource(R.drawable.add);
                                    gridButton.setEnabled(true);
                                    gridButton.setAlpha(1.0f);
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

    public void onSetupControl(View view) {

        if (pcm == ZControlView.UserInteractionMode.UserInteractionDisabled) {
            pcm = ZControlView.UserInteractionMode.UserInteractionLayout;
            setPanelControlMode();
            resetGrid();
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

        pcm = ZControlView.UserInteractionMode.UserInteractionDisabled;
        setPanelControlMode();
        resetGrid();
    }

    public void onSetupDone(View view) {

        pcm = ZControlView.UserInteractionMode.UserInteractionDisabled;
        setPanelControlMode();
        resetGrid();
    }

    public void onReconnect(View view) {
        networkIdicatorButton.setBackgroundResource(R.drawable.wifi);
    }

    public void onNavigate(View view) {
    }

    // Side-by-side button clicked
    public void onSideButtonClicked(View view) {
        Log.d("Touched", "  Side Button");
        zSideControlView = (ZControlView) findViewById(R.id.side_recycler_view);
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
                    device_width/2,
                    (i + 1) * zControlView.getHeight() / 4);
        }
    }
}