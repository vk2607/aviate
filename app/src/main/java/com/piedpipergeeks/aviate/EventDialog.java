package com.piedpipergeeks.aviate;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;

import java.text.DateFormat;
import java.util.Date;
//import android.support.v4.app.DialogFragment;

public class EventDialog extends DialogFragment {

    private String type, time;
    private TextView eventTime;
    private View view;
    private boolean timeInFuture;

    public void setDetails(String eventType, String eventTime) {
        this.type = eventType;
        this.time = eventTime;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
//        view = inflater.inflate(R.layout.event_dialog_box, null);

//        eventTime = view.findViewById(R.id.event_dialog_timestamp);

//        eventTime.setText(time);

        if (timeInFuture) {
// 0           ((TextView) view.findViewById(R.id.event_dialog_mom)).setVisibility(View.VISIBLE);
//            ((TextView) view.findViewById(R.id.event_dialog_link)).setVisibility(View.INVISIBLE);

        } else {
//            ((TextView) view.findViewById(R.id.event_dialog_mom)).setVisibility(View.GONE);
//            ((TextView) view.findViewById(R.id.event_dialog_link)).setVisibility(View.VISIBLE);

        }


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view)
                .setTitle(type)
                .setMessage("Confirm participation?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        return builder.create();
    }


    public void setDetails(Event event) {

        Timestamp currentTime = new Timestamp(new Date().getTime() / 1000, 0);

        Date date = new Date(((Timestamp) event.getTimestamp()).getSeconds() * 1000);
        final String timeOfEvent = DateFormat.getTimeInstance(DateFormat.SHORT).format(date);
        final String dateOfEvent = DateFormat.getDateInstance().format(date);

        type = (String) event.getEventType();
        time = dateOfEvent + " at " + timeOfEvent;

        if (currentTime.compareTo((Timestamp) event.getTimestamp()) <= 0) {
            timeInFuture = true;
        } else {
            timeInFuture = false;
        }


    }
}
