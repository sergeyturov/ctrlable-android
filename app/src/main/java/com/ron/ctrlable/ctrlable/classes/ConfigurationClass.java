package com.ron.ctrlable.ctrlable.classes;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;

import com.ron.ctrlable.ctrlable.R;
import com.ron.ctrlable.ctrlable.views.ControlPanelView;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Android Developer on 2/13/2017.
 */

public class ConfigurationClass {

    public static int rows = 0;
    public static int columns = 0;
    public static JSONObject controlsObject = new JSONObject();
    public static ArrayList<Integer> selectedItemsIndex = new ArrayList<>();
    public static int currentScreenIndex = 0;    // If 0 sideview, else parent/subscreen control panel.
    public static Rect[] itemRects;
    public static String SCREEN_FORMAT_ARRAY = "SCREEN_ARRAY";
    public static String current_view = "";
    public static ControlPanelView.UserInteractionMode pcm;
    public static int device_rotation = 0;

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public static void setStringSharedPreferences(Context context, String key, String value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getStringSharedPreferences(Context context, String key) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(key, "");
    }

    public static void initializeControlsJson(Context context) {

        current_view = context.getString(R.string.control_panel_view);
        String json = getStringSharedPreferences(context, SCREEN_FORMAT_ARRAY);

        if (Objects.equals(json, "")) {

            JSONObject jsonObject = new JSONObject();
            JSONArray sideArray = new JSONArray();
            JSONArray sideParentArray = new JSONArray();
            for (int i = 0; i < 4; i++) {
                sideArray.add(new JSONObject());
            }
            sideParentArray.add(sideArray);
            jsonObject.put(context.getString(R.string.side_view), sideParentArray);

            JSONArray controlPanelArray = new JSONArray();
            JSONArray controlViewArray = new JSONArray();
            for (int j = 0; j < rows * columns; j++) {
                controlViewArray.add(new JSONObject());
            }
            controlPanelArray.add(controlViewArray);
            jsonObject.put(context.getString(R.string.control_panel_view), controlPanelArray);

            controlsObject = jsonObject;
            setStringSharedPreferences(context, SCREEN_FORMAT_ARRAY, jsonObject.toString());

        } else if (controlsObject.size() == 0) {

            JSONParser parser = new JSONParser();
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject = (JSONObject) parser.parse(json);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            controlsObject = jsonObject;
            current_view = context.getString(R.string.control_panel_view);
        }
    }
}
