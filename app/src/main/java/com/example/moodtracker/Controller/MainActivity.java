package com.example.moodtracker.Controller;


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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.moodtracker.Model.Mood;
import com.example.moodtracker.Model.MoodSaveHelper;
import com.example.moodtracker.R;

import java.security.AccessController;
import java.util.ArrayList;
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

        initVars();
        initListener();
        initMoodsList();

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

    private void showBoxDialog() {
        AlertDialog.Builder alert=new AlertDialog.Builder(this);

        final EditText edittext=new EditText(this);

        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        edittext.setLayoutParams(lp);

        alert.setMessage("Entrer votre commentaire");
        alert.setTitle("COMMENTAIRE");

        alert.setView(edittext);

        alert.setPositiveButton("Valider", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Prefs.getInstance(MainActivity.this);
                mComment=edittext.getText().toString();
                moodList.get(counter).setComment(mComment);
                Mood test=moodList.get(counter);
            }
        });

        alert.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
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
        moodList.add(new Mood(R.drawable.super_happy, R.color.banana_yellow, 0, mComment));
        moodList.add(new Mood(R.drawable.happy, R.color.light_sage, 1, mComment));
        moodList.add(new Mood(R.drawable.normal, R.color.cornflower_blue_65, 2, mComment));
        moodList.add(new Mood(R.drawable.disappointed, R.color.warm_grey, 3, mComment));
        moodList.add(new Mood(R.drawable.sad, R.color.faded_red, 4, mComment));

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
        ArrayList<Mood> list=Prefs.getInstance(this).getMoodArrayList();
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