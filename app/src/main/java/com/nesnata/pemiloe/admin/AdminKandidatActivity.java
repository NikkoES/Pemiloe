package com.nesnata.pemiloe.admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.nesnata.pemiloe.R;
import com.nesnata.pemiloe.adapter.Kandidat;
import com.nesnata.pemiloe.adapter.KandidatAdapter;
import com.nesnata.pemiloe.database.DatabaseHelper;

import java.util.ArrayList;

public class AdminKandidatActivity extends AppCompatActivity {

    protected Cursor cursor;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;

    EditText noKandidat, namaKandidat, visiKandidat, misiKandidat;

    Intent i;

    String no, nama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_kandidat);

        dbHelper = new DatabaseHelper(this);

        db = dbHelper.getReadableDatabase();

        refreshList();

        i = new Intent(AdminKandidatActivity.this, AdminKandidatActivity.class);

        Button tambah = (Button) findViewById(R.id.tambah_kandidat);

        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noKandidat = new EditText(AdminKandidatActivity.this);
                noKandidat.setHint("No. Urut");
                noKandidat.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);

                namaKandidat = new EditText(AdminKandidatActivity.this);
                namaKandidat.setHint("Nama Kandidat");
                namaKandidat.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);

                visiKandidat = new EditText(AdminKandidatActivity.this);
                visiKandidat.setHint("Visi");
                visiKandidat.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);

                misiKandidat = new EditText(AdminKandidatActivity.this);
                misiKandidat.setHint("Misi");
                misiKandidat.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);

                LinearLayout ll = new LinearLayout(AdminKandidatActivity.this);
                ll.setPadding(16,4,16,4);
                ll.setOrientation(LinearLayout.VERTICAL);
                ll.addView(noKandidat);
                ll.addView(namaKandidat);
                ll.addView(visiKandidat);
                ll.addView(misiKandidat);

                AlertDialog dialog = new AlertDialog.Builder(AdminKandidatActivity.this)
                        .setTitle("Add a new Candidate")
                        .setView(ll)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String no_urut = noKandidat.getText().toString();
                                String nama = namaKandidat.getText().toString();
                                String visi = visiKandidat.getText().toString();
                                String misi = misiKandidat.getText().toString();
                                db.execSQL("INSERT INTO TB_KANDIDAT (no_urut, nama, visi, misi) VALUES ('" +
                                        no_urut + "','" +
                                        nama + "','" +
                                        visi + "','" +
                                        misi + "');");
                                finish();
                                startActivity(i);
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();
            }
        });
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

        ListView listKandidat = (ListView) findViewById(R.id.list_kandidat);

        KandidatAdapter arrayAdapter = new KandidatAdapter(this, kandidat);
        listKandidat.setAdapter(arrayAdapter);
        listKandidat.setSelected(true);
        listKandidat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Kandidat selection = kandidat.get(position);
                final String nama = selection.getNamaKandidat();
                final CharSequence[] dialogitem = {"Lihat Data", "Hapus Data"};
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminKandidatActivity.this);
                builder.setTitle("Pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int item){
                        switch(item){
                            case 0 : {
                                Intent u = new Intent(AdminKandidatActivity.this, AdminDetailKandidatActivity.class);
                                u.putExtra("nama", nama);
                                startActivity(u);
                                break;
                            }
                            case 1 : {
                                AlertDialog dialog2 = new AlertDialog.Builder(AdminKandidatActivity.this)
                                        .setTitle("Anda yakin ingin menghapus kandidat nomor "+ no +" ?")
                                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                db.execSQL("DELETE FROM TB_KANDIDAT where nama = '"+ nama +"'");
                                                finish();
                                                startActivity(i);
                                            }
                                        })
                                        .setNegativeButton("Tidak", null)
                                        .create();
                                dialog2.show();
                                break;
                            }
                        }
                    }
                });
                builder.create().show();
            }
        });
        ((ArrayAdapter) listKandidat.getAdapter()).notifyDataSetInvalidated();
    }
}