package com.nesnata.pemiloe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.nesnata.pemiloe.admin.AdminMainActivity;
import com.nesnata.pemiloe.database.SessionManager;
import com.nesnata.pemiloe.user.UserMainActivity;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    // Session Manager Class
    SessionManager session;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Session class instance
        session = new SessionManager(getApplicationContext());

        /**
         * Call this function whenever you want to check user login
         * This will redirect user to LoginActivity is he is not
         * logged in
         * */
        session.checkLogin();

        // get user data from session
        HashMap<String, String> user = session.getUserDetails();

        // level
        try{
            String level = user.get(SessionManager.KEY_LEVEL);

            finish();
            if(level.equalsIgnoreCase("User")){
                Intent i = new Intent(this, UserMainActivity.class);
                startActivity(i);
            }
            else if(level.equalsIgnoreCase("Admin")){
                Intent i = new Intent(this, AdminMainActivity.class);
                startActivity(i);
            }
        }
        catch (NullPointerException npe){
            finish();
        }

    }

}