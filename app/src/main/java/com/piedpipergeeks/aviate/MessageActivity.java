package com.piedpipergeeks.aviate;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends AppCompatActivity {
    ImageView groupIconImageView;
    TextView groupNameTextView;
    LinearLayoutManager manager;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    private RecyclerView mMessageRecycler;
    private ArrayList<Messages> mMessageList = new ArrayList<>();
    private MessageListAdapter mMessageAdapter;
    private ImageButton imageButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
//      Toolbar toolbar=findViewById(R.id.too)
        imageButton = (ImageButton) findViewById(R.id.send_messages_button);

        mMessageRecycler = (RecyclerView) findViewById(R.id.reyclerview_message_list);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setStackFromEnd(true);
        mMessageRecycler.setLayoutManager(manager);

        mMessageAdapter = new MessageListAdapter(this, mMessageList);
        mMessageRecycler.setAdapter(mMessageAdapter);

        Messages message = new Messages();
        Messages message2 = new Messages();

        Profile user1 = new Profile();
        Profile user2 = new Profile();

        user1.setFirstName("Vinod");
        user1.setUserId(FirebaseAuth.getInstance().getUid());
        message.setMessage("HELLO");
        message.setSender(user1);

        user2.setFirstName("Adwait");
        user2.setUserId("blah blah");
        message2.setMessage("HIIIIIIIIII");
        message2.setSender(user2);


        mMessageAdapter.addMessage(message);
        mMessageAdapter.addMessage(message2);
        for(int i = 0; i < 20; i++) {
            mMessageAdapter.addMessage(message);
        }

        mMessageAdapter.notifyDataSetChanged();

    }
}
