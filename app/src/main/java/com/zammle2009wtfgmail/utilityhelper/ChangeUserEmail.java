package com.zammle2009wtfgmail.utilityhelper;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangeUserEmail extends AppCompatActivity {

    private EditText email;

    private FirebaseAuth firebaseAuth;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_user_email);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        firebaseAuth = FirebaseAuth.getInstance();
        Button changeEmail = findViewById(R.id.Change);
        email = findViewById(R.id.emaileditText);
        progressDialog = new ProgressDialog(this);

        changeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeEmail();
            }
        });


    }

//Changes the email you signed up with
    public void ChangeEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String Email = email.getText().toString().trim();

        if (TextUtils.isEmpty(Email)) {
            Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Changing Email....");
        progressDialog.show();
        if (user != null) {
            if (!user.getEmail().equals(Email)) {
                user.updateEmail(email.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                progressDialog.dismiss();
                                if (task.isSuccessful()){
                                    //Toast.makeText(ChangeUserEmail.this, "Email Successfully Changed", Toast.LENGTH_SHORT).show();
                                    //sign out with the email and password you created
                                    firebaseAuth.signOut();
                                    finish();
                                    Intent changeActivity = new Intent(ChangeUserEmail.this, LoginScreen.class);

                                    startActivity(changeActivity);
                                }
                                else{
                                    //Toast.makeText(ChangeUserEmail.this, "Email not Successfully Changed", Toast.LENGTH_SHORT).show();

                                }

                            }
                        });

            }

            else {
                progressDialog.dismiss();
                Toast.makeText(ChangeUserEmail.this, "Current email is the same!", Toast.LENGTH_SHORT).show();

            }
        }


    }
}
