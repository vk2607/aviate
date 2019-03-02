package com.piedpipergeeks.aviate;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.ViewHolder1> {
    private Context mcontext;
    private List<Club> mgroups;
    int pos;

    public ChatsAdapter(Context context, List<Club> groups) {
        this.mcontext = context;
        this.mgroups = groups;
    }

    public void updateData(ArrayList<Club> data) {
        this.mgroups = data;
    }

    @NonNull
    @Override
    public ViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(mcontext);
        View view = inflater.inflate(R.layout.recycler_chats, parent, false);
        return new ChatsAdapter.ViewHolder1(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder1 viewHolder1, int position) {
        final Club groups = mgroups.get(position);
        pos = position;
        viewHolder1.groupName.setText(groups.getName());
        viewHolder1.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mcontext, MessageActivity.class);
                intent.putExtra("clubId", groups.getClubId());
                intent.putExtra("clubName", groups.getName());
                intent.putExtra("isChatMuted", groups.getIsChatMuted());
                mcontext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mgroups.size();
    }

    public class ViewHolder1 extends RecyclerView.ViewHolder {
        public TextView groupName;
        public ImageView groupIcon;

        public ViewHolder1(View itemView) {
            super(itemView);
            groupName = itemView.findViewById(R.id.group_name_texteEdit);
//            groupIcon = itemView.findViewById(R.id.group_icon_imageView);
        }
    }
}
