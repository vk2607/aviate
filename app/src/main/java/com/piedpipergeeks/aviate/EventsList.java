package com.piedpipergeeks.aviate;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class EventsList extends AppCompatActivity {
    RecyclerView recyclerView;
    LinearLayoutManager manager;
    EventAdapter eventAdapter;
    Boolean isScrolling = false;
    ArrayList<Event> display_list = new ArrayList<>();

    private FirebaseFirestore fsClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_list);

        Bundle bundle = getIntent().getExtras();
        final String clubId = bundle.getString("clubId");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_events_list);
        manager = new LinearLayoutManager(this);

        recyclerView.setAdapter(null);
        recyclerView.setLayoutManager(manager);

        fsClient = FirebaseFirestore.getInstance();
        fsClient.collection("Events")
                .whereEqualTo("clubId", clubId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot snapshot : task.getResult()) {
                                Event event = snapshot.toObject(Event.class);
                                display_list.add(event);
                            }
                            eventAdapter = new EventAdapter(display_list, EventsList.this);
                            recyclerView.setAdapter(eventAdapter);
                        }
                    }
                });

    }
}
