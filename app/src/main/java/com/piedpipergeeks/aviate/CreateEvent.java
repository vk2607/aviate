package com.piedpipergeeks.aviate;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;

public class CreateEvent extends AppCompatActivity implements SetDateDialog.SetDateDialogListener, SetTimeDialog.SetTimeDialogListener {

    private TextView setDateTextView, setTimeTextView;
    private Calendar calendar;
    private Date date;
    private Button createEvent;
    private FirebaseFirestore fsClient;
    private String clubId;
    private int d, m, y, h, min;
    final ArrayList<String> emailList = new ArrayList<>();
    String emailAddresses = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        Intent intent = getIntent();
        clubId = intent.getStringExtra("clubId");

        Spinner s = (Spinner) findViewById(R.id.spinner_select_event);
        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(this, R.array.event_arrays, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(arrayAdapter);
        setDateTextView = (TextView) findViewById(R.id.setdate_textview);
        setTimeTextView = (TextView) findViewById(R.id.settimetextview);

        fsClient = FirebaseFirestore.getInstance();

        calendar = Calendar.getInstance();
        setEventDate();
        setEventTime();
        Intialise();
        createEvent();

    }

    private void sendInvitationEmail() {

//        final String emailAddresses = "prashantbhandari1999@gmail.com";


        fsClient = FirebaseFirestore.getInstance();
        fsClient.collection("Users")
                .whereArrayContains("clubMember", clubId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot snapshot : task.getResult()) {
//                                Profile user = snapshot.toObject(Profile.class);
                                emailList.add(String.valueOf(snapshot.get("email")));

                            }
                            onEmailQueryComplete();


                        }
                    }
                });


    }

    private void onEmailQueryComplete() {

//        Log.d("EMAIL", "REACHED ON COMPLETE FUNCTION");

//        Log.d("EMAIL", emailList.toString());


        for (int i = 0; i < emailList.size(); i++) {
            if (i != 0) {
                emailAddresses += ", " + emailList.get(i);
            } else {
                emailAddresses += emailList.get(i);
            }
        }

//        Log.d("EMAIL", emailAddresses);
//
//        Intent intent=new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", emailAddresses, null));
//        intent.putExtra(Intent.EXTRA_SUBJECT,"Invitation to meeting");

        new AlertDialog.Builder(this)
                .setIcon(null)
                .setTitle("Invite members")
                .setMessage("Send event invitation to all club members?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", emailAddresses, null));
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Invitation to meeting");
                        startActivity(Intent.createChooser(intent, null));
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .show();

    }


    private void createEvent() {
        final Map<String, Object> event = new HashMap<>();
        event.put("eventType", "Webinar");

        String str = y + " " + m + " " + d + " " + h + " " + min;
        SimpleDateFormat df = new SimpleDateFormat("yyyy MM dd HH mm");

        Log.d("TIMEEE", df.toPattern());

        Date date = new Date();
        try {
            date = df.parse(str);
        } catch (Exception e) {
            Log.d("TIMEEE", "PARSE ERROR");
        }
//        Date date = new Date(y, m, d, h, min, 0);

        long time = date.getTime() / 1000;

        Timestamp timestamp = new Timestamp(new Date().getTime() / 1000, 0);
        event.put("timestamp", timestamp);

        createEvent = (Button) findViewById(R.id.confirm_create_event);
        createEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String eventId = fsClient.collection("Clubs")
                        .document(clubId)
                        .collection("Events")
                        .document()
                        .getId();

                fsClient.collection("Clubs")
                        .document(clubId)
                        .collection("Events")
                        .document(eventId)
                        .set(event)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    sendInvitationEmail();
                                }
                            }
                        });
            }
        });
    }

    private void setEventDate() {
        setDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetDateDialog setDateDialog = new SetDateDialog();
                setDateDialog.show(getFragmentManager(), "SetDateDialog");

            }
        });
    }

    private void setEventTime() {
        setTimeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetTimeDialog setDateDialog = new SetTimeDialog();
                setDateDialog.show(getFragmentManager(), "SetTimeDialog");
            }
        });
    }

    private void Intialise() {
        String dates, times;
        date = calendar.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dates = simpleDateFormat.format(date);
        setDateTextView.setText(dates);
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("HH:mm:ss");
        times = simpleDateFormat1.format(date).substring(0, 5);
        setTimeTextView.setText(times);

    }


    @Override
    public void onDateSelected(int date, int month, int year) {
        this.d = date;
        this.m = month;
        this.y = year;
        setDateTextView.setText(String.valueOf(date) + "/" + String.valueOf(month + 1) + "/" + String.valueOf(year));

    }

    @Override
    public void onTimeSelected(int hour, int min) {
        this.h = hour;
        this.min = min;
        setTimeTextView.setText(String.valueOf(hour) + ":" + String.valueOf(min));
    }
}
