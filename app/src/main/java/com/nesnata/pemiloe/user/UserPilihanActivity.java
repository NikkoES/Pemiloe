package com.nesnata.pemiloe.user;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.nesnata.pemiloe.R;
import com.nesnata.pemiloe.adapter.Kandidat;
import com.nesnata.pemiloe.adapter.KandidatAdapter;
import com.nesnata.pemiloe.database.DatabaseHelper;
import com.nesnata.pemiloe.database.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;

public class UserPilihanActivity extends AppCompatActivity {

    protected Cursor cursor;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;

    String no, nama;

    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_pilihan_user);

        dbHelper = new DatabaseHelper(this);

        db = dbHelper.getReadableDatabase();

        refreshList();

    }

    public void refreshList(){
        final ArrayList<Kandidat> kandidat = new ArrayList<Kandidat>();

        cursor = db.rawQuery("SELECT * FROM TB_KANDIDAT", null);
        cursor.moveToFirst();
        for(int j=0;j<cursor.getCount();j++){
            cursor.moveToPosition(j);
            no = cursor.getString(0).toString();
            nama = cursor.getString(1).toString();
            kandidat.add(new Kandidat("No. "+no, nama, R.drawable.profil_kandidat));
        }

        ListView listKandidat = (ListView) findViewById(R.id.list_pilihan_user);

        session = new SessionManager(getApplicationContext());
        final HashMap<String, String> user = session.getUserDetails();

        KandidatAdapter arrayAdapter = new KandidatAdapter(this, kandidat);
        listKandidat.setAdapter(arrayAdapter);
        listKandidat.setSelected(true);
        listKandidat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Kandidat selection = kandidat.get(position);
                final String selNo = selection.getNomerKandidat().substring(4,5);
                final String userName = user.get(SessionManager.KEY_NAME);

                AlertDialog dialog = new AlertDialog.Builder(UserPilihanActivity.this)
                        .setTitle("Anda yakin ingin memilih nomor "+ selNo +" ?\n(Anda tidak bisa mengganti pilihan !")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                db.execSQL("INSERT INTO TB_SUARA (pemilih, pilihan) VALUES ('" +
                                        userName + "','" +
                                        selNo + "');");
                                finish();
                                finish();
                                Intent i = new Intent(UserPilihanActivity.this, UserMainActivity.class);
                                startActivity(i);
                            }
                        })
                        .setNegativeButton("Tidak", null)
                        .create();
                dialog.show();
            }
        });
        ((ArrayAdapter) listKandidat.getAdapter()).notifyDataSetInvalidated();
    }
}