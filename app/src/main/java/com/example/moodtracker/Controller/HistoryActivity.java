package com.example.moodtracker.Controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.moodtracker.Model.Mood;
import com.example.moodtracker.R;
import com.example.moodtracker.View.MyAdapter;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    ArrayList<Mood> mMoodArrayList;
    private Button mReturnButton;
    private RelativeLayout mRelativeLayout;
    private RecyclerView.LayoutManager mLayoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        buildRecyclerView();

        mMoodArrayList = Prefs.getInstance(this).getMoodArrayList(); // ArrayList History

    }

    private void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recycler_view_history);
        mLayoutManager = new LinearLayoutManager(this);

        mAdapter=new MyAdapter(mMoodArrayList, this);
    }
}
