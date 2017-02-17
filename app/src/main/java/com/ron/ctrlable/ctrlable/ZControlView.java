package com.ron.ctrlable.ctrlable;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import static com.ron.ctrlable.ctrlable.ZControlView.UserInteractionMode.*;

/**
 * Created by Android Developer on 2/9/2017.
 */

public class ZControlView extends RecyclerView {

    public enum UserInteractionMode {
        UserInteractionDisabled,
        UserInteractionEnabled,
        UserInteractionDemo,
        UserInteractionLayout
    }
    public enum SetupWorkflowModeType {
        SetupWorkflowModeNone,
        SetupWorkflowModeNew,
        SetupWorkflowModeNewMulti,
        SetupWorkflowModeExisting
    }

    private Context context;
//    private ZControlViewAdapter adapter;
    private RecyclerView.Adapter adapter;
    private int beginPosX = 0;
    private int beginPosY = 0;
    private int endPosX = 0;
    private int endPosY = 0;
    private boolean isDragging = false;

    private UserInteractionMode userInteractionMode = UserInteractionLayout;


    public ZControlView(Context context) {
        super(context);
        this.context = context;
    }

    public ZControlView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ZControlView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setLayoutManager(int row_number) {
        this.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManage = new GridLayoutManager(context, row_number);
        this.setLayoutManager(layoutManage);
    }

    public void setAdapter(ZControlViewAdapter a, Context c) {
        this.adapter = a;
        this.context = c;
        super.setAdapter(adapter);

        isDragging = false;
    }

    public void setSideViewAdapter(ZSideControlViewAdapter a, Context c) {
        this.adapter = a;
        this.context = c;
        super.setAdapter(adapter);

        isDragging = false;
    }
}
