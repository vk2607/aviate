package com.piedpipergeeks.aviate;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MembersAdapter extends RecyclerView.Adapter<MembersAdapter.VHolder> {
    //    ArrayList<Profile> members;
    ArrayList<Map> members;
    Context context;
    int pos;
    private String firstname, lastname, userId, clubId;
    private FirebaseFirestore fsClient;
    private String clubName;
//    private DataSetChangeListener listener;

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
                                                setSecretary(vHolder, String.valueOf(member.get("userId")), String.valueOf(member.get("userName")), clubId);
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
                                                setPresident(vHolder, String.valueOf(member.get("userId")), String.valueOf(member.get("userName")), clubId);
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
                                                removeFromClub(vHolder, String.valueOf(member.get("userId")), clubId);
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
        vHolder.memberCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileDialog profileDialog = new ProfileDialog();
                profileDialog.show(((Activity) context).getFragmentManager(), "profile dialog");
                profileDialog.setDetails(member);
            }
        });
    }

    private void removeFromClub(final VHolder vHolder, String userId, String clubId) {

        Map<String, Object> update = new HashMap<>();
        update.put("members", FieldValue.arrayRemove(userId));
        update.put("memberNames." + userId, FieldValue.delete());

        fsClient = FirebaseFirestore.getInstance();
        fsClient.collection("Clubs")
                .document(clubId)
                .update(update)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(context, "Removed user from club", Toast.LENGTH_SHORT).show();
                        members.remove(vHolder.getAdapterPosition());
                        notifyDataSetChanged();
                    }
                });

        fsClient.collection("Users")
                .document(userId)
                .update("clubMember", FieldValue.arrayRemove(clubId));
    }

//    private void setPresident(final VHolder vHolder, final String userId, final String userName, String clubId) {
////        final Map<String, Object> update = new HashMap<>();
////        update.put("president", userId);
////        update.put("presidentName", userName);
////        update.put("members", FieldValue.arrayRemove(userId));
////        update.put("memberNames." + userId, FieldValue.delete());
//
//        //get club data from firestore
//        //if president != null
//        //then add the secretary as a member
//        //set userId as secretary
//
//        fsClient = FirebaseFirestore.getInstance();
//        fsClient.collection("Clubs")
//                .document(clubId)
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                        if (task.isSuccessful()) {
//                            DocumentSnapshot snapshot = task.getResult();
//                            Club club = snapshot.toObject(Club.class);
//                            if (club.getPresident() != null) {
//                                club.addMember(userId, userName);
//                            }
//                            club.setPresident(userId, userName);
//                            Map<String, Object> update = club.getMap();
//                            updateClubDoc(update);
//                            update = new HashMap<>();
//                            update.put("members", FieldValue.arrayRemove(userId));
//                            update.put("memberNames." + userId, FieldValue.delete());
//                            updateClubDoc(update);
//                        }
//                    }
//                });
//
//    }

//    private void setSecretary(final VHolder vHolder, final String userId, final String userName, String clubId) {
////        final Map<String, Object> update = new HashMap<>();
////        update.put("president", userId);
////        update.put("presidentName", userName);
////        update.put("members", FieldValue.arrayRemove(userId));
////        update.put("memberNames." + userId, FieldValue.delete());
//
//        //get club data from firestore
//        //if president != null
//        //then add the secretary as a member
//        //set userId as secretary
//
//        fsClient = FirebaseFirestore.getInstance();
//        fsClient.collection("Clubs")
//                .document(clubId)
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                        if (task.isSuccessful()) {
//                            DocumentSnapshot snapshot = task.getResult();
//                            Club club = snapshot.toObject(Club.class);
//                            if (club.getSecretary() != null) {
//                                club.addMember(userId, userName);
//                            }
//                            club.setSecretary(userId, userName);
//                            Map<String, Object> update = club.getMap();
//                            updateClubDoc(update);
//                            update = new HashMap<>();
//                            update.put("members", FieldValue.arrayRemove(userId));
//                            update.put("memberNames." + userId, FieldValue.delete());
//                            updateClubDoc(update);
//                        }
//                    }
//                });
//
//    }

    private void setPresident(final VHolder vHolder, final String userId, final String userName, final String clubId) {
        Map<String, Object> update = new HashMap<>();
        update.put("president", userId);
        update.put("presidentName", userName);
        update.put("members", FieldValue.arrayRemove(userId));
        update.put("memberNames." + userId, FieldValue.delete());

        fsClient = FirebaseFirestore.getInstance();

        fsClient.collection("Clubs")
                .document(clubId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot snapshot = task.getResult();
                            Club club = snapshot.toObject(Club.class);

                            String oldPresidentId = club.getPresident();
                            String oldPresidentName = club.getPresidentName();

                            if (oldPresidentId != null) {

                                Map<String, Object> update = new HashMap<>();
                                update.put("members", FieldValue.arrayUnion(oldPresidentId));
                                update.put("memberNames." + oldPresidentId, oldPresidentName);

                                fsClient.collection("Clubs")
                                        .document(clubId)
                                        .update(update);

                                Map<String, Object> update_2 = new HashMap<>();
                                update_2.put("clubPresident", FieldValue.arrayRemove(clubId));
                                update_2.put("clubMember", FieldValue.arrayUnion(clubId));

                                fsClient.collection("Users")
                                        .document(oldPresidentId)
                                        .update(update_2);
                            }
                        }
                    }
                });

        fsClient.collection("Clubs")
                .document(clubId)
                .update(update)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        Map<String, Object> update = new HashMap<>();
                        update.put("clubMember", FieldValue.arrayRemove(clubId));
                        update.put("clubPresident", FieldValue.arrayUnion(clubId));

                        fsClient.collection("Users")
                                .document(userId)
                                .update(update);

                        members.get(vHolder.getAdapterPosition()).put("userType", "president");
                        notifyDataSetChanged();
                        //notify data set changed
                    }
                });

//        fsClient.collection("Users")
//                .document(userId)
//                .update("clubPresident", FieldValue.arrayUnion(clubId));
//
//        fsClient.collection("Clubs")
//                .document(clubId)
//                .update("members", FieldValue.arrayUnion(userId));
//
//        fsClient.collection("Clubs")
//                .document(clubId)
//                .update("memberNames." + userId, userName);

    }

    private void setSecretary(final VHolder vHolder, final String userId, final String userName, final String clubId) {
        Map<String, Object> update = new HashMap<>();
        update.put("secretary", userId);
        update.put("secretaryName", userName);
        update.put("members", FieldValue.arrayRemove(userId));
        update.put("memberNames." + userId, FieldValue.delete());
//        update.remove("memberNames." + userId);

        fsClient = FirebaseFirestore.getInstance();

        fsClient.collection("Clubs")
                .document(clubId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot snapshot = task.getResult();
                            Club club = snapshot.toObject(Club.class);

                            String oldSecretaryId = club.getSecretary();
                            String oldSecretaryName = club.getSecretaryName();

                            if (oldSecretaryId != null) {

                                Map<String, Object> update = new HashMap<>();
                                update.put("members", FieldValue.arrayUnion(oldSecretaryId));
                                update.put("memberNames." + oldSecretaryId, oldSecretaryName);

                                fsClient.collection("Clubs")
                                        .document(clubId)
                                        .update(update);

                                Map<String, Object> update_2 = new HashMap<>();
                                update_2.put("clubSecretary", FieldValue.arrayRemove(clubId));
                                update_2.put("clubMember", FieldValue.arrayUnion(clubId));

                                fsClient.collection("Users")
                                        .document(oldSecretaryId)
                                        .update(update_2);
                            }
                        }
                    }
                });

        fsClient.collection("Clubs")
                .document(clubId)
                .update(update)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        Map<String, Object> update = new HashMap<>();
                        update.put("clubMember", FieldValue.arrayRemove(clubId));
                        update.put("clubSecretary", FieldValue.arrayUnion(clubId));

                        fsClient.collection("Users")
                                .document(userId)
                                .update(update);

                        members.get(vHolder.getAdapterPosition()).put("userType", "secretary");
                        notifyDataSetChanged();
                        //notify data set changed
                    }
                });

//        fsClient.collection("Users")
//                .document(userId)
//                .update("clubPresident", FieldValue.arrayUnion(clubId));
//
//        fsClient.collection("Clubs")
//                .document(clubId)
//                .update("members", FieldValue.arrayUnion(userId));
//
//        fsClient.collection("Clubs")
//                .document(clubId)
//                .update("memberNames." + userId, userName);

    }

//    private void setSecretary(final VHolder vHolder, final String userId, final String userName, String clubId) {
//        Map<String, Object> update = new HashMap<>();
//        update.put("secretary", userId);
//        update.put("secretaryName", userName);
//        update.put("members", FieldValue.arrayRemove(userId));
//        update.remove("memberNames." + userName);
//
//        fsClient = FirebaseFirestore.getInstance();
//        fsClient.collection("Clubs")
//                .document(clubId)
//                .update(update)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//
//                        Map<String, Object> map = members.get(vHolder.getAdapterPosition());
//                        map.put("userId", userId);
//                        map.put("userType", "secretary");
//                        map.put("userName", userName);
//
//                        members.add(vHolder.getAdapterPosition(), map);
////                        members.get(vHolder.getAdapterPosition()) = map;
//                        notifyDataSetChanged();
//                        //notify data set changed
//                    }
//                });
//
//        fsClient.collection("Users")
//                .document(userId)
//                .update("clubSecretary", FieldValue.arrayUnion(clubId));
//
//        fsClient.collection("Clubs")
//                .document(clubId)
//                .update("members", FieldValue.arrayUnion(userId));
//
//        fsClient.collection("Clubs")
//                .document(clubId)
//                .update("memberNames." + userId, userName);
//
//
//    }

    public void updateClubDoc(Map update) {
        fsClient = FirebaseFirestore.getInstance();
        fsClient.collection("Clubs")
                .document(clubId)
                .update(update)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }

    @Override
    public int getItemCount() {
        return members.size();
    }

    public void setClubDetails(String clubId, String clubName) {
        this.clubId = clubId;
        this.clubName = clubName;
    }

    public class VHolder extends RecyclerView.ViewHolder {
        TextView name, threeDOt, position;
        CardView memberCard;

        public VHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.member_name_text);
            threeDOt = (TextView) itemView.findViewById(R.id.threeDot);
            position = (TextView) itemView.findViewById((R.id.member_position_text));
            memberCard = (CardView) itemView.findViewById(R.id.member_card);
        }

    }

//    @Override
//    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
//        try {
//            listener = (DataSetChangeListener) context;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(context.toString() + " must implement onDataSetChangeListener");
//        }
//        super.onAttachedToRecyclerView(recyclerView);
//    }
//
//    public interface DataSetChangeListener {
//        public void onDataSetChangeListener();
//    }

}
