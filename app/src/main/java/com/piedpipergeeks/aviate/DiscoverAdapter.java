package com.piedpipergeeks.aviate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DiscoverAdapter extends RecyclerView.Adapter<DiscoverAdapter.VHolder> {

    ArrayList<Profile> users;
//    Profile user;
    Context context;
    int pos;

    public DiscoverAdapter (ArrayList<Profile> users, Context context) {
        this.users = users;
        this.context = context;
    }

    @NonNull
    @Override
    public VHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_discover, viewGroup, false);
        return new VHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final VHolder vHolder, int position) {
//        vHolder.textView.setText(data.get(position));
        Profile user = users.get(position);
        pos = position;

        vHolder.name.setText(user.getFirstName() + " " + user.getLastName());
        vHolder.business.setText(user.getBusinessName() + ", " + user.getBusinessCategory());
        vHolder.businessDescription.setText(user.getBusinessDescription());
        vHolder.bio.setText(user.getBio());
        vHolder.haves.setText(user.getHaves().toString());
        vHolder.wants.setText(user.getWants().toString());
        vHolder.viewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                launch profile view of that user

                openProfile(vHolder);

            }
        });
    }

    private void openProfile(VHolder vHolder) {
        Toast.makeText(context, String.valueOf(vHolder.getAdapterPosition()), Toast.LENGTH_SHORT).show();

        Profile user = users.get(vHolder.getAdapterPosition());

//        TODO: Launch a fragment or activity that displays the enter profile of user
//        with buttons like send connection request, spam etc

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class VHolder extends RecyclerView.ViewHolder {

        TextView name, haves, wants, bio, business, businessDescription;
        Button viewProfile;

        public VHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.card_name);
            haves = (TextView) itemView.findViewById(R.id.card_haves);
            wants = (TextView) itemView.findViewById(R.id.card_wants);
            bio = (TextView) itemView.findViewById(R.id.card_bio);
            business = (TextView) itemView.findViewById(R.id.card_business);
            businessDescription = (TextView) itemView.findViewById(R.id.card_business_description);
            viewProfile = (Button) itemView.findViewById(R.id.card_view_profile);
        }
    }

//    public void viewProfile(View view) {
//
//    }

}
