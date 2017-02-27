package com.ron.ctrlable.ctrlable.adapters;

import android.content.Context;
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
import com.ron.ctrlable.ctrlable.R;
import com.ron.ctrlable.ctrlable.views.ControlPanelView;
import com.ron.ctrlable.ctrlable.views.ControlScreenView;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import static com.ron.ctrlable.ctrlable.classes.ConfigurationClass.controlsObject;
import static com.ron.ctrlable.ctrlable.classes.ConfigurationClass.device_rotation;

/**
 * Created by Android Developer on 2/17/2017.
 */

public class ZSideControlViewAdapter extends RecyclerView.Adapter<ZSideControlViewAdapter.ViewHolder> {

    private Context context;
    private int controlview_height;
    private int singleSelPos = -1;
    private ArrayList<Integer> multiSelPos = new ArrayList<>();
    private int device_width;
    private int device_height;
    private ControlPanelView.UserInteractionMode userInteractionMode;

    public ZSideControlViewAdapter(Context c, int controlview_height, ControlPanelView.UserInteractionMode uim) {
        this.context = c;
        this.controlview_height = controlview_height;
        this.userInteractionMode = uim;
    }

    @Override
    public ZSideControlViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_control_view, parent, false);
        return new ZSideControlViewAdapter.ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onBindViewHolder(ZSideControlViewAdapter.ViewHolder viewHolder, int position) {
        device_width = context.getResources().getDisplayMetrics().widthPixels;
        device_height = context.getResources().getDisplayMetrics().heightPixels;

        int cell_height = controlview_height / 4;
        if (device_rotation == 0 || device_rotation == 180) {
            cell_height = device_width / 4;
        }

        viewHolder.view_control.getLayoutParams().width = device_width / 4;
        viewHolder.view_control.getLayoutParams().height = cell_height;

        viewHolder.img_eidt.setBackgroundResource(R.drawable.control_edit_mark);
        viewHolder.img_eidt.getLayoutParams().width = device_width / 4;
        viewHolder.img_eidt.getLayoutParams().height = cell_height;

        viewHolder.img_select.setBackgroundResource(R.drawable.control_select_mark);
        viewHolder.img_select.getLayoutParams().width = device_width / 4;
        viewHolder.img_select.getLayoutParams().height = cell_height;

        if (userInteractionMode == ControlPanelView.UserInteractionMode.UserInteractionDisabled) {
            viewHolder.img_eidt.setVisibility(View.INVISIBLE);
        }

        if (singleSelPos == -1) {
            if (multiSelPos.contains(position)) {
                viewHolder.img_select.setVisibility(View.VISIBLE);
            } else {
                viewHolder.img_select.setVisibility(View.INVISIBLE);
            }
        } else {
            if (position == singleSelPos) {
                viewHolder.img_select.setVisibility(View.VISIBLE);
            } else {
                viewHolder.img_select.setVisibility(View.INVISIBLE);
            }
        }

        JSONArray screenControls = (JSONArray) ((JSONArray) controlsObject.get(context.getString(R.string.side_view))).get(0);
        JSONObject itemObj = (JSONObject) screenControls.get(position);
        if (Objects.equals(itemObj.get(context.getString(R.string.view_name)), context.getString(R.string.control_screen))) {
//            View view = LayoutInflater.from(context).inflate(R.layout.item_control_screen, null);
            View view = new ControlScreenView(context);
            if (view.getParent() != null) {
                ((ViewGroup)view.getParent()).removeAllViews();
            }
            viewHolder.view_control.addView(view);
            if (device_rotation == 0) {
                viewHolder.view_control.setAngle(90);
            } else {
                viewHolder.view_control.setAngle(0);
            }
            viewHolder.img_eidt.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return 4;
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
