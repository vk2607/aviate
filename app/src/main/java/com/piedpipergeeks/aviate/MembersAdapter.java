package com.piedpipergeeks.aviate;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MembersAdapter extends RecyclerView.Adapter<MembersAdapter.VHolder> {
    ArrayList<Profile> members;
    Context context;
    int pos;
    String firstname, lastname, userId, clubId;

    public MembersAdapter(ArrayList<Profile> members, Context context) {
        this.members = members;
        this.context = context;
    }

    public void setClubId (String clubId) {
        this.clubId = clubId;
    }

    @NonNull
    @Override
    public VHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_members, viewGroup, false);
        return new VHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final VHolder vHolder, int position) {
        final Profile member = members.get(position);
        pos = position;
        vHolder.name.setText(member.getFirstName() + " " + member.getLastName());
        vHolder.threeDOt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu=new PopupMenu(context,v);
                MenuInflater menuInflater=popupMenu.getMenuInflater();
                menuInflater.inflate(R.menu.three_dot_menu,popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_secretary:
                                new AlertDialog.Builder(context)
                                        .setIcon(null)
                                        .setMessage("Set " + member.getFirstName() + " " +member.getLastName() + " as Secretary ?")
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                AddSecretary(vHolder,member.getUserId(), clubId);
                                            }
                                        })
                                        .setNegativeButton(android.R.string.no, null)
                                        .show();
                                break;
                            case R.id.menu_president:
                                new AlertDialog.Builder(context)
                                        .setIcon(null)
                                        .setMessage("Set " + member.getFirstName() + " " +member.getLastName() + " as President ?")
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                AddSecretary(vHolder,member.getUserId(), clubId);
                                            }
                                        })
                                        .setNegativeButton(android.R.string.no, null)
                                        .show();
                                break;
                            case R.id.menu_remove:
                                new AlertDialog.Builder(context)
                                        .setIcon(null)
                                        .setMessage("Remove " + member.getFirstName() + " " +member.getLastName() + " from group ? ")
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                AddSecretary(vHolder,member.getUserId(), clubId);
                                            }
                                        })
                                        .setNegativeButton(android.R.string.no, null)
                                        .show();
                                break;
                        }
                        return true;
                    }
                });
            }
        });
    }

    private void AddSecretary(VHolder vHolder, String userId, String clubId) {

    }

    @Override
    public int getItemCount() {
        return members.size();
    }

    public class VHolder extends RecyclerView.ViewHolder {
        TextView name,threeDOt;

        public VHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.member_name_text);
            threeDOt=(TextView)itemView.findViewById(R.id.threeDot);
        }

    }
}
