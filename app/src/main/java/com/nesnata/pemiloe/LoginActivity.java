package com.nesnata.pemiloe;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nesnata.pemiloe.adapter.AlertDialogManager;
import com.nesnata.pemiloe.database.DatabaseHelper;
import com.nesnata.pemiloe.database.SessionManager;

import static com.nesnata.pemiloe.database.DatabaseHelper.COL_PASS;
import static com.nesnata.pemiloe.database.DatabaseHelper.COL_USER;
import static com.nesnata.pemiloe.database.DatabaseHelper.TABLE_USER;

public class LoginActivity extends AppCompatActivity {

    // Email, password edittext
    EditText textUsername, textPassword;

    // login button
    Button btnLogin;

    // Alert Dialog Manager
    AlertDialogManager alert = new AlertDialogManager();

    // Session Manager Class
    SessionManager session;

    DatabaseHelper dbUser;

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbUser = new DatabaseHelper(this);

        db = dbUser.getWritableDatabase();

        session = new SessionManager(getApplicationContext());

        textUsername = (EditText) findViewById(R.id.username);
        textPassword = (EditText) findViewById(R.id.password);

        btnLogin = (Button) findViewById(R.id.masuk);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = textUsername.getText().toString();
                String password = textPassword.getText().toString();
                String level = "null";

                try{
                    // Check if username, password is filled
                    if(username.trim().length() > 0 && password.trim().length() > 0){
                        dbUser.open();

                        if(dbUser.Login(username, password)){
                            final Cursor cursorLevel = db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE " + COL_USER + "=? AND " + COL_PASS + "=?", new String[]{username,password});
                            cursorLevel.moveToFirst();
                            if(cursorLevel.getCount()>0){
                                level = cursorLevel.getString(2).toString();
                            }

                            session.createLoginSession(username,level);

                            // Staring MainActivity
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i);
                            finish();

                        }
                        else{
                            // username / password doesn't match
                            alert.showAlertDialog(LoginActivity.this, "Login failed..", "Username/Password is incorrect", false);
                        }
                    }
                    else{
                        // user didn't entered username or password
                        // Show alert asking him to enter the details
                        alert.showAlertDialog(LoginActivity.this, "Login failed..", "Please enter username and password", false);
                    }
                }
                catch (Exception e){
                    Toast.makeText(LoginActivity.this,e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}
