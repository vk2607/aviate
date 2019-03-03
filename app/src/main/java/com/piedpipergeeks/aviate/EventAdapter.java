package com.piedpipergeeks.aviate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.Timestamp;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.VHolder> {
    ArrayList<Event> events;
    Context context;
    int pos;

    public EventAdapter(ArrayList<Event> events, Context context) {
        this.events = events;
        this.context = context;
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
//            sendMom = (Button) itemView.findViewById(R.id.)
        }
    }
}
