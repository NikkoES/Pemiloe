package com.nesnata.pemiloe.user;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nesnata.pemiloe.R;
import com.nesnata.pemiloe.database.DatabaseHelper;
import com.nesnata.pemiloe.database.SessionManager;

import java.util.HashMap;

public class UserMainActivity extends AppCompatActivity {

    protected Cursor cursor, cursor2, cursor3;
    DatabaseHelper dbHelper;

    SessionManager session;

    String no, nama, akses;

    Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);

        session = new SessionManager(getApplicationContext());
        final HashMap<String, String> user = session.getUserDetails();

        dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM TB_SUARA WHERE pemilih = '"+ user.get(SessionManager.KEY_NAME) +"'", null);
        cursor.moveToFirst();
        if(cursor.getCount()>0){
            cursor.moveToPosition(0);
            no = cursor.getString(1).toString();
        }

        cursor2 = db.rawQuery("SELECT * FROM TB_KANDIDAT WHERE no_urut = '"+ no +"'", null);
        cursor2.moveToFirst();
        if(cursor2.getCount()>0){
            cursor2.moveToPosition(0);
            nama = cursor2.getString(1).toString();
        }

        cursor3 = db.rawQuery("SELECT * FROM TB_AKSES", null);
        cursor3.moveToFirst();
        if(cursor3.getCount()>0){
            cursor3.moveToPosition(0);
            akses = cursor3.getString(0).toString();
        }
        else{
            akses = "ditutup";
        }

        TextView txtPilihan = (TextView) findViewById(R.id.pilihan);
        if(no==null){
            txtPilihan.setText("Anda belum memilih");
        }
        else{
            txtPilihan.setText("Anda memilih : \n\n"+ nama +"\nNomor "+ no);
        }

        btnLogout = (Button) findViewById(R.id.keluar);

        btnLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                AlertDialog dialog = new AlertDialog.Builder(UserMainActivity.this)
                        .setTitle("Anda yakin ingin keluar ?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                                session.logoutUser();
                            }
                        })
                        .setNegativeButton("Tidak", null)
                        .create();
                dialog.show();
            }
        });
    }

    public void profilKandidat(View v){
        Intent i = new Intent(this, UserKandidatActivity.class);
        startActivity(i);
    }

    public void pilihKandidat(View v){
        if(akses.equalsIgnoreCase("dibuka")){
            if(no==null){
                Intent i = new Intent(this, UserPilihanActivity.class);
                startActivity(i);
            }
            else{
                Toast.makeText(UserMainActivity.this,"Mohon maaf, anda sudah memilih", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(UserMainActivity.this,"Mohon maaf, akses pemilu belum dibuka", Toast.LENGTH_SHORT).show();
        }

    }
}
