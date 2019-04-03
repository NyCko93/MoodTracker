package com.example.moodtracker.Model;

import org.w3c.dom.Comment;

import java.util.Date;

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

    public void getComment(String Comment){
        this.mComment = Comment;
    }
}
