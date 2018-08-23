package com.zammle2009wtfgmail.utilityhelper;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateAccount extends AppCompatActivity {


    private EditText email, password, reenterPassword;

    private FirebaseAuth firebaseAuth;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Button createAccount = findViewById(R.id.CreateAccountButton);
        email = findViewById(R.id.EmaileditText);
        password = findViewById(R.id.PasswordeditText);
        reenterPassword = findViewById(R.id.ReenterPasswordeditText);
        final TextView ShowPassword = findViewById(R.id.showPassword);
        final TextView ShowPassword2 = findViewById(R.id.showPassword2);
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();

        ShowPassword2.setVisibility(View.GONE);
        ShowPassword.setVisibility(View.GONE);
        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        reenterPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);


        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (password.getText().length() > 0){
                    ShowPassword.setVisibility(View.VISIBLE);
                }else{
                    ShowPassword.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        reenterPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (reenterPassword.getText().length() > 0){
                    ShowPassword2.setVisibility(View.VISIBLE);
                }else{
                    ShowPassword2.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ShowPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ShowPassword.getText() == "SHOW"){
                    ShowPassword.setText("HIDE");
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    password.setSelection(password.length());
                }else{
                    ShowPassword.setText("SHOW");
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    password.setSelection(password.length());
                }
            }
        });

        ShowPassword2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ShowPassword2.getText() == "SHOW"){
                    ShowPassword2.setText("HIDE");
                    reenterPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    reenterPassword.setSelection(password.length());
                }else{
                    ShowPassword2.setText("SHOW");
                    reenterPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    reenterPassword.setSelection(password.length());
                }
            }
        });

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SaveData();
            }
        });
    }

    //creating account with firebase auth
    public void SaveData() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
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
                                        //Toast.makeText(CreateAccount.this, "Account Successfully Created", Toast.LENGTH_SHORT).show();
                                        Intent changeActivity = new Intent(CreateAccount.this, MainActivity.class);
                                        startActivity(changeActivity);
                                    } else {
                                        //Toast.makeText(CreateAccount.this, "Could Not Create Account, Please Try Again!", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
        } else {
            progressDialog.dismiss();
            Toast.makeText(CreateAccount.this, "Passwords Don't Match!", Toast.LENGTH_SHORT).show();

        }

    }

}
