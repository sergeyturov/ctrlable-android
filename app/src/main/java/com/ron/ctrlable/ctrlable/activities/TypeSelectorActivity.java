package com.ron.ctrlable.ctrlable.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ron.ctrlable.ctrlable.R;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Objects;

import static com.ron.ctrlable.ctrlable.classes.ConfigurationClass.columns;
import static com.ron.ctrlable.ctrlable.classes.ConfigurationClass.controlsObject;
import static com.ron.ctrlable.ctrlable.classes.ConfigurationClass.currentScreenIndex;
import static com.ron.ctrlable.ctrlable.classes.ConfigurationClass.current_view;
import static com.ron.ctrlable.ctrlable.classes.ConfigurationClass.rows;
import static com.ron.ctrlable.ctrlable.classes.ConfigurationClass.selectedItemsIndex;

/**
 * Created by Android Developer on 2/12/2017.
 */

public class TypeSelectorActivity extends CustomActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_type_selector);

        final ListView listView = (ListView) findViewById(R.id.control_list_view);
        String[] values = new String[]{
                "Control Screen",
                "No Control"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_type_selector, R.id.text_title, values);
        listView.setAdapter(adapter);

        // ListView item click listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                JSONArray allControls = (JSONArray) controlsObject.get(current_view);
                JSONArray screenControls = (JSONArray) allControls.get(currentScreenIndex);
                if (Objects.equals(current_view, getString(R.string.side_view))) {
                    screenControls = (JSONArray) allControls.get(0);
                }

                switch (position) {
                    case 0:
                        for (int i = 0; i < selectedItemsIndex.size(); i++) {
                            JSONObject itemObj = (JSONObject) screenControls.get(selectedItemsIndex.get(i));
                            itemObj.put(getString(R.string.view_name), getString(R.string.control_screen));
                            screenControls.set(selectedItemsIndex.get(i), itemObj);

                            JSONArray controlViewArray = new JSONArray();
                            for (int j = 0; j < rows * columns; j++) {
                                controlViewArray.add(new JSONObject());
                            }
                            JSONArray controlPanelArray = (JSONArray) controlsObject.get(getString(R.string.control_panel_view));
                            controlPanelArray.add(controlViewArray);
                            controlsObject.put(getString(R.string.control_panel_view), controlPanelArray);
                        }

                        allControls = (JSONArray) controlsObject.get(current_view);
                        allControls.set(currentScreenIndex, screenControls);
                        controlsObject.put(current_view, allControls);

//                        Intent main_intent = new Intent(TypeSelectorActivity.this, ControlPanelActivity.class);
//                        startActivity(main_intent);
                        finish();
                        break;
                    case 1:
                        Intent intent = new Intent(TypeSelectorActivity.this, DeviceSelectorActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }

    public void OnCancel(View view) {
        this.finish();
    }
}
