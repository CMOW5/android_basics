package com.example.cristian.recyclerviewbasic2;

import java.util.Date;
import java.util.UUID;

/**
 * Created by root on 1/7/17.
 */

public class SingleItem {

    private UUID mId;
    private String mTitle;

    public SingleItem(){
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
