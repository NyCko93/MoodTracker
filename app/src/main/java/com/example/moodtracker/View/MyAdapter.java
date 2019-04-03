package com.example.moodtracker.View;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.moodtracker.Model.Mood;

import java.util.ArrayList;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private ArrayList<Mood> mMoodArrayList;

    /**
     * MyAdapter takes as parameter the Arraylist mMoodArrayList in order to retrieve the content to display
     */
    public MyAdapter(ArrayList<Mood> parties) {
        mMoodArrayList= parties;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    /**
     * Method to display the contents of cells in my recyclerview
     */
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mMoodArrayList.size();
    }

    /**
     * Cell of my recyclerview
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
