package com.nesnata.pemiloe.adapter;

/**
 * Created by Nikko Eka Saputra on 27/09/2017.
 */

public class Kandidat {

    private String mNomerKandidat;

    private String mNamaKandidat;

    private int mImageResourceId = NO_IMAGE_PROVIDED;

    private static final int NO_IMAGE_PROVIDED = -1;

    public Kandidat(String nomerKandidat, String namaKandidat, int imageResourceId){
        mNomerKandidat = nomerKandidat;
        mNamaKandidat = namaKandidat;
        mImageResourceId = imageResourceId;
    }


    public String getNomerKandidat(){
        return mNomerKandidat;
    }

    public String getNamaKandidat(){
        return mNamaKandidat;
    }

    public int getImageResourceId(){
        return mImageResourceId;
    }

    public boolean hasImage(){
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }

}