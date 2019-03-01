package com.piedpipergeeks.aviate;

import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClubDetails extends AppCompatActivity {
    RecyclerView recyclerView;
    LinearLayoutManager manager;
    MembersAdapter membersAdapter;
    Boolean isScrolling = false;
    FirebaseFirestore fsClient;

    String clubId, clubName;

    ArrayList<Profile> display_list = new ArrayList<>();
    ArrayList<Map> display_list_2 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_details);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        clubId = getIntent().getStringExtra("clubId");
        clubName = getIntent().getStringExtra("clubName");

//        FloatingActionButton fab = findViewById(R.id.fab);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_member_list);

        manager = new LinearLayoutManager(this);
        recyclerView.setAdapter(null);
        recyclerView.setLayoutManager(manager);

//
//        Profile member = new Profile();
//        member.setFirstName("Shreyas");
//        member.setLastName("Garud");
//
//        for (int i = 0; i < 15; i++) {
//            display_list.add(member);
//        }
//
        fsClient = FirebaseFirestore.getInstance();
        fsClient.collection("Clubs")
                .document(clubId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot snapshot = task.getResult();
                            Club club = snapshot.toObject(Club.class);

                            ArrayList<String> adminIds = club.getAdmins();
                            Map<String, Object> adminNames = club.getAdminNames();

                            if (adminIds != null) {
                                for (int i = 0; i < adminIds.size(); i++) {
                                    String id = adminIds.get(i);
                                    Map<String, Object> user = new HashMap<>();
                                    user.put("userId", id);
                                    user.put("userName", adminNames.get(id));
                                    user.put("userType", "admin");
                                    if (adminNames.get(id) != null) {
                                        display_list_2.add(user);
                                    }
                                }
                            }

                            if (club.getPresident() != null && club.getPresidentName() != null) {
                                Map<String, Object> user = new HashMap<>();
                                user.put("userId", club.getPresident());
                                user.put("userName", club.getSecretary());
                                user.put("userType", "president");
                                display_list_2.add(user);
                            }

                            if (club.getSecretary() != null && club.getSecretaryName() != null) {
                                Map<String, Object> user = new HashMap<>();
                                user.put("userId", club.getSecretary());
                                user.put("userName", club.getSecretaryName());
                                user.put("userType", "secretary");
                                display_list_2.add(user);
                            }

                            ArrayList<String> memberIds = club.getMembers();
                            Map<String, Object> memberNames = club.getMemberNames();

                            ArrayList<String> guestIds = club.getMembers();
                            Map<String, Object> guestNames = club.getGuestNames();

                            if (guestIds != null) {
                                for (int i = 0; i < guestIds.size(); i++) {
                                    String id = guestIds.get(i);
                                    Map<String, Object> user = new HashMap<>();
                                    user.put("userId", id);
                                    user.put("userName", guestNames.get(id));
                                    user.put("userType", "guest");
                                    if (guestNames.get(id) != null) {
                                        display_list_2.add(user);
                                    }
                                }
                            }

                            if (memberIds != null) {
                                for (int i = 0; i < memberIds.size(); i++) {
                                    String id = memberIds.get(i);
                                    Map<String, Object> user = new HashMap<>();
                                    user.put("userId", id);
                                    user.put("userName", memberNames.get(id));
                                    user.put("userType", "user");
                                    if (memberNames.get(id) != null) {
                                        display_list_2.add(user);
                                    }
                                }
                            }

                            membersAdapter = new MembersAdapter(display_list_2, ClubDetails.this);
                            membersAdapter.setClubId(clubId);
                            recyclerView.setAdapter(membersAdapter);

                        }
                    }
                });


//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }


}
