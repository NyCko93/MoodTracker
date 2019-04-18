package com.example.moodtracker.View;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moodtracker.Model.Mood;
import com.example.moodtracker.R;

import java.util.ArrayList;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private final Context mContext;
    private ArrayList<Mood> mMoodArrayList;
    private int sdk=android.os.Build.VERSION.SDK_INT;

    // MyAdapter takes as parameter the Arraylist mMoodArrayList and context to retrieve the content to display
    public MyAdapter(ArrayList<Mood> moods, Context context) {
        this.mMoodArrayList=moods;
        mContext=context;
    }

    // getItemCount returns the size of our object list, and thus tells the Adapter how many lines the RecyclerView can hold
    @Override
    public int getItemCount() {
        if (mMoodArrayList != null) {
            return mMoodArrayList.size();
        } else {
            return 0;
        }
    }

    // onCreateViewHolder allows us to create a ViewHolder from the XML layout representing each line of the RecyclerView
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        LayoutInflater inflater=LayoutInflater.from(parent.getContext());

        View view=inflater.inflate(R.layout.recycler_view_item, parent, false);

        return new MyViewHolder(view);

    }

    // onBindViewHolder is called for each of the visible lines displayed in our RecyclerView
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        // Display layout and resize
        holder.mainLayout.setLayoutParams(new RelativeLayout.LayoutParams((int) (80 * holder.mainLayout.getResources().getDisplayMetrics().density + mMoodArrayList.get(position).getId()
                * 83 * holder.mainLayout.getResources().getDisplayMetrics().density), ViewGroup.LayoutParams.WRAP_CONTENT));

        // Retrieve the final item in mMoodArrayList
        final Mood item=mMoodArrayList.get(position);

        // Management of the background
        int idDrawable=mMoodArrayList.get(position).getBackground();
        Drawable drawable=mContext.getResources().getDrawable(idDrawable);
        holder.mainLayout.setBackgroundDrawable(drawable);

        // If comment, visible button, click to display the text
        if (mMoodArrayList.get(position).getComment() != null) {
            holder.buttonComment.setVisibility(View.VISIBLE);
            final String comment=mMoodArrayList.get(position).getComment();

            holder.buttonComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, comment, Toast.LENGTH_LONG).show();
                }
            });
            //Invisible button if no text
        } else holder.buttonComment.setVisibility(View.INVISIBLE);

        //Display the day via getDays (yesterday, the day before yesterday ...)
        holder.textTv.setText(item.getDays(mMoodArrayList.size() - position));

    }


    // MyViewHolder represents a cell in my recyclerview
    class MyViewHolder extends RecyclerView.ViewHolder {

        // day (yesterday, before yesterday...)
        private final TextView textTv;
        // background
        final View mainLayout;
        // button comment
        ImageButton buttonComment;

        MyViewHolder(final View itemView) {

            super(itemView);

            mainLayout=itemView.findViewById(R.id.item_mood);

            textTv=itemView.findViewById(R.id.text_view);

            buttonComment=itemView.findViewById(R.id.btn_comment_item);

        }
    }
}
