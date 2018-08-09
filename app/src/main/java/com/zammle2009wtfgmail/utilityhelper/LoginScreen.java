package com.zammle2009wtfgmail.utilityhelper;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginScreen extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button login;
    private Button createAccount;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

       login = findViewById(R.id.LoginButton);
       createAccount = findViewById(R.id.createaccountButton);
       email = findViewById(R.id.EmaileditText);
       password = findViewById(R.id.PasswordeditText);
       progressDialog = new ProgressDialog(this);
       firebaseAuth = FirebaseAuth.getInstance();

       login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changeActivity = new Intent(LoginScreen.this, CreateAccount.class);

                startActivity(changeActivity);
            }
        });

    }

    private void userLogin(){
        String Email = email.getText().toString().trim();
        String Password = password.getText().toString().trim();

        if(TextUtils.isEmpty(Email)){
            Toast.makeText(this,"Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(Password)){
            Toast.makeText(this,"Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Logging In....");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if(task.isSuccessful()){
                            Toast.makeText(LoginScreen.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            Intent changeActivity = new Intent(LoginScreen.this, MainActivity.class);

                            startActivity(changeActivity);
                        }
                        else {
                            Toast.makeText(LoginScreen.this, "Please enter the correct email and password!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
