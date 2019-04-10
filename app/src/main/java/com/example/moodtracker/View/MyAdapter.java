package com.example.moodtracker.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
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

    /**
     * MyAdapter prend en paramètre l'Arraylist mMoodArrayList et un contexte afin de récupérer le contenu à afficher
     */
    public MyAdapter(ArrayList<Mood> moods, Context context) {
        this.mMoodArrayList=moods;
        mContext=context;
    }

    /**
     * getItemCount permet de retourner la taille de notre liste d'objet, et ainsi indiquer à l'Adapter le nombre de lignes que peut contenir la RecyclerView.
     */
    @Override
    public int getItemCount() {
        if (mMoodArrayList != null) {
            return mMoodArrayList.size();
        } else {
            return 0;
        }
    }

    /**
     * onCreateViewHolder nous permet de créer un ViewHolder à partir du layout XML représentant chaque ligne de la RecyclerView. Celle-ci sera appelée pour les premières lignes visibles de la RecyclerView.
     * Pourquoi pas les autres ? Tout simplement car la RecyclerView possède un système permettant de réutiliser (ou recycler... ;) ) les ViewHolder déjà créés.
     * Il faut savoir que la création d'une vue sur Android est une action qui demande beaucoup de ressources.
     * Imaginez donc avoir 1000 lignes à afficher dans votre application : sans ce mécanisme de réutilisation, cette dernière souffrirait de ralentissements assez importants.
     */
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        LayoutInflater inflater=LayoutInflater.from(parent.getContext());

        View view=inflater.inflate(R.layout.recycler_view_item, parent, false);

        return new MyViewHolder(view);

    }

    /**
     * onBindViewHolder est appelée pour chacune des lignes visibles affichées dans notre RecyclerView.
     * C'est généralement ici que l'on met à jour leur apparence.
     * Dans notre cas nous appelons la méthode du ViewHolder que nous avons précédemment créée, afin de mettre à jour son TextView à partir d'un GithubUser.
     * D'ailleurs, nous avons grâce à la variable position, récupéré l'objet GithubUser correspondant dans notre liste d'objet.
     */
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        // Display layout and resize
        holder.mainLayout.setLayoutParams(new RelativeLayout.LayoutParams((int) (80 * holder.mainLayout.getResources().getDisplayMetrics().density + mMoodArrayList.get(position).getId()
                * 85 * holder.mainLayout.getResources().getDisplayMetrics().density), ViewGroup.LayoutParams.WRAP_CONTENT));

        // Retrieve the final item in mMoodArrayList
        final Mood item=mMoodArrayList.get(position);


        // Management of the background
        int idDrawable=mMoodArrayList.get(position).getBackground();
        Drawable drawable=mContext.getResources().getDrawable(idDrawable);
        holder.mainLayout.setBackgroundDrawable(drawable);
//        holder.buttonComment.setBackgroundDrawable(drawable);
//        holder.textTv.setBackgroundDrawable(drawable);

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

        // jour (hier, avant hier...)
        private final TextView textTv;
        // background
        final View mainLayout;
        // bouton commentaire
        ImageButton buttonComment;

        MyViewHolder(final View itemView) {

            super(itemView);

            mainLayout=itemView.findViewById(R.id.item_mood);

            textTv=itemView.findViewById(R.id.text_view);

            buttonComment=itemView.findViewById(R.id.btn_comment_item);

        }
    }
}
