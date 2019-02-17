package com.piedpipergeeks.aviate;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {


    private FirebaseAuth lAuth;
    private EditText emailEditText,passwordEditText;
    private Button signInButton;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        lAuth=FirebaseAuth.getInstance();
        Intialiaze();
        SignIn();
    }
    protected void Intialiaze(){
       emailEditText=(EditText)findViewById(R.id.email_login_edittext);
       passwordEditText=(EditText)findViewById(R.id.password_login_edittext);
       signInButton=(Button)findViewById(R.id.signin_login_button);
    }
    protected void SignIn(){
        signInButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String email=emailEditText.getText().toString();
                String password=passwordEditText.getText().toString();
                if(!email.isEmpty() && !password.isEmpty()){
                    lAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                if(lAuth.getCurrentUser().isEmailVerified()){
                                    Toast.makeText(LoginActivity.this,"Signed in successfully",Toast.LENGTH_SHORT).show();
                                    toHomeScreen();
                                }
                                else{
                                    Toast.makeText(LoginActivity.this,"Please verify your email",Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                Toast.makeText(LoginActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
                else if(email.isEmpty()){
                    Toast.makeText(LoginActivity.this,"Enter the email",Toast.LENGTH_SHORT).show();
                }
                else if (password.isEmpty()){
                    Toast.makeText(LoginActivity.this,"Enter the password",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(LoginActivity.this,"Problem",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    protected void toHomeScreen () {
        startActivity(new Intent(LoginActivity.this, HomeScreenActivity.class));
    }
}
