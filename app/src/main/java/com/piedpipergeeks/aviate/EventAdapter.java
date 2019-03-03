package com.piedpipergeeks.aviate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.VHolder> {
    ArrayList<Event> events;
    Context context;
    String clubId;
    FirebaseFirestore fsClient;
    ArrayList<String> emailList;
    int pos;

    public EventAdapter(ArrayList<Event> events, Context context) {
        this.events = events;
        this.context = context;
        this.emailList = new ArrayList<>();
    }

    @NonNull
    @Override

    public VHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int ViewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_events, viewGroup, false);
        return new VHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventAdapter.VHolder vHolder, int position) {
        Event event = events.get(position);

        Timestamp timestamp = (Timestamp) event.getTimestamp();
        Date date = new Date(timestamp.getSeconds() * 1000);
        String timeOfEvent = DateFormat.getTimeInstance(DateFormat.SHORT).format(date);
        String dateOfEvent = DateFormat.getDateInstance().format(date);

        vHolder.eventType.setText(event.getEventType());
        vHolder.eventDate.setText(dateOfEvent + " at " + timeOfEvent);
        vHolder.sendMom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmailForMom();
            }
        });

    }

    public void setClubId(String clubId) {
        this.clubId = clubId;
        Log.d("EMAIL", "CLUB ID SET TO: " + clubId);
    }

    private void sendEmailForMom() {
        Log.d("EMAIL", "REACHED FUNCTION");
        fsClient = FirebaseFirestore.getInstance();
//        fsClient.collection("Users")
//                .whereArrayContains("clubMember", clubId)
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//
//                            Log.d("EMAIL", "FIRST QUERY IS SUCCESSFUL");
//
//                            for (DocumentSnapshot snapshot : task.getResult()) {
//                                Log.d("EMAIL", "FIRST QUERY: " + snapshot.get("userName"));
//                                Profile user = snapshot.toObject(Profile.class);
//                                emailList.add(user.getEmail());
//                            }
//
//                            Log.d("EMAIL", "COMPLETED FIRST QUERY");
//
//                            fsClient.collection("Users")
//                                    .whereArrayContains("clubSecretary", clubId)
//                                    .get()
//                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                            if (task.isSuccessful()) {
//                                                for (DocumentSnapshot snapshot : task.getResult()) {
//                                                    Profile user = snapshot.toObject(Profile.class);
//                                                    emailList.add(user.getEmail());
//
//                                                    Log.d("EMAIL", "COMPLETED SECOND QUERY");
//
//                                                    onEmailQueryComplete();
//
//                                                }
//
////                                                fsClient.collection("Users")
////                                                        .whereArrayContains("clubGuest", clubId)
////                                                        .get()
////                                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
////                                                            @Override
////                                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
////                                                                if (task.isSuccessful()) {
////                                                                    for (QueryDocumentSnapshot snapshot : task.getResult()) {
////                                                                        Profile user = snapshot.toObject(Profile.class);
////                                                                        emailList.add(user.getEmail());
////                                                                    }
////                                                                }
////                                                            }
////                                                        });
//                                            }
//
//                                        }
//                                    });
//
//                        }
//                    }
//                });

        FirebaseAuth auth = FirebaseAuth.getInstance();
        final ArrayList<Club> display_list = new ArrayList<>();

        fsClient.collection("Clubs")
                .whereArrayContains("members", auth.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot snapshot : task.getResult()) {
                                Club club = snapshot.toObject(Club.class);
                                display_list.add(club);
                            }

                            Log.d("EMAIL", display_list.get(0).getName());
//                            chatsAdapter.updateData(display_list);
//                            chatsAdapter.notifyDataSetChanged();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });





//        fsClient.collection("Users")
//                .whereArrayContains("clubGuest", clubId)
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot snapshot : task.getResult()) {
//                                Profile user = snapshot.toObject(Profile.class);
//                                emailList.add(user.getEmail());
//                            }
//                        }
//                    }
//                });
    }

    private void onEmailQueryComplete() {

        Log.d("EMAIL", "REACHED ON COMPLETE FUNCTION");

        Log.d("EMAIL", emailList.toString());

        String emailAddresses = "";
        for (int i = 0; i < emailList.size(); i++) {
            if (i != 0) {
                emailAddresses += ", " + emailList.get(i);
            }
        }

        Log.d("EMAIL", emailAddresses);

    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class VHolder extends RecyclerView.ViewHolder {
        TextView eventType, eventDate;
        Button sendMom;

        public VHolder(@NonNull View itemView) {
            super(itemView);
            eventType = (TextView) itemView.findViewById(R.id.event_type_text);
            eventDate = (TextView) itemView.findViewById(R.id.event_date_text);
            sendMom = (Button) itemView.findViewById(R.id.mom_button);
        }
    }
}
