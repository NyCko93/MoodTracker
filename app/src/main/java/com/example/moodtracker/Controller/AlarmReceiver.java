package com.example.moodtracker.Controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.moodtracker.Model.SaveHelper;

//receive an intent from the method alarmMidnight to call SaveMoodHelper
public class AlarmReceiver extends BroadcastReceiver {

    SaveHelper mSaveHelper;
    // au démarrage de AlarmMidnight, cette méthode appelle la méthode SaveMoodMidnight
    @Override
    public void onReceive(Context context, Intent intent) {
        mSaveHelper.SaveMoodMidnight();
    }
}
