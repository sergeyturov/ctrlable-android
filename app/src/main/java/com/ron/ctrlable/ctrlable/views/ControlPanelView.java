package com.ron.ctrlable.ctrlable.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.ron.ctrlable.ctrlable.adapters.ControlPanelViewAdapter;
import com.ron.ctrlable.ctrlable.adapters.ZSideControlViewAdapter;

import static com.ron.ctrlable.ctrlable.views.ControlPanelView.UserInteractionMode.*;

/**
 * Created by Android Developer on 2/9/2017.
 */

public class ControlPanelView extends RecyclerView {

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
//    private ControlPanelViewAdapter adapter;
    private RecyclerView.Adapter adapter;
    private int beginPosX = 0;
    private int beginPosY = 0;
    private int endPosX = 0;
    private int endPosY = 0;
    private boolean isDragging = false;

    private UserInteractionMode userInteractionMode = UserInteractionLayout;


    public ControlPanelView(Context context) {
        super(context);
        this.context = context;
    }

    public ControlPanelView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ControlPanelView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setLayoutManager(int row_number) {
        this.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManage = new GridLayoutManager(context, row_number);
        this.setLayoutManager(layoutManage);
    }

    public void setAdapter(ControlPanelViewAdapter a, Context c) {
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
