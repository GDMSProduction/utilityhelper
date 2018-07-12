package com.zammle2009wtfgmail.utilityhelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class LoginScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

       final Button login = (Button) findViewById(R.id.LoginButton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changeActivity = new Intent(LoginScreen.this, MainActivity.class);

                startActivity(changeActivity);


            }
        });






        final Button createAccount = (Button) findViewById(R.id.createaccountButton);
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changeActivity = new Intent(LoginScreen.this, CreateAccount.class);

                startActivity(changeActivity);
            }
        });


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }
}
