package com.piedpipergeeks.aviate;

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
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class SetTimeDialog extends DialogFragment {
    private static final String TAG = "SetTimeDialog";
    private int hr, min;
    private SetTimeDialogListener listener;

    //    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
//        inflater=getActivity().getLayoutInflater();
//        View view=inflater.inflate(R.layout.settime_dialog_box,null);
//        return view;
//
//
//    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.settime_dialog_box, null);
        TimePicker tp = view.findViewById(R.id.time_dialog);
        tp.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                hr = hourOfDay;
                min = minute;
            }
        });
        builder.setView(view)
                .setTitle("Set time")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onTimeSelected(hr, min);
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        try {
            listener = (SetTimeDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement SetTimeDialogListener");
        }
        super.onAttach(context);
    }

    public interface SetTimeDialogListener {
        void onTimeSelected(int hour, int min);
    }
}

