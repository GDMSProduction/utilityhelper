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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateAccount extends AppCompatActivity {


    private EditText email, password, reenterPassword;
    private Button createAccount;

    private FirebaseAuth firebaseAuth;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        createAccount = findViewById(R.id.CreateAccountButton);
        email = findViewById(R.id.EmaileditText);
        password = findViewById(R.id.PasswordeditText);
        reenterPassword = findViewById(R.id.ReenterPasswordeditText);
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SaveData();
            }
        });
    }

    //creating account with firebase auth
    public void SaveData() {
        String Email = email.getText().toString().trim();
        String Password = password.getText().toString().trim();
        String ReenterPassword = reenterPassword.getText().toString().trim();


        if (TextUtils.isEmpty(Email)) {
            Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(Password)) {
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(ReenterPassword)) {
            Toast.makeText(this, "Please Re-enter Password", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Creating Account....");
        progressDialog.show();
        if (Password.equals(ReenterPassword)) {
            firebaseAuth.createUserWithEmailAndPassword(Email, Password)
                    .addOnCompleteListener(this,
                            new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressDialog.dismiss();
                                    if (task.isSuccessful()) {
                                        Toast.makeText(CreateAccount.this, "Account Successfully Created", Toast.LENGTH_SHORT).show();
                                        Intent changeActivity = new Intent(CreateAccount.this, MainActivity.class);
                                        progressDialog.dismiss();

                                        startActivity(changeActivity);
                                    } else {
                                        Toast.makeText(CreateAccount.this, "Could Not Create Account, Please Try Again!", Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                    }

                                }
                            });
        }else{
            Toast.makeText(CreateAccount.this, "Passwords Don't Match!", Toast.LENGTH_SHORT).show();

        }

    }

}
