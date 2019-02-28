package com.piedpipergeeks.aviate;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends AppCompatActivity {
    ImageView groupIconImageView;
    TextView groupNameTextView;
    LinearLayoutManager manager;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    String clubId;

    private RecyclerView mMessageRecycler;
    private ArrayList<Messages> mMessageList = new ArrayList<>();
    private MessageListAdapter mMessageAdapter;
    private ImageButton imageButton;
    private FirebaseDatabase firebaseDatabase;
    private TextView messageTextView;
    private FirebaseAuth firebaseAuth;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
//      Toolbar toolbar=findViewById(R.id.too)

        Intent intent = getIntent();
        clubId = intent.getStringExtra("clubId");

        imageButton = (ImageButton) findViewById(R.id.send_messages_button);
        messageTextView=(TextView)findViewById(R.id.edittext_chatbox);
        mMessageRecycler = (RecyclerView) findViewById(R.id.reyclerview_message_list);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setStackFromEnd(true);
        mMessageRecycler.setLayoutManager(manager);

        mMessageAdapter = new MessageListAdapter(this, mMessageList);
        mMessageRecycler.setAdapter(mMessageAdapter);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendMessages();
            }
        });

        mMessageAdapter.notifyDataSetChanged();


    }
    public void SendMessages(){
        Messages message = new Messages();
//        Messages message2 = new Messages();
        String text=messageTextView.getText().toString();
        if(!text.isEmpty()) {
            Profile user = new Profile();
//        Profile user2 = new Profile();

            user.setFirstName("Vinod");
            user.setUserId(FirebaseAuth.getInstance().getUid());
            message.setMessage(text);
            message.setSender(user);
            mMessageAdapter.addMessage(message);
            messageTextView.setText("");
//        message.setMessage("HELLO");
//        message.setSender(user1);
//
//        user2.setFirstName("Adwait");
//        user2.setUserId("blah blah");
//        message2.setMessage("HIIIIIIIIII");
//        message2.setSender(user2);
//
//
//        mMessageAdapter.addMessage(message);
//        mMessageAdapter.addMessage(message2);
//        for(int i = 0; i < 20; i++) {
//            mMessageAdapter.addMessage(message);
//        }
            mMessageAdapter.notifyDataSetChanged();
        }

    }
}
