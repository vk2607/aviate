package com.piedpipergeeks.aviate;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Map;

public class MembersAdapter extends RecyclerView.Adapter<MembersAdapter.VHolder> {
    //    ArrayList<Profile> members;
    ArrayList<Map> members;
    Context context;
    int pos;
    String firstname, lastname, userId, clubId;
    FirebaseFirestore fsClient;

    public MembersAdapter(ArrayList<Map> members, Context context) {
        this.members = members;
        this.context = context;
    }

    public void setClubId(String clubId) {
        this.clubId = clubId;
    }

    @NonNull
    @Override
    public VHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_members, viewGroup, false);
        final TextView position = (TextView) view.findViewById(R.id.threeDot);

        SharedPreferences pref = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        if (pref.getString("userType", "").equals("admin")) {
            position.setVisibility(View.VISIBLE);
        }

        return new VHolder(view);
    }

    @Override

    public void onBindViewHolder(@NonNull final VHolder vHolder, int position) {
//         final Profile member = members.get(position);
        final Map<String, Object> member = members.get(position);
//         pos = position;

        vHolder.name.setText(String.valueOf(member.get("userName")));

        if (member.get("userType").equals("admin")) {
            vHolder.position.setText("Admin");
        } else if (member.get("userType").equals("guest")) {
            vHolder.position.setText("Guest");
        } else if (member.get("userType").equals("president")) {
            vHolder.position.setText("President");
        } else if (member.get("userType").equals("secretary")) {
            vHolder.position.setText("Secretary");
        } else {
            vHolder.position.setText("");
        }

        vHolder.threeDOt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, v);
                MenuInflater menuInflater = popupMenu.getMenuInflater();
                menuInflater.inflate(R.menu.three_dot_menu, popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_secretary:
                                new AlertDialog.Builder(context)
                                        .setIcon(null)
                                        .setMessage("Set " + String.valueOf(member.get("userName")) + " as Secretary?")
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                addSecretary(vHolder, String.valueOf(member.get("userId")), clubId);
                                            }
                                        })
                                        .setNegativeButton(android.R.string.no, null)
                                        .show();
                                break;
                            case R.id.menu_president:
                                new AlertDialog.Builder(context)
                                        .setIcon(null)
                                        .setMessage("Set " + String.valueOf(member.get("userName")) + " as President?")
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                addSecretary(vHolder, String.valueOf(member.get("userId")), clubId);
                                            }
                                        })
                                        .setNegativeButton(android.R.string.no, null)
                                        .show();
                                break;
                            case R.id.menu_remove:
                                new AlertDialog.Builder(context)
                                        .setIcon(null)
                                        .setMessage("Remove " + String.valueOf(member.get("userName")) + " from group? ")
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                addSecretary(vHolder, String.valueOf(member.get("userId")), clubId);
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

    private void addSecretary(VHolder vHolder, String userId, String clubId) {


    }

    @Override
    public int getItemCount() {
        return members.size();
    }

    public class VHolder extends RecyclerView.ViewHolder {
        TextView name, threeDOt, position;

        public VHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.member_name_text);
            threeDOt = (TextView) itemView.findViewById(R.id.threeDot);
            position = (TextView) itemView.findViewById((R.id.member_position_text));



        }

    }
}
