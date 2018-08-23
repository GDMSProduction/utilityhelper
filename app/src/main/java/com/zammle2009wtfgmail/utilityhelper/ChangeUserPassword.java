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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangeUserPassword extends AppCompatActivity {

    private EditText password, reenterPassword;
    private TextView ShowPassword, ShowPassword2;

    private FirebaseAuth firebaseAuth;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_user_password);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        firebaseAuth = FirebaseAuth.getInstance();
        Button changePassword = findViewById(R.id.Change);
        password = findViewById(R.id.PasswordeditText);
        ShowPassword = findViewById(R.id.showPassword);
        ShowPassword2 = findViewById(R.id.showPassword2);
        reenterPassword = findViewById(R.id.ReenterPasswordeditText);
        progressDialog = new ProgressDialog(this);

        ShowPassword2.setVisibility(View.GONE);
        ShowPassword.setVisibility(View.GONE);
        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        reenterPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangePassword();
            }
        });

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
    }

//Changes the password you signed up with
    public void ChangePassword() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String Password = password.getText().toString().trim();
        String ReenterPassword = reenterPassword.getText().toString().trim();

        if (TextUtils.isEmpty(Password)) {
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(ReenterPassword)) {
            Toast.makeText(this, "Please Re-enter Password", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Changing Password....");
        progressDialog.show();
        if (user != null) {
            if (Password.equals(ReenterPassword)) {
                user.updatePassword(password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                progressDialog.dismiss();
                                if (task.isSuccessful()){
                                    //Toast.makeText(ChangeUserPassword.this, "Password Successfully Changed", Toast.LENGTH_SHORT).show();
                                    //sign out with the email and password you created
                                    firebaseAuth.signOut();
                                    finish();
                                    Intent changeActivity = new Intent(ChangeUserPassword.this, LoginScreen.class);

                                    startActivity(changeActivity);
                                }
                                else{
                                    Toast.makeText(ChangeUserPassword.this, "Password not Successfully Changed", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

            } else {
                progressDialog.dismiss();
                Toast.makeText(ChangeUserPassword.this, "Passwords Don't Match!", Toast.LENGTH_SHORT).show();

            }
        }


    }
}
