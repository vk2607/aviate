package com.piedpipergeeks.aviate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;

import java.text.SimpleDateFormat;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.ls.LSException;

import java.util.ArrayList;
import java.util.List;

public class MessageListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
    private Context mContext;
    private List<Messages> mMessageList;
    private FirebaseDatabase firebaseDatabase;
    private ArrayList<Messages> chats;
    public void IntialiseDatabase(){
        firebaseDatabase=FirebaseDatabase.getInstance();
    }
    public void UpdateMessages(){

    }

    public MessageListAdapter(Context context, List<Messages> messagesList) {
        mContext = context;
        mMessageList = messagesList;

    }

    public void addMessage(Messages message) {
        mMessageList.add(message);
    }

    public void setMessages(List<Messages> messages) {
        this.mMessageList = messages;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view;
        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_message_sent, viewGroup, false);

            return new SentMessageHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_message_received, viewGroup, false);

            return new ReceivedMessageHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Messages message = (Messages) mMessageList.get(position);

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(message);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    @Override
    public int getItemViewType(int position) {
        Messages message = (Messages) mMessageList.get(position);

        if (message.getSender().getUserId().equals(FirebaseAuth.getInstance().getUid())) {
            // If the current user is the sender of the message
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            // If some other user sent the message
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText, nameText;
        ImageView profileImage;

        public ReceivedMessageHolder(@NonNull View itemView) {
            super(itemView);
            messageText = (TextView) itemView.findViewById(R.id.text_message_body);
            timeText = (TextView) itemView.findViewById(R.id.text_message_time);
            nameText = (TextView) itemView.findViewById(R.id.text_message_name);
        }

        void bind(Messages message) {
            messageText.setText(message.getMessage());

            // Format the stored timestamp into a readable String using method.
            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            String time = dateFormat.format(message.getDate()).substring(0, 5);
            timeText.setText(time);
            nameText.setText(message.getSender().getFirstName() + " " + message.getSender().getLastName());


        }
    }

    private class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;

        SentMessageHolder(View itemView) {
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.text_message_body);
            timeText = (TextView) itemView.findViewById(R.id.text_message_time);
        }

        void bind(Messages message) {
            messageText.setText(message.getMessage());

            // Format the stored timestamp into a readable String using method.
            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            timeText.setText(dateFormat.format(message.getDate()).substring(0, 5));
        }
    }

}
