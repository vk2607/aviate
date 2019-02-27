package com.piedpipergeeks.aviate;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PickClubAdapter extends RecyclerView.Adapter<PickClubAdapter.VHolder> {

    ArrayList<Club_1> club1s;
    Context context;
    int pos;

    public PickClubAdapter(ArrayList<Club_1> users, Context context){
        this.club1s =users;
        this.context=context;
    }

    public VHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_pick_club, viewGroup, false);
        return new VHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final VHolder vHolder, int position) {
        Club_1 club1 = club1s.get(position);
        pos = position;
        vHolder.clubname.setText(club1.getClubname());
        vHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setIcon(null)

                        .setTitle("Add to club1")
                        .setMessage("Are you sure you want to add in club1")
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
        Club_1 club1 = club1s.get(vHolder.getAdapterPosition());


    }

    @Override
    public int getItemCount() {
        return club1s.size();
    }

    public class VHolder extends RecyclerView.ViewHolder {

        TextView clubname;
        CardView cardView;
        public VHolder(@NonNull View itemView) {
            super(itemView);
          clubname=(TextView) itemView.findViewById(R.id.recycler_club_name);
          cardView = (CardView) itemView.findViewById(R.id.recycler_club_card);
        }
    }
}
