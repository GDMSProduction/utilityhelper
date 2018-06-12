package com.zammle2009wtfgmail.utilityhelper;

import android.content.Intent;
import android.icu.text.IDNA;
import android.os.UserHandle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CreateAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);


        Button back = (Button)  findViewById(R.id.BackButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backButton = new Intent(CreateAccount.this, LoginScreen.class);

                startActivity(backButton);
            }
        });

        final Button createAccount = (Button) findViewById(R.id.CreateAccountButton);
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent accountButton = new Intent(CreateAccount.this, LoginScreen.class);

                startActivity(accountButton);
            }
        });
    }

}
