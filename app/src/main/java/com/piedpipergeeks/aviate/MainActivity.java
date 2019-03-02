package com.piedpipergeeks.aviate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    FirebaseFirestore fsClient;
    FirebaseDatabase database;

    ProgressBar progressBar;

    SharedPreferences preferences;
    private static final String MyPREFERENCES = "MyPrefs";
    private static final String USER_ID = "userId";
    private static final String EMAIL = "email";
    private static final String USER_TYPE = "userType";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String PHONE_NUMBER = "phoneNumber";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startActivity(new Intent(this,ChangePassword.class));
        mAuth = FirebaseAuth.getInstance();

        fsClient = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        fsClient.setFirestoreSettings(settings);
        database=FirebaseDatabase.getInstance();
        database.setPersistenceEnabled(true);
        progressBar = (ProgressBar) findViewById(R.id.auto_login_progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        sharedPreferencesLogin();

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }


    public void toSignInActivity(View view) {
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }

    public void toSignUpeEntrepreneurActivity(View view) {
        startActivity(new Intent(MainActivity.this, RegisterEntrepreneurActivity.class));
    }

    private void sharedPreferencesLogin() {
        SharedPreferences sharedPreferences;
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        if (sharedPreferences.contains(USER_ID)) {
            Log.d("SHARED PREF", "SHARED PREFERENCES CONTAINS USER");
            if (sharedPreferences.getString(USER_TYPE, "").equals("user")) {
                startActivity(new Intent(MainActivity.this, HomeScreenUserActivity.class));
                finish();
            } else if (sharedPreferences.getString(USER_TYPE, "").equals("admin")) {
                startActivity(new Intent(MainActivity.this, HomeScreenActivity.class));
                finish();
            }
        } else {
            defaultLogin();
        }
    }

    private void getUserData() {

        String userId = mAuth.getUid();

        fsClient.collection("Users")
                .document(userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            Profile user = document.toObject(Profile.class);
                            saveDataLocally(user);
                        }
                    }
                });


    }

    public void saveDataLocally(Profile user) {
        preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(FIRST_NAME, user.getFirstName());
        editor.putString(LAST_NAME, user.getLastName());
        editor.putString(USER_ID, user.getUserId());
        editor.putString(EMAIL, user.getPhoneNumber());
        editor.putString(USER_TYPE, user.getUserType());
        editor.putString(PHONE_NUMBER, user.getPhoneNumber());
        editor.apply();
    }

    public void defaultLogin() {
        currentUser = mAuth.getCurrentUser();
        String userId;

        if (currentUser != null && currentUser.isEmailVerified()) {
            userId = currentUser.getUid();

            fsClient.collection("Users")
                    .document(userId)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot snapshot = task.getResult();
                                try {
                                    if (snapshot.get("userType").equals("user")) {
                                        getUserData();
                                        startActivity(new Intent(MainActivity.this, HomeScreenUserActivity.class));
                                        finish();
                                    } else if (snapshot.get("userType").equals("admin")) {
                                        getUserData();
                                        startActivity(new Intent(MainActivity.this, HomeScreenActivity.class));
                                        finish();
                                    }
                                } catch (Exception e) {
                                    Log.d("QUERY", e.toString());
                                }
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("QUERY", e.toString());
                        }
                    });
        }
        if (currentUser == null) {
            progressBar.setVisibility(View.GONE);
        }
    }



}
