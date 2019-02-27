package com.piedpipergeeks.aviate;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PickClubAdapter extends RecyclerView.Adapter<PickClubAdapter.VHolder> {

    ArrayList<Club> clubs;
    Context context;
    int pos;
    String firstname, userId;
    Bundle bundle;

    public PickClubAdapter(ArrayList<Club> clubs, Context context) {
        this.clubs = clubs;
        this.context = context;
    }

    public void setData(String firstname, String userId) {
        this.firstname = firstname;
        this.userId = userId;
    }

    public VHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_pick_club, viewGroup, false);
        return new VHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final VHolder vHolder, int position) {
        final Club club = clubs.get(position);
        pos = position;
        vHolder.clubname.setText(club.getName());
        vHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setIcon(null)
//                        .setTitle("Add to "+ club.getName())
                        .setMessage("Are you sure you want to add " + firstname + "to " + club.getName() + "?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .show();
            }
        });
    }

    private void openClub(PickClubAdapter.VHolder vHolder) {
        Toast.makeText(context, String.valueOf(vHolder.getAdapterPosition()), Toast.LENGTH_SHORT).show();
        Club club = clubs.get(vHolder.getAdapterPosition());


    }

    @Override
    public int getItemCount() {
        return clubs.size();
    }

    public class VHolder extends RecyclerView.ViewHolder {

        TextView clubname;
        CardView cardView;

        public VHolder(@NonNull View itemView) {
            super(itemView);
            clubname = (TextView) itemView.findViewById(R.id.recycler_club_name);
            cardView = (CardView) itemView.findViewById(R.id.recycler_club_card);
        }
    }
}
