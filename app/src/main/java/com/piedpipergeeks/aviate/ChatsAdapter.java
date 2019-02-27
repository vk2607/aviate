package com.piedpipergeeks.aviate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.ViewHolder1> {
    private Context mcontext;
    private List<Groups> mgroups;
    int pos;

    public ChatsAdapter(Context context, List<Groups> groups) {
        this.mcontext = context;
        this.mgroups = groups;
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
        Groups groups = mgroups.get(position);
        pos = position;

        viewHolder1.groupName.setText(groups.getName());
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
