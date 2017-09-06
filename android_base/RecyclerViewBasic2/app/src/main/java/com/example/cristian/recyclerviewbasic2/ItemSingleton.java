package com.example.cristian.recyclerviewbasic2;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by cristian on 21/05/17.
 */

public class ItemSingleton {

    private static ItemSingleton sItemSingleton;
    private List<SingleItem> mSingleItemList;

    public static ItemSingleton get(Context context){
        if(sItemSingleton == null){
            sItemSingleton = new ItemSingleton(context);
        }
        return sItemSingleton;
    }

    private ItemSingleton(Context context) { //constructo
        mSingleItemList = new ArrayList<>();
        //init array
        for (int i = 0; i < 100; i++){
            SingleItem item = new SingleItem();
            item.setTitle("item " + String.valueOf(i));
            mSingleItemList.add(item);
        }
    }

    public List<SingleItem> getList(){
        return mSingleItemList;
    }

    public SingleItem getItem(UUID id){
        for(SingleItem item : mSingleItemList){
            if (item.getId().equals(id)){
                return item;
            }
        }
        return null;
    }
}
