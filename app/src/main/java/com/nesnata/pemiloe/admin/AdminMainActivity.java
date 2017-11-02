package com.nesnata.pemiloe.admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.nesnata.pemiloe.R;
import com.nesnata.pemiloe.database.SessionManager;

public class AdminMainActivity extends AppCompatActivity {

    SessionManager session;

    Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        session = new SessionManager(getApplicationContext());

        btnLogout = (Button) findViewById(R.id.keluar);
        btnLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                AlertDialog dialog = new AlertDialog.Builder(AdminMainActivity.this)
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

    public void kelolaUser(View v){
        Intent i = new Intent(this, AdminUserActivity.class);
        startActivity(i);
    }

    public void kelolaKandidat(View v){
        Intent i = new Intent(this, AdminKandidatActivity.class);
        startActivity(i);
    }

    public void mulaiPemilu(View v){
        Intent i = new Intent(this, AdminMulaiActivity.class);
        startActivity(i);
    }

    public void hasilPemilu(View v){
        Intent i = new Intent(this, AdminHasilActivity.class);
        startActivity(i);
    }
}