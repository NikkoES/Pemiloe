package com.nesnata.pemiloe.admin;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.nesnata.pemiloe.database.DatabaseHelper;
import com.nesnata.pemiloe.R;

public class AdminDetailKandidatActivity extends AppCompatActivity {

    protected Cursor cursor;
    DatabaseHelper dbHelper;

    String no, nama, visi, misi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kandidat);

        dbHelper = new DatabaseHelper(this);

        nama = getIntent().getStringExtra("nama");

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM TB_KANDIDAT WHERE nama = '"+ nama +"'", null);
        cursor.moveToFirst();
        if(cursor.getCount()>0){
            cursor.moveToPosition(0);
            no = cursor.getString(0).toString();
            nama = cursor.getString(1).toString();
            visi = cursor.getString(2).toString();
            misi = cursor.getString(3).toString();
        }

        TextView txtNo = (TextView) findViewById(R.id.no_urut_text_view);
        TextView txtNama = (TextView) findViewById(R.id.nama_text_view);
        TextView txtVisi = (TextView) findViewById(R.id.visi_text_view);
        TextView txtMisi = (TextView) findViewById(R.id.misi_text_view);

        txtNo.setText(no);
        txtNama.setText(nama);
        txtVisi.setText(visi);
        txtMisi.setText(misi);
    }
}