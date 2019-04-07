package com.example.moodtracker.View;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
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
import java.util.Locale;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private final Context mContext;
    private ArrayList<Mood> mMoodArrayList;

    /**
     * MyAdapter prend en paramètre l'Arraylist mMoodArrayList et un contexte afin de récupérer le contenu à afficher
     */
    public MyAdapter(ArrayList<Mood> moods, Context context) {
        this.mMoodArrayList=moods;
        mContext=context;
    }

    /**
     * Cette méthode retourne l'item à afficher dans la/les cellules de mon recyclerview
     */
    @Override
    public int getItemCount() {
        if (mMoodArrayList != null) {
            return mMoodArrayList.size();
        } else {
            return 0;
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        LayoutInflater inflater=LayoutInflater.from(parent.getContext());

        View view=inflater.inflate(R.layout.recycler_view_item, parent, false);

        return new MyViewHolder(view);

    }

    /**
     * Méthode d'affichage du contenu des cellules dans mon  recyclerview
     */
    @RequiresApi(api=Build.VERSION_CODES.JELLY_BEAN)
    public void onBindViewHolder(MyViewHolder holder, int position) {

//        holder.textTv.setText(String.format(Locale.getDefault(), "%s: %d", mMoodArrayList.get(position).getDate(), mMoodArrayList.get(position).getBackground()));
        holder.mainLayout.setLayoutParams(new RelativeLayout.LayoutParams(230 + mMoodArrayList.get(position).getId() * 215,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        int idDrawable = mMoodArrayList.get(position).getBackground();
        Drawable drawable = mContext.getResources().getDrawable(idDrawable);
        holder.textTv.setBackground(drawable);
        holder.buttonComment.setBackground(drawable);

        /**
         * Si commentaire, bouton visible, on clique pour afficher le texte
         */
        if (mMoodArrayList.get(position).getComment() != null) {
            holder.buttonComment.setVisibility(View.VISIBLE);
            final String comment=mMoodArrayList.get(position).getComment();

            holder.buttonComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, comment, Toast.LENGTH_LONG).show();
                }
            });
            /**
             * Bouton invisible si pas de texte
             */
        } else holder.buttonComment.setVisibility(View.INVISIBLE);
    }


    /**
     * MyViewHolder représente une cellule de mon recyclerview
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {

        // jour (hier, avant hier...)
        private final TextView textTv;
        // background
        public final View mainLayout;
        // bouton commentaire
        public ImageButton buttonComment;

        public MyViewHolder(final View itemView) {

            super(itemView);


            mainLayout=itemView.findViewById(R.id.item_mood);

            textTv=itemView.findViewById(R.id.text_view);

            buttonComment=itemView.findViewById(R.id.btn_comment_item);

        }
    }
}
