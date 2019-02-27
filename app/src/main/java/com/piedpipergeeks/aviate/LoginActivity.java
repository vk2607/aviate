package com.piedpipergeeks.aviate;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import org.w3c.dom.Text;

import android.app.ProgressDialog;

public class LoginActivity extends AppCompatActivity {


    private FirebaseAuth lAuth;
    private FirebaseFirestore fsClient;
    private EditText emailEditText, passwordEditText;
    private Button signInButton;
    private FirebaseUser currentUser;
    private String userType = "user";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        lAuth = FirebaseAuth.getInstance();

        fsClient = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        fsClient.setFirestoreSettings(settings);

        Intialiaze();
        SignIn();
    }

    protected void Intialiaze() {
        emailEditText = (EditText) findViewById(R.id.email_login_edittext);
        passwordEditText = (EditText) findViewById(R.id.password_login_edittext);
        signInButton = (Button) findViewById(R.id.signin_login_button);
    }

    private void showHelp() {
        TextView help = (TextView) findViewById(R.id.extra_text_textview);
        TextView reset_password = (TextView) findViewById(R.id.reset_password_textview);
        TextView resend_email = (TextView) findViewById(R.id.verify_email_textview);

        help.setVisibility(View.VISIBLE);
        reset_password.setVisibility(View.VISIBLE);
        resend_email.setVisibility(View.VISIBLE);
    }

    public void resetPassword(View view) {
        Toast.makeText(this, "Function called", Toast.LENGTH_SHORT).show();

        // Do something to update user's password

    }

    public void resendVerificationEmail(View view) {

        Toast.makeText(LoginActivity.this, "Function called", Toast.LENGTH_SHORT).show();


    }

    protected void SignIn() {
        signInButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
                pd.setMessage("Loading...");
                pd.show();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (!email.isEmpty() && !password.isEmpty()) {
                    lAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                if (lAuth.getCurrentUser().isEmailVerified()) {
                                    Toast.makeText(LoginActivity.this, "Signed in successfully", Toast.LENGTH_SHORT).show();
                                    fsClient.collection("Users")
                                            .document(lAuth.getCurrentUser().getUid())
                                            .update("emailVerified", true)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                                                }
                                            });

                                    toHomeScreen();

                                } else {
                                    Toast.makeText(LoginActivity.this, "Please verify your email first", Toast.LENGTH_SHORT).show();
                                    showHelp();
                                }
                            } else {
                                Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                showHelp();
                            }
                        }
                    });

                } else if (email.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Enter the email", Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Enter the password", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Problem", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    protected void toHomeScreen() {
        Intent homeScreenIntent;
        if (userType.equals("user")) {
            homeScreenIntent = new Intent(LoginActivity.this, HomeScreenUserActivity.class);
        } else {
            homeScreenIntent = new Intent(LoginActivity.this, HomeScreenActivity.class);
        }
        startActivity(homeScreenIntent);
        finish();

    }


}
