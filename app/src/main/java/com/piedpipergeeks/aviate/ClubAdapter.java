package com.piedpipergeeks.aviate;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ClubAdapter extends RecyclerView.Adapter<ClubAdapter.VHolder> {
    ArrayList<Club> users;
    Context context;
//    int pos;

    public ClubAdapter(ArrayList<Club> users, Context context) {
        this.users = users;
        this.context = context;
    }

    @NonNull
    @Override
    public VHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_club, viewGroup, false);

        return new VHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final VHolder vHolder, int position) {
        Log.d("BIND", "BIND FUNCTION CALLED");
        final Club club = users.get(position);
        vHolder.name.setText(club.getName());
        vHolder.clubCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MessageActivity.class);
                intent.putExtra("clubId", club.getClubId());
                context.startActivity(intent);
            }
        });
    }

    public void updateData(ArrayList<Club> display_list) {
        this.users = display_list;
    }

    @Override
    public int getItemCount() {
//        Toast.makeText(context, "Item count: " + String.valueOf(users.size()), Toast.LENGTH_SHORT).show();
        return users.size();
    }

    public class VHolder extends RecyclerView.ViewHolder {
        TextView name;
        CardView clubCard;

        public VHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.club_name);
            clubCard = (CardView) itemView.findViewById(R.id.club_card);
        }
    }
}