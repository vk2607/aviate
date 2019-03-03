package com.piedpipergeeks.aviate;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Settings extends AppCompatActivity {
    private EditText emailEditText;
    private Button resetPasswordButton;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        emailEditText = (EditText) findViewById(R.id.email_edittext);
        resetPasswordButton = (Button) findViewById(R.id.resetpassword_button);
        firebaseAuth = FirebaseAuth.getInstance();
        resetPassword();

    }

    private void resetPassword() {
        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString();
                if (!email.isEmpty()) {
                    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Settings.this, "Reset password email sent ", Toast.LENGTH_SHORT).show();
                            } else {

                                Toast.makeText(Settings.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }


                    });

                } else {
                    Toast.makeText(Settings.this, "Enter the email", Toast.LENGTH_SHORT).show();
                }
            }


        });


    }
}

