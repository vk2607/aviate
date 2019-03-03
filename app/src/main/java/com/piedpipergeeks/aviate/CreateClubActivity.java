package com.piedpipergeeks.aviate;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.security.cert.Certificate;
import java.util.HashMap;
import java.util.Map;

public class CreateClubActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseFirestore fsClient;
    FirebaseDatabase firebaseDatabase;
    Club club = new Club();
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_club);

        auth = FirebaseAuth.getInstance();
        fsClient = FirebaseFirestore.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    public void createClub(View view) {

        String clubName = ((EditText) findViewById(R.id.create_club_name)).getText().toString();
        String clubInfo = ((EditText) findViewById(R.id.create_club_agenda)).getText().toString();

        String clubId = fsClient.collection("Clubs")
                .document()
                .getId();

        pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        club.setName(clubName);
        club.setInfo(clubInfo);
        club.addAdmin(auth.getUid(), pref.getString("firstName", "") + " " + pref.getString("lastName", ""));
        club.setClubId(clubId);
        club.setIsChatMuted(false);
        club.setNumberOfMembers(1);

//        Log.d("DOC ID", clubId);

        fsClient.collection("Clubs")
                .document(clubId)
                .set(club.getMap())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(CreateClubActivity.this, "Club created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(CreateClubActivity.this, HomeScreenActivity.class));
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

//
        firebaseDatabase.getReference("Clubs")
                .child(clubId).setValue(clubName);


        fsClient.collection("Users")
                .document(auth.getUid())
                .update("clubAdmin", FieldValue.arrayUnion(clubId));

    }

}
