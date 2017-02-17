package com.ron.ctrlable.ctrlable.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ron.ctrlable.ctrlable.classes.ConfigurationClass;
import com.ron.ctrlable.ctrlable.R;
import com.ron.ctrlable.ctrlable.views.ControlPanelView;

import java.util.ArrayList;

public class ControlPanelViewAdapter extends RecyclerView.Adapter<ControlPanelViewAdapter.ViewHolder> {
    private Context context;
    private int controlview_height;
    private int singleSelPos = -1;
    private ArrayList<Integer> multiSelPos = new ArrayList<>();
    private int device_width;
    private int device_height;
    private ControlPanelView.UserInteractionMode userInteractionMode;

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

    @Override
    public void onBindViewHolder(ControlPanelViewAdapter.ViewHolder viewHolder, final int i) {

        device_width = context.getResources().getDisplayMetrics().widthPixels;
        device_height = context.getResources().getDisplayMetrics().heightPixels;

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
    }

    @Override
    public int getItemCount() {
        return ConfigurationClass.rows * ConfigurationClass.columns;
    }

    public void selectMultiControlViews(ArrayList<Integer> selectedList) {
        if (userInteractionMode == ControlPanelView.UserInteractionMode.UserInteractionLayout) {
            singleSelPos = -1;
            multiSelPos = selectedList;
            notifyDataSetChanged();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView img_eidt;
        private ImageView img_select;

        ViewHolder(View view) {
            super(view);

            img_eidt = (ImageView) view.findViewById(R.id.edit_mark_img);
            img_select = (ImageView) view.findViewById(R.id.select_mark_img);

            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent e) {
                    switch (e.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            // touch down code
                            Log.d("Position", String.valueOf(v.getTop()));
                            Log.d("Position", String.valueOf(v.getLeft()));
                            Log.d("Position", String.valueOf(v.getRight()));
                            Log.d("Position", String.valueOf(v.getBottom()));
                            break;

                        case MotionEvent.ACTION_MOVE:
                            // touch move code
                            break;

                        case MotionEvent.ACTION_UP:
                            // touch up code
                            break;
                    }

                    return false;
                }
            });
       }
    }
}