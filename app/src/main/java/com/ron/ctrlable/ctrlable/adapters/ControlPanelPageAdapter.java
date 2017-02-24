package com.ron.ctrlable.ctrlable.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;

import com.github.rongi.rotate_layout.layout.RotateLayout;
import com.ron.ctrlable.ctrlable.R;
import com.ron.ctrlable.ctrlable.activities.ControlPanelActivity;
import com.ron.ctrlable.ctrlable.classes.ConfigurationClass;
import com.ron.ctrlable.ctrlable.views.ControlPanelView;

import org.json.simple.JSONArray;

import java.util.ArrayList;

import static com.ron.ctrlable.ctrlable.classes.ConfigurationClass.columns;
import static com.ron.ctrlable.ctrlable.classes.ConfigurationClass.controlsObject;
import static com.ron.ctrlable.ctrlable.classes.ConfigurationClass.currentScreenIndex;
import static com.ron.ctrlable.ctrlable.classes.ConfigurationClass.current_view;
import static com.ron.ctrlable.ctrlable.classes.ConfigurationClass.isTablet;
import static com.ron.ctrlable.ctrlable.classes.ConfigurationClass.itemRects;
import static com.ron.ctrlable.ctrlable.classes.ConfigurationClass.pcm;
import static com.ron.ctrlable.ctrlable.classes.ConfigurationClass.rows;

/**
 * Created by Android Developer on 2/22/2017.
 */

public class ControlPanelPageAdapter extends PagerAdapter {

    private int beginPosX = 0;
    private int beginPosY = 0;
    private int endPosX = 0;
    private int endPosY = 0;
    ArrayList<Integer> selectedViewList;
    ArrayList<Integer> selectedSideViewList;
    private Context mContext;
    private JSONArray screenControls = new JSONArray();
    private JSONArray allControls = new JSONArray();
    private ControlPanelView view;
    private ControlPanelViewAdapter adapter;

    public ControlPanelPageAdapter(Context context) {
        mContext = context;
        allControls = (JSONArray) controlsObject.get(current_view);
        screenControls = (JSONArray) allControls.get(currentScreenIndex);
    }

    @Override
    public Object instantiateItem(final ViewGroup collection, int position) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.view_control_panel, collection, false);

        view = (ControlPanelView) layout.findViewById(R.id.control_recycler_view1);
        final ControlPanelViewAdapter adapter = new ControlPanelViewAdapter(mContext, collection.getMeasuredHeight(), pcm);

        view.setLayoutManager(ConfigurationClass.rows);
        view.setAdapter(adapter, mContext);

        final ZSideControlViewAdapter sideAdapter = new ZSideControlViewAdapter(mContext, collection.getMeasuredHeight(), pcm);

        view.setOnTouchListener(new View.OnTouchListener() {
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
                        for (int i = 0; i < rows * columns; i++) {
                            if (Rect.intersects(rect, itemRects[i])) {
                                selectedViewList.add(i);
                            }
                        }
                        if (selectedViewList.size() > 0 && pcm == ControlPanelView.UserInteractionMode.UserInteractionLayout) {
                            adapter.selectMultiControlViews(selectedViewList);
                            sideAdapter.selectMultiControlViews(selectedSideViewList);

                            ((ControlPanelActivity) mContext).setAddControlsMode(selectedViewList, 0, mContext.getString(R.string.control_panel_view));
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
                        for (int i = 0; i < rows * columns; i++) {
                            if (Rect.intersects(rect, itemRects[i])) {
                                Log.d("Selected", String.valueOf(i + 1));
                                selectedViewList.add(i);
                            }
                        }
                        if (selectedViewList.size() > 0 && pcm == ControlPanelView.UserInteractionMode.UserInteractionLayout) {
                            adapter.selectMultiControlViews(selectedViewList);
                            sideAdapter.selectMultiControlViews(selectedSideViewList);

                            ((ControlPanelActivity) mContext).setAddControlsMode(selectedViewList, 0, mContext.getString(R.string.control_panel_view));
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                        // touch up code
                        break;
                }
                return false;
            }
        });

        collection.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public int getCount() {
        return allControls.size();
    }
}
