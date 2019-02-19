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
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth regAuth;
    private PhoneAuthProvider pAuth;
    private EditText firstNameEditText, lastNameEditText, emailEditText, passwordEditText, aadharNumberEditText, mobilenumberEditText, otpEditText;
    private Button otpButton, signUpButton;
    private String rVerificationId;
    private PhoneAuthProvider.ForceResendingToken rResendToken;
    private int btnId=0;
    private boolean phoneVerified=false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        regAuth = FirebaseAuth.getInstance();
        pAuth = PhoneAuthProvider.getInstance();
//        Log.d("CHECK", String.valueOf(pAuth == null));

        Intialise();
        PhoneAuthentication();
        EmailAuthentication();
    }

    protected void Intialise() {
        firstNameEditText = (EditText) findViewById(R.id.firstName_register_edittext);
        lastNameEditText = (EditText) findViewById(R.id.lastName_register_edittext);
        emailEditText = (EditText) findViewById(R.id.email_register_edittext);
        passwordEditText = (EditText) findViewById(R.id.password_register_edittext);
        aadharNumberEditText = (EditText) findViewById(R.id.aadharNumber_register_edittext);
        mobilenumberEditText = (EditText) findViewById(R.id.mobileNumber_register_edittext);
        otpEditText = (EditText) findViewById(R.id.enterOtp_register_edittext);
        otpButton = (Button) findViewById(R.id.sendOtp_register_button);
        signUpButton = (Button) findViewById(R.id.signUp_register_button);
        otpEditText.setEnabled(false);
    }

    protected void PhoneAuthentication() {
        otpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String mobilenumber = mobilenumberEditText.getText().toString();
                if (!mobilenumber.isEmpty()) {

                    if(btnId==0){
                        mobilenumberEditText.setEnabled(false);
                        pAuth.verifyPhoneNumber(mobilenumber, 100, TimeUnit.SECONDS, RegisterActivity.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                               signInWithPhoneAuthCredential(phoneAuthCredential);
                            }

                            @Override
                            public void onVerificationFailed(FirebaseException e) {
                                mobilenumberEditText.setEnabled(true);
                                Toast.makeText(RegisterActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                            public void onCodeSent(String verificationId,
                                                   PhoneAuthProvider.ForceResendingToken token){
                                rVerificationId = verificationId;
                                btnId=1;
                                rResendToken = token;
                                otpEditText.setEnabled(true);
                                otpButton.setText("Verify Otp");
                            }

                        });

                    }
                    else{
                        String verificationcode=otpEditText.getText().toString();
                        if(!verificationcode.isEmpty()) {
                            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(rVerificationId, verificationcode);
                            signInWithPhoneAuthCredential(credential);
                        }
                        else
                        {
                            Toast.makeText(RegisterActivity.this,"Please enter the otp",Toast.LENGTH_SHORT).show();
                        }
                    }




                }
                else{
                    Toast.makeText(RegisterActivity.this,"Enter the mobile number..",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    protected void EmailAuthentication() {
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String firstname = firstNameEditText.getText().toString();
                String lastname= lastNameEditText.getText().toString();
                String mobilenumber=mobilenumberEditText.getText().toString();
                String aadharnumber=aadharNumberEditText.getText().toString();
                if (!password.isEmpty() && !email.isEmpty() && !firstname.isEmpty() && !lastname.isEmpty() && !mobilenumber.isEmpty() && !aadharnumber.isEmpty() && phoneVerified==true){
                    regAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                regAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(RegisterActivity.this, "Click on the verification link and sign in", Toast.LENGTH_SHORT).show();
                                            Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
                else if(!phoneVerified){
                    Toast.makeText(RegisterActivity.this,"Please verify mobile number first...",Toast.LENGTH_SHORT).show();
                }
                else if (email.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please enter the email", Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please enter the password", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(RegisterActivity.this,"Please fill all the fields",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential){
        regAuth.signInWithCredential(credential).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this,"Mobile Number is Verified...",Toast.LENGTH_SHORT).show();
                    otpButton.setEnabled(false);
                    phoneVerified=true;
                    mobilenumberEditText.setEnabled(false);
                    otpEditText.setEnabled(false);
                    otpEditText.setVisibility(View.INVISIBLE);
                    otpButton.setText("OTP verified");

                    FirebaseUser user=task.getResult().getUser();
                }
                else{
                    Toast.makeText(RegisterActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


}
