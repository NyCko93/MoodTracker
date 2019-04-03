package com.example.moodtracker.View;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moodtracker.Controller.MainActivity;
import com.example.moodtracker.Model.Mood;
import com.example.moodtracker.R;

import java.util.ArrayList;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private  final Context mContext;
    private ArrayList<Mood> mMoodArrayList;

    // MyAdapter takes as parameter the Arraylist mMoodArrayList and a context in order to retrieve the content to display
    public MyAdapter(ArrayList<Mood> moods, Context context) {
        this.mMoodArrayList=moods;
        mContext = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.recycler_view_item, parent, false);
        return new MyViewHolder(view);
    }

    // Method to display the contents of cells in my recyclerview
    @RequiresApi(api=Build.VERSION_CODES.JELLY_BEAN)
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.mainLayout.setLayoutParams(new RelativeLayout.LayoutParams(230 + mMoodArrayList.get(position).getId() * 215,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        //call ArrayList items position to display them
        final Mood item = mMoodArrayList.get(position);

        //put background colors items
        int idDrawable = mMoodArrayList.get(position).getBackground();
        Drawable drawable = mContext.getResources().getDrawable(idDrawable);
        holder.textTv.setBackground(drawable);
        holder.buttonComment.setBackground(drawable);

        // put week days on textView
        holder.textTv.setText(item.getDays(mMoodArrayList.size() - position));

        //If there is a comment, the button is visible and you can click on it to see the text
        if (mMoodArrayList.get(position).getComment() != null){
            holder.buttonComment.setVisibility(View.VISIBLE);
            final String comment = mMoodArrayList.get(position).getComment();

            holder.buttonComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, comment, Toast.LENGTH_LONG).show();
                }
            });
        // Button comment is invisible if no text
        }else holder.buttonComment.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        if (mMoodArrayList != null) {
            return mMoodArrayList.size();
        } else {
            return 0;
        }
    }


    // Cell of my recyclerview
    public class MyViewHolder extends RecyclerView.ViewHolder {


        private final TextView textTv;
        public final View mainLayout;
        public ImageButton buttonComment;

        public MyViewHolder(final View itemView) {

            super(itemView);

            mainLayout = itemView;
            textTv = itemView.findViewById(R.id.text_view);
            buttonComment = itemView.findViewById(R.id.btn_comment_item);

        }
    }
}
