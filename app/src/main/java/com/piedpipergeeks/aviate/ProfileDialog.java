package com.piedpipergeeks.aviate;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

public class ProfileDialog extends DialogFragment {

    private Profile profile;
    private View view;
    private TextView emailtxt,locationtxt,categorytxt;
    private String name;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater=getActivity().getLayoutInflater();
        view=inflater.inflate(R.layout.dialog_profile,null);

        emailtxt=(TextView)view.findViewById(R.id.dialog_email);
        locationtxt=(TextView)view.findViewById(R.id.dialog_location);
        categorytxt=(TextView)view.findViewById(R.id.dialog_category);
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setView(view)
                .setTitle(name);

//        Problem occurs
//        emailtxt.setText(profile.getEmail());
//        locationtxt.setText(profile.getCityName());
//        categorytxt.setText(profile.getBusinessCategory());

        return builder.create();
    }
    public void setDetails(Map map){
        name=(String)map.get("userName");
    }
    public void setDetails(Profile profile){
        this.profile=profile;
    }

}
