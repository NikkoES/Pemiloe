package com.nesnata.pemiloe.admin;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.nesnata.pemiloe.database.DatabaseHelper;
import com.nesnata.pemiloe.R;
import com.nesnata.pemiloe.adapter.Hasil;
import com.nesnata.pemiloe.adapter.HasilAdapter;

import java.util.ArrayList;

public class AdminHasilActivity extends AppCompatActivity {

    protected Cursor cursor, cursor2;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;

    String no, nama;

    int jumlah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil_pemilihan);

        dbHelper = new DatabaseHelper(this);

        db = dbHelper.getReadableDatabase();

        refreshList();

    }

    public void refreshList(){
        final ArrayList<Hasil> hasil = new ArrayList<Hasil>();

        cursor = db.rawQuery("SELECT * FROM TB_KANDIDAT", null);
        cursor.moveToFirst();
        for(int i=0;i<cursor.getCount();i++){
            cursor.moveToPosition(i);
            no = cursor.getString(0).toString();
            nama = cursor.getString(1).toString();
            jumlah = 0;
            cursor2 = db.rawQuery("SELECT * FROM TB_SUARA WHERE pilihan = '"+ no +"'", null);
            for(int j=0;j<cursor2.getCount();j++){
                jumlah = jumlah + 1;
            }
            hasil.add(new Hasil("No. "+no, nama, ""+jumlah,  R.drawable.profil_kandidat));
        }

        ListView listHasil = (ListView) findViewById(R.id.list_hasil);

        HasilAdapter arrayAdapter = new HasilAdapter(this, hasil);

        listHasil.setAdapter(arrayAdapter);
    }
}
