package com.nesnata.pemiloe.admin;

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

import com.nesnata.pemiloe.R;
import com.nesnata.pemiloe.database.DatabaseHelper;

public class AdminMulaiActivity extends AppCompatActivity {

    protected Cursor cursor, cursor2;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;

    Intent i;

    String akses, tombol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_mulai);

        dbHelper = new DatabaseHelper(this);

        db = dbHelper.getReadableDatabase();

        i = new Intent(AdminMulaiActivity.this, AdminMulaiActivity.class);

        Button mulai = (Button) findViewById(R.id.btn_akses);
        TextView status = (TextView) findViewById(R.id.akses);

        cursor = db.rawQuery("SELECT * FROM TB_AKSES", null);
        cursor.moveToFirst();
        if(cursor.getCount()>0){
            cursor.moveToPosition(0);
            akses = cursor.getString(0).toString();
            tombol = "Tutup Akses";
        }
        else{
            akses = "ditutup";
            tombol = "Buka Akses";
        }

        mulai.setText(tombol);
        status.setText(akses);

        mulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(akses.equalsIgnoreCase("ditutup")){
                    db.execSQL("INSERT INTO TB_AKSES (status) VALUES ('dibuka');");
                    finish();
                    startActivity(i);
                }
                else if(akses.equalsIgnoreCase("dibuka")){
                    AlertDialog dialog2 = new AlertDialog.Builder(AdminMulaiActivity.this)
                            .setTitle("Anda yakin ingin menutup akses pemilu ?")
                            .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    db.execSQL("DELETE FROM TB_AKSES");
                                    finish();
                                    startActivity(i);
                                }
                            })
                            .setNegativeButton("Tidak", null)
                            .create();
                    dialog2.show();
                }
            }
        });
    }
}