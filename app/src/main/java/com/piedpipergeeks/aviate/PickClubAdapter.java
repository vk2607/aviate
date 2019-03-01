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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PickClubAdapter extends RecyclerView.Adapter<PickClubAdapter.VHolder> {

    ArrayList<Club> clubs;
    Context context;
    int pos;
    String firstname, lastname, userId;
    Bundle bundle;
    FirebaseFirestore fsClient;

    public PickClubAdapter(ArrayList<Club> clubs, Context context) {
        this.clubs = clubs;
        this.context = context;
    }

    public void setData(String firstname, String lastname, String userId) {
        this.firstname = firstname;
        this.lastname = lastname;
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
                        .setMessage("Add " + firstname + " " + lastname + " to " + club.getName() + "?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                addToClub(vHolder, userId, club.getClubId());
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .show();
            }
        });
    }

    private void addToClub(VHolder vHolder, String userId, String clubId) {
        Club club = clubs.get(vHolder.getAdapterPosition());

        Toast.makeText(context, club.getName(), Toast.LENGTH_SHORT);

        Map<String, Object> data = new HashMap<>();
        data.put("members", FieldValue.arrayUnion(userId));
        data.put("memberNames." + userId, firstname + " " + lastname);

        fsClient = FirebaseFirestore.getInstance();
        fsClient.collection("Clubs")
                .document(club.getClubId())
                .update(data)
//                .update("memberNames." + userId, "");
//                .update("members", FieldValue.arrayUnion(userId))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(context, "Added successfully", Toast.LENGTH_SHORT).show();
                            context.startActivity(new Intent(context, HomeScreenActivity.class));
                        }
                    }
                });
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
