package com.piedpipergeeks.aviate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import java.util.ArrayList;

public class DiscoverAdapter extends RecyclerView.Adapter<DiscoverAdapter.VHolder> {

    ArrayList<Profile> users;
//    Profile user;
    Context context;

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
    public void onBindViewHolder(@NonNull VHolder vHolder, int position) {
//        vHolder.textView.setText(data.get(position));
        Profile user = users.get(position);
        vHolder.name.setText(user.getFirstName() + " " + user.getLastName());
        vHolder.business.setText(user.getBusinessName() + ", " + user.getBusinessCategory());
        vHolder.businessDescription.setText(user.getBusinessDescription());
        vHolder.bio.setText(user.getBio());
        vHolder.haves.setText("Haves: " + user.getHaves().toString());
        vHolder.wants.setText("Wants: " + user.getWants().toString());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class VHolder extends RecyclerView.ViewHolder {

        TextView name, haves, wants, bio, business, businessDescription;
        public VHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.card_name);
            haves = (TextView) itemView.findViewById(R.id.card_haves);
            wants = (TextView) itemView.findViewById(R.id.card_wants);
            bio = (TextView) itemView.findViewById(R.id.card_bio);
            business = (TextView) itemView.findViewById(R.id.card_business);
            businessDescription = (TextView) itemView.findViewById(R.id.card_business_description);
        }
    }

}
