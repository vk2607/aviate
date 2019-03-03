package com.piedpipergeeks.aviate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.Date;


public class CalendarEventsAdapter extends RecyclerView.Adapter<CalendarEventsAdapter.VHolder> {

    ArrayList<Event> events;
    Context context;

    public CalendarEventsAdapter(ArrayList<Event> events, Context context) {
        this.events = events;
        this.context = context;
//        Toast.makeText(context, "Adapter object created", Toast.LENGTH_SHORT).show();

    }

    @NonNull
    @Override
    public VHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_calendar_events, viewGroup, false);
//        Toast.makeText(context, "View holder created", Toast.LENGTH_SHORT).show();
        return new VHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VHolder vHolder, int position) {
        Event event = events.get(position);
        Timestamp timestamp = event.getTimestamp();
        Date date = new Date(timestamp.getSeconds() * 1000);

        vHolder.date.setText(date.toString());
        vHolder.clubName.setText(event.getClubName());
    }

    public void updateEvents(ArrayList<Event> events) {
        this.events = events;
    }

    @Override
    public int getItemCount() {
        Log.d("ITEM COUNT", String.valueOf(events.size()));
        return events.size();
    }

    public class VHolder extends RecyclerView.ViewHolder {

        TextView date, time, clubName;

        public VHolder(@NonNull View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.calendar_event_timestamp);
//            time = (TextView) itemView.findViewById(R.id.);
            clubName = (TextView) itemView.findViewById(R.id.calendar_event_club_name);
        }
    }


}
