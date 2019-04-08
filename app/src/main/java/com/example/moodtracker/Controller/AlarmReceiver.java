package com.example.moodtracker.Controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.moodtracker.Model.SaveHelper;

//receive an intent from the method alarmMidnight to call SaveHelper
public class AlarmReceiver extends BroadcastReceiver {

    SaveHelper mSaveHelper;
    // when AlarmMidnight starts, this method calls the SaveMoodMidnight method
    @Override
    public void onReceive(Context context, Intent intent) {
        mSaveHelper.SaveMoodMidnight();
    }
}
