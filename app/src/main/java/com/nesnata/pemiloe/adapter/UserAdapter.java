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

public class UserAdapter extends ArrayAdapter<User> {

    public UserAdapter(Activity context, ArrayList<User> user) {
        super(context, 0, user);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item_user, parent, false);
        }

        //membuat objek yg memanggil class word
        User current = getItem(position);

        TextView nisUser = (TextView) listItemView.findViewById(R.id.nis);
        nisUser.setText(current.getNisUser());

        TextView statusUser = (TextView) listItemView.findViewById(R.id.status);
        statusUser.setText(current.getStatusUser());

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
