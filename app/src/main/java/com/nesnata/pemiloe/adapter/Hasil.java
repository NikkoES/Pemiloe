package com.nesnata.pemiloe.adapter;

public class Hasil {

    private String mNomor;

    private String mNama;

    private String mSuara;

    private int mImageResourceId = NO_IMAGE_PROVIDED;

    private static final int NO_IMAGE_PROVIDED = -1;

    public Hasil(String nomor, String nama, String suara, int imageResourceId){
        mNomor = nomor;
        mNama = nama;
        mSuara = suara;
        mImageResourceId = imageResourceId;
    }


    public String getNomor(){
        return mNomor;
    }

    public String getNama(){
        return mNama;
    }

    public String getSuara(){
        return mSuara;
    }

    public int getImageResourceId(){
        return mImageResourceId;
    }

    public boolean hasImage(){
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }

}