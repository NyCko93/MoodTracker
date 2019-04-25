package com.example.moodtracker.Controller;


import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
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
import com.example.moodtracker.Model.SaveHelper;
import com.example.moodtracker.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.example.moodtracker.R.raw.si;
import static com.example.moodtracker.R.raw.sol;
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
    private String mComment;
    SaveHelper mSaveHelper;
    MediaPlayer mMediaPlayerUp;
    MediaPlayer mMediaPlayerDown;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        counter=DEFAULT_MOOD_POSITION;
        mSaveHelper=new SaveHelper();
        initVars();
        initListener();
        initMoodsList();
        date=mSaveHelper.getCurrentDate();

        initAlarmManager(this);

        mMediaPlayerUp=MediaPlayer.create(this, sol);
        mMediaPlayerDown=MediaPlayer.create(this, si);

        mHistoryImage=findViewById(R.id.btn_history);

        mHistoryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // The user presses the button to access the history
                Intent HistoryActivityIntent=new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(HistoryActivityIntent);
            }
        });


        // Button comment
        mNoteImage=findViewById(R.id.btn_note);

        mNoteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBoxDialog();
            }
        });

    }

    // Dialog box to write a comment with the mood of the day
    private void showBoxDialog() {
        AlertDialog.Builder alert=new AlertDialog.Builder(this);

        final EditText edittext=new EditText(this);
        alert.setMessage("Entrer votre message");
        alert.setTitle("Commentaire");

        alert.setView(edittext);

        // For save the comment
        alert.setPositiveButton("Valider", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Prefs.getInstance(MainActivity.this);
                mComment=edittext.getText().toString();
                moodList.get(counter).setComment(mComment);
                mSaveHelper.saveCurrentMood(moodList.get(counter), MainActivity.this);
            }
        });

        // For cancel without save
        alert.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });

        alert.show();
    }

    // Initialization of the variables
    private void initVars() {
        mLayout=findViewById(R.id.activity_main1);

        mImageView=findViewById(R.id.smiley_img);

        mHistoryImage=findViewById(R.id.btn_history);

        mNoteImage=findViewById(R.id.btn_note);
    }

    // Initializing the listener to detect interractions
    @SuppressLint("ClickableViewAccessibility")
    private void initListener() {

        mDetector=new GestureDetector(MainActivity.this);
        mLayout.setOnTouchListener(this);
    }


    // Initialization of my arraylist with my moods
    private void initMoodsList() {
        moodList=new ArrayList<>();
        moodList.add(new Mood(R.drawable.super_happy, R.color.banana_yellow, 4, mComment, date));
        moodList.add(new Mood(R.drawable.happy, R.color.light_sage, 3, mComment, date));
        moodList.add(new Mood(R.drawable.normal, R.color.cornflower_blue_65, 2, mComment, date));
        moodList.add(new Mood(R.drawable.disappointed, R.color.warm_grey, 1, mComment, date));
        moodList.add(new Mood(R.drawable.sad, R.color.faded_red, 0, mComment, date));
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

    // This method detects the position of the finger and allows scroller and play sound
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        if (e1.getY() - e2.getY() > 30) {
            if (counter > 0) {
                counter--;
                updateView();
                mMediaPlayerUp.start();
            }
        }
        if (e2.getY() - e1.getY() > 30) {
            if (counter < moodList.size() - 1) {
                counter++;
                updateView();
                mMediaPlayerDown.start();
            }
        }
        return true;
    }

    private void updateView() {
        if ((counter >= 0) && (counter < moodList.size())) {
            mLayout.setBackgroundColor(getResources().getColor(moodList.get(counter).getBackground()));
            mImageView.setImageDrawable(getResources().getDrawable(moodList.get(counter).getSmiley()));
        }
    }

    // Save current mood
    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("MainActivity::onPause()");
        mSaveHelper.saveCurrentMood(moodList.get(counter), this);
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

    private void initAlarmManager(Context context) {
        AlarmManager alarmManager;
        PendingIntent pendingIntent;

        //Hour of save
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 50);

        //Call AlarmReceiver.class
        alarmManager=(AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent=new Intent(context, com.example.moodtracker.Controller.AlarmReceiver.class);
        pendingIntent=PendingIntent.getBroadcast(context, 0, intent, 0);

        //RTC-WAKEUP that will wake the device when it turns off.
        if (alarmManager != null) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }
}