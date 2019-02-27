package com.piedpipergeeks.aviate;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;



public class RegistrationAdapter extends  RecyclerView.Adapter<RegistrationAdapter.VHolder>{
        ArrayList<Profile>users;
        Context context;
        int pos;
    public RegistrationAdapter (ArrayList<Profile> users, Context context) {
        this.users = users;
        this.context = context;
    }
    @NonNull
    @Override
    public RegistrationAdapter.VHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_registration, viewGroup, false);
        return new VHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final VHolder vHolder, int position) {
        Profile user=users.get(position);
        pos=position;
        vHolder.name.setText(user.getFirstName()+ " "+user.getLastName());
        vHolder.email.setText(user.getEmail());
        vHolder.addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectClub(vHolder);
            }
        });
    }

    private void selectClub(VHolder vHolder) {
        Profile user = users.get(vHolder.getAdapterPosition());

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class VHolder extends RecyclerView.ViewHolder {

        TextView name,email;
        Button addUser;
        public VHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.user_name_registration);
            email=(TextView)itemView.findViewById(R.id.user_email_registration);
            addUser = (Button) itemView.findViewById(R.id.add_user_button_registration);
        }
    }
}