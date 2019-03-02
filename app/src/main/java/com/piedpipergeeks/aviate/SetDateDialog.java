package com.piedpipergeeks.aviate;

//

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class SetDateDialog extends DialogFragment {
    private static final String TAG = "SetDateDialog";
    CalendarView calendar;
    private int y, m, d;
    Button okButton;
    TextView textView;
    private SetDateDialogListener listener;
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
//
//        inflater = getActivity().getLayoutInflater();
//        View view = inflater.inflate(R.layout.setdate_dialog_box, null);
//        calendar = view.findViewById(R.id.calendar_dialog);
//        okButton = view.findViewById(R.id.okdatecreateevent_button);
//        Toast.makeText(getActivity(), "select 1", Toast.LENGTH_SHORT).show();
//        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
//            @Override
//            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
//                textView.setText(dayOfMonth + "/" + month + "/" + year);
//            }
//        });
//        return view;
//    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.setdate_dialog_box, null);

        CalendarView calendarView = view.findViewById(R.id.calendar_dialog);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                y = year;
                m = month;
                d = dayOfMonth;
            }
        });

        builder.setView(view)
                .setTitle("Set date")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onDateSelected(d, m, y);
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        try {
            listener = (SetDateDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement SetDateDialogListener");
        }
        super.onAttach(context);
    }

    public interface SetDateDialogListener {
        void onDateSelected(int date, int month, int year);
    }
}
