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
import com.nesnata.pemiloe.adapter.User;
import com.nesnata.pemiloe.adapter.UserAdapter;
import com.nesnata.pemiloe.database.DatabaseHelper;

import java.util.ArrayList;

public class AdminUserActivity extends AppCompatActivity {

    protected Cursor cursor, cursor2;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;

    EditText userNIS, userPassword;

    Intent i;

    String no, nis, status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user);

        dbHelper = new DatabaseHelper(this);

        db = dbHelper.getReadableDatabase();

        refreshList();

        i = new Intent(AdminUserActivity.this, AdminUserActivity.class);

        Button tambah = (Button) findViewById(R.id.tambah_user);
        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userNIS = new EditText(AdminUserActivity.this);
                userNIS.setHint("NIS");
                userNIS.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);

                userPassword = new EditText(AdminUserActivity.this);
                userPassword.setHint("Password");
                userPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

                LinearLayout ll = new LinearLayout(AdminUserActivity.this);
                ll.setPadding(16,4,16,4);
                ll.setOrientation(LinearLayout.VERTICAL);
                ll.addView(userNIS);
                ll.addView(userPassword);

                AlertDialog dialog = new AlertDialog.Builder(AdminUserActivity.this)
                        .setTitle("Add a new User")
                        .setView(ll)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String nis = userNIS.getText().toString();
                                String password = userPassword.getText().toString();
                                String level = "user";
                                db.execSQL("INSERT INTO TB_USER (username, password, level) VALUES ('" +
                                        nis + "','" +
                                        password + "','" +
                                        level + "');");
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
        final ArrayList<User> user = new ArrayList<User>();
        cursor = db.rawQuery("SELECT * FROM TB_USER WHERE LEVEL='user'", null);
        cursor.moveToFirst();
        for(int j=0;j<cursor.getCount();j++){
            cursor.moveToPosition(j);
            nis = cursor.getString(0).toString();
            cursor2 = db.rawQuery("SELECT * FROM TB_SUARA WHERE pemilih = '"+ nis +"'", null);
            if(cursor2.getCount()>0){
                status = "sudah memilih";
            }
            else{
                status = "belum memilih";
            }
            user.add(new User(nis, status, R.drawable.ic_launcher));
        }

        ListView listUser = (ListView) findViewById(R.id.list_user);

        UserAdapter arrayAdapter = new UserAdapter(this, user);
        listUser.setAdapter(arrayAdapter);
        listUser.setSelected(true);
        listUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final User selection = user.get(position);
                final String nis = selection.getNisUser();
                final CharSequence[] dialogitem = {"Lihat Data", "Hapus Data"};
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminUserActivity.this);
                builder.setTitle("Pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int item){
                        switch(item){
                            case 0 : {
                                Intent u = new Intent(AdminUserActivity.this, AdminDetailUserActivity.class);
                                u.putExtra("username", nis);
                                startActivity(u);
                                break;
                            }
                            case 1 : {
                                AlertDialog dialog2 = new AlertDialog.Builder(AdminUserActivity.this)
                                        .setTitle("Anda yakin ingin menghapus user "+ nis +" ? (Pilihan suara dari user juga akan terhapus !)")
                                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                db.execSQL("DELETE FROM TB_USER where username = '"+ nis +"'");
                                                db.execSQL("DELETE FROM TB_SUARA where pemilih = '"+ nis +"'");
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
        ((ArrayAdapter) listUser.getAdapter()).notifyDataSetInvalidated();
    }
}
