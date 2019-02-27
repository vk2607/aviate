package com.piedpipergeeks.aviate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class PickClubActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayoutManager manager;
    PickClubAdapter pickClubAdapter;

    ArrayList<Club> display_list = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_club);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_club_name);
        manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        Club club = new Club();
        club.setName("deAsra");
        for (int i = 0; i < 15; i++) {
            display_list.add(club);
        }

        pickClubAdapter = new PickClubAdapter(display_list, this);

        recyclerView.setAdapter(pickClubAdapter);
        recyclerView.setLayoutManager(manager);

    }
}
