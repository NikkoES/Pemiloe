package com.nesnata.pemiloe.admin;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.nesnata.pemiloe.R;
import com.nesnata.pemiloe.database.DatabaseHelper;

public class AdminDetailUserActivity extends AppCompatActivity {

    protected Cursor cursor;
    DatabaseHelper dbHelper;

    String username, password, level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_user);

        dbHelper = new DatabaseHelper(this);

        String nis = getIntent().getStringExtra("username");

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM TB_USER WHERE username = '"+ nis +"'", null);
        cursor.moveToFirst();
        if(cursor.getCount()>0){
            cursor.moveToPosition(0);
            username = cursor.getString(0).toString();
            password = cursor.getString(1).toString();
            level = cursor.getString(2).toString();

        }

        TextView lblName = (TextView) findViewById(R.id.lblName);
        TextView lblPassword = (TextView) findViewById(R.id.lblEmail);
        TextView lblLevel = (TextView) findViewById(R.id.lblLevel);

        // displaying user data
        lblName.setText(username);
        lblPassword.setText(password);
        lblLevel.setText(level);
    }
}
