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

public class HasilAdapter extends ArrayAdapter<Hasil> {

    public HasilAdapter(Activity context, ArrayList<Hasil> hasil) {
        super(context, 0, hasil);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item_hasil, parent, false);
        }

        //membuat objek yg memanggil class word
        Hasil current = getItem(position);

        TextView nomerKandidat = (TextView) listItemView.findViewById(R.id.nomor);
        nomerKandidat.setText(current.getNomor());

        TextView namaKandidat = (TextView) listItemView.findViewById(R.id.nama);
        namaKandidat.setText(current.getNama());

        TextView suaraKandidat = (TextView) listItemView.findViewById(R.id.suara);
        suaraKandidat.setText(current.getSuara());

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