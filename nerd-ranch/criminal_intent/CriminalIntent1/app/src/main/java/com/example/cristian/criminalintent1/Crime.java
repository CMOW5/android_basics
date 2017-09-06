package com.example.cristian.criminalintent1;

import java.util.UUID;

/**
 * Created by root on 1/7/17.
 */

public class Crime {

    private UUID mId;
    private String mTitle;
    public Crime(){
        //generate unique identifier
        mId = UUID.randomUUID();

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


}
