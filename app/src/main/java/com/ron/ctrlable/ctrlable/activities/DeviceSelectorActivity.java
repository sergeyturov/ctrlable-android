package com.ron.ctrlable.ctrlable.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ron.ctrlable.ctrlable.R;

/**
 * Created by Android Developer on 2/14/2017.
 */

public class DeviceSelectorActivity extends CustomActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_device_selector);

        final ListView listView = (ListView) findViewById(R.id.device_list_view);
        String[] values = new String[] {
                "1",
                "2"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_device_selector, R.id.text_title, values);
        listView.setAdapter(adapter);

        // ListView item click listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DeviceSelectorActivity.this, ControlPanelActivity.class);
                startActivity(intent);
            }
        });
    }

    public void OnCancel(View view) {
        this.finish();
    }
}
