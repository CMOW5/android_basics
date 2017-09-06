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
    private String mSuspect;

    public Crime(){
        this(UUID.randomUUID());
        /*
        'this()' statement is used in constructor chaining. If a class has more than
        one constructor and you want to execute all of them then you will use this()
        statement. Keep in mind this() must be first statement in constructor always.

        so the previous line call Crime(UUID id) constructor
         */
    }

    public Crime(UUID id){
        //generate unique identifier
        mId = id;
        mDate = new Date();
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

    public String getSuspect() {
        return mSuspect;
    }

    public void setSuspect(String suspect) {
        mSuspect = suspect;
    }
}
