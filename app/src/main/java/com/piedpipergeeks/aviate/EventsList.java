package com.piedpipergeeks.aviate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class EventsList extends AppCompatActivity {
    RecyclerView recyclerView;
    LinearLayoutManager manager;
    EventAdapter eventAdapter;
    Boolean isScrolling = false;
    ArrayList<Event> display_list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_list);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_events_list);
        manager = new LinearLayoutManager(this);
        Event event = new Event();
        event.setEventType("Webinar");

        for (int i = 0; i < 15; i++) {
            display_list.add(event);
        }
        eventAdapter = new EventAdapter(display_list, this);

        recyclerView.setAdapter(eventAdapter);
        recyclerView.setLayoutManager(manager);
    }
}
