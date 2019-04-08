package com.example.moodtracker.Controller;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.moodtracker.Model.Mood;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Prefs {


    private static Prefs instance;
    private static String PREFS="saveMood";
    private static final String MOOD="MOOD";
    private SharedPreferences prefs;

    private Prefs(Context context) {

        prefs=context.getSharedPreferences(PREFS, Activity.MODE_PRIVATE);

    }


    // Initialization of the backup. If no backup, we create one
    public static Prefs getInstance(Context context) {
        if (instance == null)
            instance=new Prefs(context);
        return instance;
    }


    public void saveMood(ArrayList<Mood> moodArrayList) {
        // Editing data to back up
        SharedPreferences.Editor editor=prefs.edit();
        //We store the data
        Gson gson=new Gson();
        String json=gson.toJson(moodArrayList);
        editor.putString(MOOD, json);
        // We close the file and then apply the edition
        editor.apply();
    }

    // Here we recover the saved data. If null, create a new list
    public ArrayList<Mood> getMoodArrayList() {
        Gson gson=new Gson();
        String json=prefs.getString(MOOD, "");

        ArrayList<Mood> moodArrayList;

        if ((json != null ? json.length() : 0) < 1) {
            moodArrayList=new ArrayList<>();
        } else {
            Type type=new TypeToken<ArrayList<Mood>>() {
            }.getType();
            moodArrayList=gson.fromJson(json, type);
        }
        return moodArrayList;
    }


}
