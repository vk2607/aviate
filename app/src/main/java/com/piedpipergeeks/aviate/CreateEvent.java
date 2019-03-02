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


import java.util.Calendar;

public class CreateEvent extends AppCompatActivity{

    private TextView setDateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        Spinner s = (Spinner) findViewById(R.id.spinner_select_event);
        ArrayAdapter arrayAdapter=ArrayAdapter.createFromResource(this,R.array.event_arrays,android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(arrayAdapter);
        setDateTextView=(TextView)findViewById(R.id.setdate_textview);
        setEventDate();

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


}
