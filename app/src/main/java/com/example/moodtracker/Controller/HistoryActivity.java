package com.example.moodtracker.Controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.moodtracker.Model.Mood;
import com.example.moodtracker.R;
import com.example.moodtracker.View.MyAdapter;


import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    private MyAdapter mAdapter;
    ArrayList<Mood> mMoodArrayList;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        //recovers saved ArrayList to Prefs class
        mMoodArrayList = Prefs.getInstance(this).getMoodArrayList();

        displayComment();

        buildRecyclerView();

    }

    // Method to manage the display of history
    private void displayComment() {
        //
        if (mMoodArrayList.size() > 0){mMoodArrayList.remove(mMoodArrayList.size() - 1);}

        if (mMoodArrayList.size() < 1){
            Toast.makeText(this, "Vous n'avez pas d'historique à afficher", Toast.LENGTH_LONG).show(); }
    }

    // Constructor of RecyclerView
    private void buildRecyclerView() {
        RecyclerView mRecyclerView = findViewById(R.id.recycler_view_history);
        mLayoutManager=new LinearLayoutManager(this);
        mAdapter=new MyAdapter(mMoodArrayList, this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

    }
}