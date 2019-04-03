package com.example.moodtracker.Model;

import android.content.Context;

import com.example.moodtracker.Controller.MainActivity;
import com.example.moodtracker.Controller.Prefs;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MoodSaveHelper {
    private Context context;

    public MoodSaveHelper(MainActivity mainActivity) {
        this.context = context;
    }

    public void SaveCurrentMood(Mood currentMood) {
        currentMood.setDate(getCurrentDate());
        Prefs prefs = Prefs.getInstance(context);
        ArrayList<Mood> prefsMoodArrayList = prefs.getMoodArrayList();
        if(prefsMoodArrayList == null){
            prefsMoodArrayList = new ArrayList<>();
        }
        if (prefsMoodArrayList.size() > 0 && (prefsMoodArrayList.get(prefsMoodArrayList.size()-1).getDate()).equals(getCurrentDate())) {
            prefsMoodArrayList.remove(prefsMoodArrayList.size() - 1);
        }
        prefsMoodArrayList.add(currentMood);

        if (prefsMoodArrayList.size() > 8) {
            prefsMoodArrayList.remove(0);
            System.out.println(prefsMoodArrayList.size());
        }

        prefs.saveMood(prefsMoodArrayList);
    }

    public Date getCurrentDate() {
        Date date;
        DateFormat outputFormatter = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        date = Calendar.getInstance().getTime();
        date = new Date(outputFormatter.format(date));
        return date;
    }

    public void SaveMoodMidnight() {
    }
}
