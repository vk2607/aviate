package com.piedpipergeeks.aviate;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CreateEvent extends AppCompatActivity{

    private TextView setDateTextView,setTimeTextView;
    private Calendar calendar;
    private Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        Spinner s = (Spinner) findViewById(R.id.spinner_select_event);
        ArrayAdapter arrayAdapter=ArrayAdapter.createFromResource(this,R.array.event_arrays,android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(arrayAdapter);
        setDateTextView=(TextView)findViewById(R.id.setdate_textview);
        setTimeTextView=(TextView)findViewById(R.id.settimetextview) ;
        calendar=Calendar.getInstance();
        setEventDate();
        setEventTime();
        Intialise();

    }

    private void setEventDate() {
        setDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetDateDialog setDateDialog=new SetDateDialog();
                setDateDialog.show(getFragmentManager(),"SetDateDialog");
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
    private void Intialise(){
        String dates,times;
        date= calendar.getTime();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
        dates=simpleDateFormat.format(date);
        setDateTextView.setText(dates);
        SimpleDateFormat simpleDateFormat1=new SimpleDateFormat("HH:mm:ss");
        times=simpleDateFormat1.format(date).substring(0,5);
        setTimeTextView.setText(times);
    }


}
