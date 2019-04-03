package com.example.moodtracker.Controller;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.moodtracker.Model.Mood;
import com.example.moodtracker.Model.MoodSaveHelper;
import com.example.moodtracker.R;

import java.security.AccessController;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static java.lang.System.out;


public class MainActivity extends AppCompatActivity implements OnGestureListener, View.OnTouchListener {
    private static final int DEFAULT_MOOD_POSITION=1;
    private ArrayList<Mood> moodList;
    private static int counter;
    private GestureDetector mDetector;
    private RelativeLayout mLayout;
    Date date;
    private ImageView mImageView;
    private ImageView mHistoryImage;
    private ImageView mNoteImage;
    private AccessController view;
    private String mComment;
    MoodSaveHelper mMoodSaveHelper;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        counter=DEFAULT_MOOD_POSITION;
        mMoodSaveHelper = new MoodSaveHelper(this);
        initVars();
        initListener();
        initMoodsList();
        date = mMoodSaveHelper.getCurrentDate();

        AlarmMidnight(this);

        /**
         * Button return on HistoryActivity
         */
        mHistoryImage=(ImageView) findViewById(R.id.btn_history);

        mHistoryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * L'utilisateur clique pour retourner à l'accueil
                 */
                Intent MainActivityIntent=new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(MainActivityIntent);

            }
        });

        /**
         * Button comment
          */
        mNoteImage=(ImageView) findViewById(R.id.btn_note);

        mNoteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBoxDialog();
            }
        });

    }

    private void AlarmMidnight(MainActivity mainActivity) {
        AlarmManager alarmManager;
        PendingIntent pendingIntent;

        //in a current date at midnight, this property get an instance to calendar
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.DATE, 1);

        //call AlarmReceiver class
        alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        //RTC-WAKEUP that will wake the device when it turns off.
        if (alarmManager != null) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }

        //Resetting the mood list comment, and assigning the default smiley
        for(int i = 0; i<moodList.size(); i++)
        {
            moodList.get(i).setComment(null);
        }
        counter = 1;

    }

    private void showBoxDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        final EditText edittext = new EditText(this);
        alert.setMessage("Enter Your Message");
        alert.setTitle("Enter Your Title");

        alert.setView(edittext);

        alert.setPositiveButton("Yes Option", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });

        alert.setNegativeButton("No Option", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
            }
        });

        alert.show();
    }

    /**
     * Initialization of the variables
     */
    private void initVars() {
        mLayout=findViewById(R.id.activity_main1);

        mImageView=findViewById(R.id.smiley_img);

        mHistoryImage=findViewById(R.id.btn_history);

        mNoteImage=findViewById(R.id.btn_note);
    }

    /**
     * Initializing the listener to detect interractions
     */
    private void initListener() {

        mDetector=new GestureDetector(MainActivity.this);
        mLayout.setOnTouchListener(this);
        mHistoryImage.setOnTouchListener(this);
        mNoteImage.setOnTouchListener(this);

    }

    /**
     * Initialization of my arraylist with my moods
     */
    private void initMoodsList() {

        moodList=new ArrayList<>();
        moodList.add(new Mood(R.drawable.super_happy, R.color.banana_yellow, 0,mComment));
        moodList.add(new Mood(R.drawable.happy, R.color.light_sage, 1,mComment));
        moodList.add(new Mood(R.drawable.normal, R.color.cornflower_blue_65, 2,mComment));
        moodList.add(new Mood(R.drawable.disappointed, R.color.warm_grey, 3,mComment));
        moodList.add(new Mood(R.drawable.sad, R.color.faded_red, 4,mComment));

    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
    }

    /**
     * This method detects the position of the finger and allows scroller
     */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (e1.getY() - e2.getY() > 30) {
            if (counter > 0) {
                counter--;
            }
        }
        if (e2.getY() - e1.getY() > 30) {
            if (counter < moodList.size() - 1) {
                counter++;
            }
        }
        updateView();
        return true;
    }

    private void updateView() {
        mLayout.setBackgroundColor(getResources().getColor(moodList.get(counter).getBackground()));
        mImageView.setImageDrawable(getResources().getDrawable(moodList.get(counter).getSmiley()));
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("MainActivity::onPause()");
        moodList.get(counter).setDate(new Date());
        ArrayList<Mood> list = Prefs.getInstance(this).getMoodArrayList();
        list.add(moodList.get(counter));
        Prefs.getInstance(this).saveMood(list);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {

        v.performClick();
        return mDetector.onTouchEvent(event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        out.println("MainActivity::onDestroy()");
    }
}