package com.piedpipergeeks.aviate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

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
    Button createEventButton, eventsButton;

    String clubId, clubName, clubInfo;

    ArrayList<Profile> display_list = new ArrayList<>();
    ArrayList<Map> display_list_2 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_details);

        Intent intent = getIntent();

        clubId = intent.getStringExtra("clubId");
        clubName = intent.getStringExtra("clubName");
        clubInfo = intent.getStringExtra("clubInfo");

//        getActionBar().setDisplayHomeAsUpEnabled(true);


//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        createEventButton = (Button) findViewById(R.id.create_event_button);

        SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String userType = pref.getString("userType", "");
        if (userType.equals("admin")) {
            createEventButton.setVisibility(View.VISIBLE);
            createEventButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ClubDetails.this, CreateEvent.class);
                    intent.putExtra("clubId", clubId);
                    startActivity(intent);
                }
            });
        }

        eventsButton = (Button) findViewById(R.id.events_button);
        eventsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClubDetails.this, EventsList.class);
                intent.putExtra("clubId", clubId);
                startActivity(intent);
            }
        });


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
                                user.put("userName", club.getPresidentName());
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
                            membersAdapter.setClubDetails(clubId, clubName);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case (R.id.home):
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                upIntent.putExtra("clubId", clubId);
                upIntent.putExtra("clubName", clubName);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    TaskStackBuilder.create(this)
                            .addNextIntentWithParentStack(upIntent)
                            .startActivities();
                } else {
                    NavUtils.navigateUpTo(this, upIntent);
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
