package com.piedpipergeeks.aviate;

import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

public class ClubDetails extends AppCompatActivity {
    RecyclerView recyclerView;
    LinearLayoutManager manager;
    MembersAdapter membersAdapter;
    Boolean isScrolling = false;

    String clubId, clubName;

    ArrayList<Profile> display_list = new ArrayList<>();

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
        Profile member = new Profile();
        member.setFirstName("Shreyas");
        member.setLastName("Garud");
        for (int i = 0; i < 15; i++) {
            display_list.add(member);
        }
        membersAdapter = new MembersAdapter(display_list, this);
        membersAdapter.setClubId(clubId);
        recyclerView.setAdapter(membersAdapter);
        recyclerView.setLayoutManager(manager);

//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }


}
