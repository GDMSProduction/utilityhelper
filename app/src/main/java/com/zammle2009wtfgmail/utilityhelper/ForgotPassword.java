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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ForgotPassword extends AppCompatActivity {
    private EditText email;
    private Button resetPassword;

    private FirebaseAuth firebaseAuth;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        Hardware_Spec.timer = 601;
        firebaseAuth = FirebaseAuth.getInstance();
        resetPassword = findViewById(R.id.resetpasswordButton);

        email = findViewById(R.id.EmaileditText);
        progressDialog = new ProgressDialog(this);

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Forgotpassword();
            }
        });

    }

    void Forgotpassword() {
        String Email = email.getText().toString().trim();

        if (Email.isEmpty()){
            Toast.makeText(ForgotPassword.this, "Please Enter Email!", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Sending Reset Password Link.......");
        progressDialog.show();
        firebaseAuth.sendPasswordResetEmail(email.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            //Toast.makeText(ForgotPassword.this, "Reset Password Link Sent To Email!", Toast.LENGTH_SHORT).show();
                            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


                        } else {
                            Toast.makeText(ForgotPassword.this, "Email Not Registered", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
}
