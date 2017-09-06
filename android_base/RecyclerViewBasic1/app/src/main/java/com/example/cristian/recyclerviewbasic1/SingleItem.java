package com.example.cristian.recyclerviewbasic1;

import java.util.UUID;

/**
 * Created by cristian on 21/05/17.
 */

public class SingleItem {

    private String name;
    private UUID mId;

    public SingleItem() {
        //generate unique identifier
        mId = UUID.randomUUID();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId() {
        return mId;
    }

    public void setId(UUID mId) {
        this.mId = mId;
    }
}
