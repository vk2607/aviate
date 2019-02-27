package com.piedpipergeeks.aviate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.security.cert.Certificate;
import java.util.HashMap;
import java.util.Map;

public class CreateClubActivity extends AppCompatActivity {

    FirebaseFirestore fsClient;
    Map<String, Object> club = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_club);

        fsClient = FirebaseFirestore.getInstance();
    }

    public void createClub(View view) {

        String clubName = ((EditText) findViewById(R.id.create_club_name)).getText().toString();
        String clubInfo = ((EditText) findViewById(R.id.create_club_agenda)).getText().toString();

        club.put("name", clubName);
        club.put("info", clubInfo);

        fsClient.collection("Clubs")
                .document()
                .set(club)
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
    }

}
