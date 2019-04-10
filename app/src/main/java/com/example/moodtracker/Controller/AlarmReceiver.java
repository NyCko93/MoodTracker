package com.example.moodtracker.Controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.moodtracker.Model.SaveHelper;

//receive an intent from the method alarmMidnight to call SaveHelper
public class AlarmReceiver extends BroadcastReceiver {


    // when AlarmMidnight starts, this method calls the saveMoodMidnight method
    @Override
    public void onReceive(Context context, Intent intent) {
        SaveHelper saveHelper = new SaveHelper();
        saveHelper.saveMoodMidnight(context);
    }
}
