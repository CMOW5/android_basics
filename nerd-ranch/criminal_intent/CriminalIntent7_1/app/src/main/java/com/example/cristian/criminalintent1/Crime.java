package com.example.cristian.criminalintent1;

import java.util.Date;
import java.util.UUID;

/**
 * Created by root on 1/7/17.
 */

public class Crime {

    private UUID mId;
    private String mTitle;
    private boolean mSolved;
    private Date mDate;


    public Crime(){
        //generate unique identifier
        mId = UUID.randomUUID();
        mDate = new Date(); //sets mDate to the current date
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }
}
