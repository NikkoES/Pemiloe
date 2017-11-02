package com.nesnata.pemiloe.adapter;

/**
 * Created by Nikko Eka Saputra on 27/09/2017.
 */

public class User {

    private String mNIS;

    private String mStatus;

    private int mImageResourceId = NO_IMAGE_PROVIDED;

    private static final int NO_IMAGE_PROVIDED = -1;

    public User(String nis, String status, int imageResourceId){
        mNIS = nis;
        mStatus = status;
        mImageResourceId = imageResourceId;
    }


    public String getNisUser(){
        return mNIS;
    }

    public String getStatusUser(){
        return mStatus;
    }

    public int getImageResourceId(){
        return mImageResourceId;
    }

    public boolean hasImage(){
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }

}