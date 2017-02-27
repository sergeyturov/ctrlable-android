package com.ron.ctrlable.ctrlable.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.rongi.rotate_layout.layout.RotateLayout;
import com.ron.ctrlable.ctrlable.activities.ControlPanelActivity;
import com.ron.ctrlable.ctrlable.classes.ConfigurationClass;
import com.ron.ctrlable.ctrlable.R;
import com.ron.ctrlable.ctrlable.views.ControlPanelView;
import com.ron.ctrlable.ctrlable.views.ControlScreenView;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import static com.ron.ctrlable.ctrlable.classes.ConfigurationClass.controlsObject;
import static com.ron.ctrlable.ctrlable.classes.ConfigurationClass.currentScreenIndex;
import static com.ron.ctrlable.ctrlable.classes.ConfigurationClass.device_rotation;
import static com.ron.ctrlable.ctrlable.classes.ConfigurationClass.isTablet;
import static com.ron.ctrlable.ctrlable.classes.ConfigurationClass.sliding_home_panel;

public class ControlPanelViewAdapter extends RecyclerView.Adapter<ControlPanelViewAdapter.ViewHolder> {
    private Context context;
    private int controlview_height;
    private int singleSelPos = -1;
    private ArrayList<Integer> multiSelPos = new ArrayList<>();
    private int device_width;
    private int device_height;
    private ControlPanelView.UserInteractionMode userInteractionMode;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public ControlPanelViewAdapter(Context c, int controlview_height, ControlPanelView.UserInteractionMode uim) {
        this.context = c;
        this.controlview_height = controlview_height;
        this.userInteractionMode = uim;
    }

    @Override
    public ControlPanelViewAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_control_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onBindViewHolder(final ControlPanelViewAdapter.ViewHolder viewHolder, final int i) {

        device_width = context.getResources().getDisplayMetrics().widthPixels;
        device_height = context.getResources().getDisplayMetrics().heightPixels;

        viewHolder.view_control.getLayoutParams().width = device_width / ConfigurationClass.rows;
        viewHolder.view_control.getLayoutParams().height = controlview_height / ConfigurationClass.columns;

        viewHolder.img_eidt.setBackgroundResource(R.drawable.control_edit_mark);
        viewHolder.img_eidt.getLayoutParams().width = device_width / ConfigurationClass.rows;
        viewHolder.img_eidt.getLayoutParams().height = controlview_height / ConfigurationClass.columns;

        viewHolder.img_select.setBackgroundResource(R.drawable.control_select_mark);
        viewHolder.img_select.getLayoutParams().width = device_width / ConfigurationClass.rows;
        viewHolder.img_select.getLayoutParams().height = controlview_height / ConfigurationClass.columns;


        if (userInteractionMode == ControlPanelView.UserInteractionMode.UserInteractionDisabled) {
            viewHolder.img_eidt.setVisibility(View.INVISIBLE);
        }

        if (singleSelPos == -1) {
            if (multiSelPos.contains(i)) {
                viewHolder.img_select.setVisibility(View.VISIBLE);
            } else {
                viewHolder.img_select.setVisibility(View.INVISIBLE);
            }
        } else {
            if (i == singleSelPos) {
                viewHolder.img_select.setVisibility(View.VISIBLE);
            } else {
                viewHolder.img_select.setVisibility(View.INVISIBLE);
            }
        }

        JSONArray screenControls = (JSONArray) ((JSONArray) controlsObject.get(context.getString(R.string.control_panel_view))).get(currentScreenIndex);
        JSONObject itemObj = (JSONObject) screenControls.get(i);

        // Add the view for the [Control Screen] widget.
        if (Objects.equals(itemObj.get(context.getString(R.string.view_name)), context.getString(R.string.control_screen))) {
            final View view = new ControlScreenView(context);
            if (view.getParent() != null) {
                ((ViewGroup)view.getParent()).removeAllViews();
            }
            viewHolder.view_control.addView(view);
            if (!isTablet(context) || sliding_home_panel) {
                viewHolder.view_control.setAngle(device_rotation);
            }
            Log.d("Device rotation: ", String.valueOf(device_rotation));
            viewHolder.img_eidt.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return ConfigurationClass.rows * ConfigurationClass.columns;
    }

    public void selectMultiControlViews(ArrayList<Integer> selectedList) {
        if (userInteractionMode == ControlPanelView.UserInteractionMode.UserInteractionLayout) {
            singleSelPos = -1;
            multiSelPos.clear();
            multiSelPos.addAll(selectedList);
            notifyDataSetChanged();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView img_eidt;
        private ImageView img_select;
        private RotateLayout view_control;

        ViewHolder(View view) {
            super(view);

            img_eidt = (ImageView) view.findViewById(R.id.edit_mark_img);
            img_select = (ImageView) view.findViewById(R.id.select_mark_img);
            view_control = (RotateLayout) view.findViewById(R.id.control_view);
        }
    }
}