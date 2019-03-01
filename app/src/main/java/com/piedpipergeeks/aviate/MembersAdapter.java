package com.piedpipergeeks.aviate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MembersAdapter extends RecyclerView.Adapter<MembersAdapter.VHolder> {
    ArrayList<Profile> members;
    Context context;
    int pos;

    public MembersAdapter(ArrayList<Profile> members, Context context) {
        this.members = members;
        this.context = context;
    }

    @NonNull
    @Override
    public VHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_members, viewGroup, false);
        return new VHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VHolder vHolder, int position) {
        Profile member = members.get(position);
        pos = position;
        vHolder.name.setText(member.getFirstName() + " " + member.getLastName());
    }

    @Override
    public int getItemCount() {
        return members.size();
    }

    public class VHolder extends RecyclerView.ViewHolder {
        TextView name;

        public VHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.member_name_text);
        }
    }
}
