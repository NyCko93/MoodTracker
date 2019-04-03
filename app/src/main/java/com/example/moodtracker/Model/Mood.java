package com.example.moodtracker.Model;

import java.util.Date;
import java.util.Hashtable;

public class Mood {

    private int mSmiley; // Smiley of mood
    private int mBackground; // Color of background
    private int mId; // Id
    private String mComment;

    private Date date;

    public Mood(int mSmiley, int mBackground, int Id, String mComment) {
        this.mSmiley = mSmiley;
        this.mBackground = mBackground;
        this.mId = Id;
        this.mComment = mComment;
    }


    public int getSmiley() {
        return mSmiley;
    }

    public int getBackground(){
        return mBackground;
    }

    public int getId() {
        return mId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setComment(String Comment) {
        this.mComment = Comment;
    }

    public String getDays(int days) {

        Hashtable<Integer, String> weekDays = new Hashtable<>();
        weekDays.put(7, "Il y a une semaine");
        weekDays.put(6, "Il y a 6 jours");
        weekDays.put(5, "Il y a 5 jours");
        weekDays.put(4, "Il y a 4 jours");
        weekDays.put(3, "Il y a 3 jours");
        weekDays.put(2, "Avant-hier");
        weekDays.put(1, "Hier");

        return weekDays.get(days);
    }

    public String getComment() {
        return null;
    }
}
