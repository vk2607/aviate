package com.piedpipergeeks.aviate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class RegisterEntrepreneurActivity extends AppCompatActivity {

    private FirebaseAuth regAuth;
    private PhoneAuthProvider pAuth;
    private FirebaseFirestore fsClient;
    private EditText firstNameEditText, lastNameEditText, emailEditText, passwordEditText, mobilenumberEditText, otpEditText;
    private Button otpButton, signUpButton;
    private String rVerificationId, email, firstname, lastname, mobilenumber, userId;
    private PhoneAuthProvider.ForceResendingToken rResendToken;
    private int btnId = 0;
    private boolean phoneVerified = false;

    SharedPreferences sharedPreferences;
    private static final String MyPreferences = "MyPrefs";
    private static final String UserIdKey = "UserId";
    private static final String EmailKey = "Email";
    private static final String UserTypeKey = "UserType";
    private static final String FirstNameKey = "FirstName";
    private static final String LastNameKey = "LastName";
    private static final String PhoneNumberKey = "PhoneNumber";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_entrepreneur);
        regAuth = FirebaseAuth.getInstance();
        pAuth = PhoneAuthProvider.getInstance();
        fsClient = FirebaseFirestore.getInstance();
//        Log.d("CHECK", String.valueOf(pAuth == null));

        Intialise();
        PhoneAuthentication();
        EmailAuthentication();

    }

    protected void Intialise() {
        EditText editText=new EditText(this);
        firstNameEditText = (EditText) findViewById(R.id.firstName_registerEntrepreneur_edittext);
        lastNameEditText = (EditText) findViewById(R.id.lastName_registerEntrepreneur_edittext);
        emailEditText = (EditText) findViewById(R.id.email_registerEntrepreneur_edittext);
        passwordEditText = (EditText) findViewById(R.id.password_registerEntrepreneur_edittext);
        mobilenumberEditText = (EditText) findViewById(R.id.mobileNumber_registerEntrepreneur_edittext);
        otpEditText = (EditText) findViewById(R.id.enterOtp_registerEntrepreneur_edittext);
        otpButton = (Button) findViewById(R.id.sendOtp_registerEntrepreneur_button);
        signUpButton = (Button) findViewById(R.id.signUp_registerEntrepreneur_button);
        otpEditText.setEnabled(false);
    }

    protected void PhoneAuthentication() {
        otpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String mobilenumber = mobilenumberEditText.getText().toString();
                if (!mobilenumber.isEmpty()) {

                    if (btnId == 0) {
                        mobilenumberEditText.setEnabled(false);
                        pAuth.verifyPhoneNumber(mobilenumber, 100, TimeUnit.SECONDS, RegisterEntrepreneurActivity.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                                signInWithPhoneAuthCredential(phoneAuthCredential);
                            }

                            @Override
                            public void onVerificationFailed(FirebaseException e) {
                                mobilenumberEditText.setEnabled(true);
                                Toast.makeText(RegisterEntrepreneurActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            public void onCodeSent(String verificationId,
                                                   PhoneAuthProvider.ForceResendingToken token) {
                                rVerificationId = verificationId;
                                btnId = 1;
                                rResendToken = token;
                                otpEditText.setEnabled(true);
                                otpButton.setText("Verify Otp");
                            }

                        });

                    } else {
                        String verificationcode = otpEditText.getText().toString();
                        if (!verificationcode.isEmpty()) {
                            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(rVerificationId, verificationcode);
                            signInWithPhoneAuthCredential(credential);
                        } else {
                            Toast.makeText(RegisterEntrepreneurActivity.this, "Please enter the otp", Toast.LENGTH_SHORT).show();
                        }
                    }


                } else {
                    Toast.makeText(RegisterEntrepreneurActivity.this, "Enter the mobile number..", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    protected void EmailAuthentication() {
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                firstname = firstNameEditText.getText().toString();
                lastname = lastNameEditText.getText().toString();
                mobilenumber = mobilenumberEditText.getText().toString();


                if (!password.isEmpty() && !email.isEmpty() && !firstname.isEmpty() && !lastname.isEmpty() && !mobilenumber.isEmpty() && phoneVerified) {
                    regAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterEntrepreneurActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                regAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(RegisterEntrepreneurActivity.this, "Click on the verification link and sign in", Toast.LENGTH_SHORT).show();
                                            storeDataLocally();
                                            uploadUserData();
                                            Intent intent = new Intent(RegisterEntrepreneurActivity.this, LoginActivity.class);
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(RegisterEntrepreneurActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(RegisterEntrepreneurActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                } else if (!phoneVerified) {
                    Toast.makeText(RegisterEntrepreneurActivity.this, "Please verify mobile number first...", Toast.LENGTH_SHORT).show();
                } else if (email.isEmpty()) {
                    Toast.makeText(RegisterEntrepreneurActivity.this, "Please enter the email", Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty()) {
                    Toast.makeText(RegisterEntrepreneurActivity.this, "Please enter the password", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterEntrepreneurActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void storeDataLocally() {
        sharedPreferences = getSharedPreferences(MyPreferences, Context.MODE_PRIVATE);
        String UserIdValue = regAuth.getUid().toString();
        String EmailValue = emailEditText.getText().toString();
        String UserTypeValue = "user";
        String LastNameValue = lastNameEditText.getText().toString();
        String PhoneNumberValue = mobilenumberEditText.getText().toString();
        String FirstNameValue = firstNameEditText.getText().toString();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(FirstNameKey, FirstNameValue);
        editor.putString(LastNameKey, LastNameValue);
        editor.putString(UserIdKey, UserIdValue);
        editor.putString(EmailKey, EmailValue);
        editor.putString(UserTypeKey, UserTypeValue);
        editor.putString(PhoneNumberKey, PhoneNumberValue);
        editor.apply();

    }

    private void uploadUserData() {
        userId = regAuth.getUid();
        Boolean emailVerified = regAuth.getCurrentUser().isEmailVerified();

        Map<String, Object> userData = new HashMap<>();
        userData.put("userId", userId);
        userData.put("email", email);
        userData.put("userType", "user");
        userData.put("emailVerified", emailVerified);
        userData.put("firstName", firstname);
        userData.put("lastName", lastname);
        userData.put("phoneNumber", mobilenumber);

        fsClient.collection("Users")
                .document(userId)
                .set(userData)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterEntrepreneurActivity.this, "Data uploaded to cloud", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        regAuth.signInWithCredential(credential).addOnCompleteListener(RegisterEntrepreneurActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(RegisterEntrepreneurActivity.this, "Mobile Number is Verified...", Toast.LENGTH_SHORT).show();
                    otpButton.setEnabled(false);
                    phoneVerified = true;
                    mobilenumberEditText.setEnabled(false);
                    otpEditText.setEnabled(false);
                    otpEditText.setVisibility(View.INVISIBLE);
                    otpButton.setText("OTP verified");

                    FirebaseUser user = task.getResult().getUser();
                } else {
                    Toast.makeText(RegisterEntrepreneurActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


}
