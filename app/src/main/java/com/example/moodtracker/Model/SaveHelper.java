package com.example.moodtracker.Model;

import android.content.Context;

import com.example.moodtracker.Controller.MainActivity;
import com.example.moodtracker.Controller.Prefs;
import com.example.moodtracker.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SaveHelper {


    public SaveHelper() {
    }

    // Save the current mood, if no backup, we create an arraylist, if backup, we add and if complete, we replace the index 0
    public void SaveCurrentMood(Mood currentMood, MainActivity context) {
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

    // Retrieve the current date
    public Date getCurrentDate() {
        Date date;
        DateFormat outputFormatter = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        date = Calendar.getInstance().getTime();
        date = new Date(outputFormatter.format(date));
        return date;
    }

    // save current mood day, if 0 > new, if no mood selected > default mood, if >8 > remove index 0
    public void saveMoodMidnight(Context context) {

        Prefs prefs = Prefs.getInstance(context);
        ArrayList<Mood> prefsMoodArrayList = prefs.getMoodArrayList();
        if(prefsMoodArrayList == null){
            prefsMoodArrayList = new ArrayList<>();
        }
        if (prefsMoodArrayList.size() > 0 && (prefsMoodArrayList.get(prefsMoodArrayList.size()-1).getDate()) != getCurrentDate()) {
            Mood defaultMood = new Mood(R.drawable.happy, R.color.light_sage, 3, "", getCurrentDate());
            prefsMoodArrayList.add(defaultMood);
        }

        if (prefsMoodArrayList.size() > 8) {
            prefsMoodArrayList.remove(0);
        }

        prefs.saveMood(prefsMoodArrayList);
    }
}
