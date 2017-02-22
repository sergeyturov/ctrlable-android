package com.ron.ctrlable.ctrlable.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ron.ctrlable.ctrlable.R;

import org.json.simple.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;

import static com.ron.ctrlable.ctrlable.classes.ConfigurationClass.SCREEN_FORMAT_ARRAY;
import static com.ron.ctrlable.ctrlable.classes.ConfigurationClass.controlsObject;
import static com.ron.ctrlable.ctrlable.classes.ConfigurationClass.currentScreenIndex;
import static com.ron.ctrlable.ctrlable.classes.ConfigurationClass.current_view;
import static com.ron.ctrlable.ctrlable.classes.ConfigurationClass.selectedItemsIndex;
import static com.ron.ctrlable.ctrlable.classes.ConfigurationClass.setStringSharedPreferences;

/**
 * Created by Android Developer on 2/12/2017.
 */

public class TypeSelectorActivity extends Activity {

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

                switch (position) {
                    case 0:
                        JSONArray allControls = (JSONArray) controlsObject.get(current_view);
                        JSONArray screenControls = (JSONArray) allControls.get(currentScreenIndex);

                        for (int i = 0; i < selectedItemsIndex.size(); i++) {
                            JSONObject itemObj = (JSONObject) screenControls.get(selectedItemsIndex.get(i));
                            itemObj.put(getString(R.string.view_name), getString(R.string.control_screen));
                            screenControls.set(selectedItemsIndex.get(i), itemObj);
                        }

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
