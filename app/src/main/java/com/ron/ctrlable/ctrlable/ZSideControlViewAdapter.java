package com.ron.ctrlable.ctrlable;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

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
    private ZControlView.UserInteractionMode userInteractionMode;

    ZSideControlViewAdapter(Context c, int controlview_height, ZControlView.UserInteractionMode uim) {
        this.context = c;
        this.controlview_height = controlview_height;
        this.userInteractionMode = uim;
    }

    @Override
    public ZSideControlViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_control_view, parent, false);
        return new ZSideControlViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ZSideControlViewAdapter.ViewHolder viewHolder, int position) {
        device_width = context.getResources().getDisplayMetrics().widthPixels;
        device_height = context.getResources().getDisplayMetrics().heightPixels;

        viewHolder.img_eidt.setBackgroundResource(R.drawable.control_edit_mark);
        viewHolder.img_eidt.getLayoutParams().width = device_width / 4;
        viewHolder.img_eidt.getLayoutParams().height = controlview_height / 4;

        viewHolder.img_select.setBackgroundResource(R.drawable.control_select_mark);
        viewHolder.img_select.getLayoutParams().width = device_width / 4;
        viewHolder.img_select.getLayoutParams().height = controlview_height / 4;

        if (userInteractionMode == ZControlView.UserInteractionMode.UserInteractionDisabled) {
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
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    void selectMultiControlViews(ArrayList<Integer> selectedList) {
        if (userInteractionMode == ZControlView.UserInteractionMode.UserInteractionLayout) {
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
