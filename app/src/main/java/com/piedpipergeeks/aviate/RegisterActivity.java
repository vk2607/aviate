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
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth regAuth;
    private PhoneAuthProvider pAuth;
    private EditText firstNameEditText,lastNameEditText,emailEditText,passwordEditText,aadharNumberEditText,mobilenumberEditText,otpEditText;
    private Button otpButton,signUpButton;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        regAuth=FirebaseAuth.getInstance();
        pAuth=PhoneAuthProvider.getInstance();
        Intialise();
        PhoneAuthentication();
        EmailAuthentication();
    }
    protected void Intialise(){
        firstNameEditText=(EditText)findViewById(R.id.firstName_edittext);
        lastNameEditText=(EditText)findViewById(R.id.lastName_edittext);
        emailEditText=(EditText)findViewById(R.id.email_edittext);
        passwordEditText=(EditText)findViewById(R.id.password_edittext);
        aadharNumberEditText=(EditText)findViewById(R.id.aadharNumber_edittext);
        mobilenumberEditText=(EditText)findViewById(R.id.mobileNumber_edittext);
        otpEditText=(EditText)findViewById(R.id.enterOtp_edittext);
        otpButton=(Button)findViewById(R.id.sendOtp_button);
        signUpButton=(Button)findViewById(R.id.signUp_button);
        otpEditText.setEnabled(false);
    }
    protected void PhoneAuthentication(){
        otpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mobilenumber=mobilenumberEditText.getText().toString();
//                pAuth.verifyPhoneNumber(mobilenumber, 300, TimeUnit.SECONDS, RegisterActivity.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//                    @Override
//                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
//                        Toast.makeText(RegisterActivity.this,"Verfied",Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onVerificationFailed(FirebaseException e) {
//                        Toast.makeText(RegisterActivity.this,e.getMessage().toString(),Toast.LENGTH_SHORT).show();
//                    }
//                });
            }
        });
    }
    protected void EmailAuthentication(){
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email=emailEditText.getText().toString();
                String password=passwordEditText.getText().toString();
//                gygyyh
                regAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            regAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                      if(task.isSuccessful()){
                                         Toast.makeText(RegisterActivity.this,"Register successfully",Toast.LENGTH_SHORT).show();
                                      }
                                      else{
                                          Toast.makeText(RegisterActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                      }
                                }
                            });
                        }
                        else{
                            Toast.makeText(RegisterActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });
    }


}
