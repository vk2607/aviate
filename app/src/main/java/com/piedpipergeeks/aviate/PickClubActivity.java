package com.piedpipergeeks.aviate;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ProgressBar;

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
        club.setClubname("deAsra");
        for (int i = 0; i < 15; i++) {
            display_list.add(club);
        }

        pickClubAdapter = new PickClubAdapter(display_list, this);

        recyclerView.setAdapter(pickClubAdapter);
        recyclerView.setLayoutManager(manager);

    }
}
