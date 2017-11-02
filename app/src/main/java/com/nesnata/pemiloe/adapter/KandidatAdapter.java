package com.nesnata.pemiloe.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nesnata.pemiloe.R;

import java.util.ArrayList;

public class KandidatAdapter extends ArrayAdapter<Kandidat> {

    private int mColorResourceId;

    public KandidatAdapter(Activity context, ArrayList<Kandidat> kandidat) {
        super(context, 0, kandidat);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item_kandidat, parent, false);
        }

        //membuat objek yg memanggil class word
        Kandidat current = getItem(position);

        TextView nomerKandidat = (TextView) listItemView.findViewById(R.id.no_urut);
        nomerKandidat.setText(current.getNomerKandidat());

        TextView namaKandidat = (TextView) listItemView.findViewById(R.id.nama_kandidat);
        namaKandidat.setText(current.getNamaKandidat());

        ImageView imageIcon = (ImageView) listItemView.findViewById(R.id.image);

        if(current.hasImage()){
            imageIcon.setImageResource(current.getImageResourceId());
            imageIcon.setVisibility(View.VISIBLE);
        }
        else{
            imageIcon.setVisibility(View.GONE);
        }

        return listItemView;
    }

}
