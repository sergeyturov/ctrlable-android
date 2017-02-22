package com.ron.ctrlable.ctrlable.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ron.ctrlable.ctrlable.R;
import com.ron.ctrlable.ctrlable.classes.ConfigurationClass;
import com.ron.ctrlable.ctrlable.views.ControlPanelView;

import static com.ron.ctrlable.ctrlable.classes.ConfigurationClass.currentScreenIndex;
import static com.ron.ctrlable.ctrlable.classes.ConfigurationClass.pcm;

/**
 * Created by Android Developer on 2/22/2017.
 */

public class ControlPanelPageAdapter extends PagerAdapter {

    private Context mContext;

    public ControlPanelPageAdapter(Context context) {
        mContext = context;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {

//        currentScreenIndex = position;

        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.view_control_panel, collection, false);

        ControlPanelView view = (ControlPanelView) layout.findViewById(R.id.control_recycler_view);
        view.setLayoutManager(ConfigurationClass.rows);

        final ControlPanelViewAdapter adapter = new ControlPanelViewAdapter(mContext, collection.getMeasuredHeight(), pcm);
        view.setAdapter(adapter, mContext);

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
        return 4;
    }
}
