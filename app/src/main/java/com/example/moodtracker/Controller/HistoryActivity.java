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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        //recovers saved ArrayList to Prefs class
        mMoodArrayList = Prefs.getInstance(this).getMoodArrayList();

        //To don't display current mood
        if (mMoodArrayList.size() > 0){mMoodArrayList.remove(mMoodArrayList.size() - 1);}

        //if historical don't exist, toast show "You have no history"
        if (mMoodArrayList.size() < 1){
            Toast.makeText(this, "Vous n'avez pas encore d'historique", Toast.LENGTH_LONG).show(); }

        //instantiation of recyclerView and use a linearLayoutManager
        RecyclerView recyclerView = findViewById(R.id.recycler_view_history);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //give the list to adapter to display items on it
        mAdapter=new MyAdapter(mMoodArrayList, this);
        recyclerView.setAdapter(mAdapter);

    }
}
