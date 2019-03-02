package com.piedpipergeeks.aviate;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {


    private EditText emailEditText,resetPasswordEditText;
    private Button resetPasswordButton;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        firebaseAuth=FirebaseAuth.getInstance();
        Intialise();
        resetPassword();
    }
    public void Intialise(){
        emailEditText=(EditText)findViewById(R.id.emailpasswordreset_edittext);
        resetPasswordEditText=(EditText)findViewById(R.id.passwordreset_edittext);

    }
    public void resetPassword(){
        String email=emailEditText.getText().toString();
        if(!email.isEmpty()){
            firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                     if(task.isSuccessful()){
                         emailEditText.setEnabled(false);
                         Toast.makeText(ResetPassword.this,"Sent reset password email",Toast.LENGTH_SHORT).show();
                     }
                     else{
                         Toast.makeText(ResetPassword.this,"Failed to send reset email",Toast.LENGTH_SHORT).show();
                     }
                }
            });
        }
        else{
            Toast.makeText(ResetPassword.this,"Please enter the mail",Toast.LENGTH_SHORT).show();
        }
    }

}
