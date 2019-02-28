package com.piedpipergeeks.aviate;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class PickClubActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayoutManager manager;
    PickClubAdapter pickClubAdapter;
    FirebaseFirestore fsClient;

    ArrayList<Club> display_list = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_club);

        Bundle bundle = getIntent().getExtras();
        String firstname = bundle.getString("firstname");
        String lastname = bundle.getString("lastname");
        String userId = bundle.getString("userId");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_club_name);
        manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);

//        Club club = new Club();
//////        club.setName("deAsra");
//        for (int i = 0; i < 15; i++) {
//            Club club = new Club();
//            club.setName("deAsra" + i);
//            display_list.add(club);
//        }

        fsClient.collection("Clubs")
                .whereArrayContains("admins", FirebaseAuth.getInstance().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot snapshot : task.getResult()) {
                                Club club = snapshot.toObject(Club.class);
                                display_list.add(club);
                            }
                        }
                    }
                });

        pickClubAdapter = new PickClubAdapter(display_list, this);
        pickClubAdapter.setData(firstname, lastname, userId);
        recyclerView.setAdapter(pickClubAdapter);
        recyclerView.setLayoutManager(manager);

    }
}
