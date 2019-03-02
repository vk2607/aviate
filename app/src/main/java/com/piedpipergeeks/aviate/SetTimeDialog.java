package com.piedpipergeeks.aviate;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
public class SetTimeDialog  extends DialogFragment {
    private static final String TAG = "SetTimeDialog";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.settime_dialog_box,null);
        return view;


    }
}

