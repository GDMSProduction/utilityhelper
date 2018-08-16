package com.zammle2009wtfgmail.utilityhelper;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangeUserPassword extends AppCompatActivity {

    private EditText password, reenterPassword;
    private Button changePassword;

    private FirebaseAuth firebaseAuth;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_user_password);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        firebaseAuth = FirebaseAuth.getInstance();
        changePassword = findViewById(R.id.Change);
        password = findViewById(R.id.PasswordeditText);
        reenterPassword = findViewById(R.id.ReenterPasswordeditText);
        progressDialog = new ProgressDialog(this);

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveData();
            }
        });


    }


    public void SaveData() {
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
                                    Toast.makeText(ChangeUserPassword.this, "Password Successfully Changed", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(ChangeUserPassword.this, "Passwords Don't Match!", Toast.LENGTH_SHORT).show();

            }
        }


    }
}
