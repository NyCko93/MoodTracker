package com.example.moodtracker.Controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.moodtracker.Model.MoodSaveHelper;

public class AlarmReceiver extends BroadcastReceiver {

    MoodSaveHelper mMoodSaveHelper;
    //when AlarmMidnight start, this method call method SaveMoodMidnight
    @Override
    public void onReceive(Context context, Intent intent) {
        mMoodSaveHelper.SaveMoodMidnight();
    }
}
