package com.piedpipergeeks.aviate;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

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

        Messages message = new Messages();
        Messages message2 = new Messages();
        Profile users = new Profile();

        users.setFirstName("Vinod");
        message.setMessage("HELLO");
        message.setSender(users);
        mMessageList.add(message);

        users.setFirstName("Vinod");
        message2.setMessage("HIIIIIIIIII");
        message2.setSender(users);
        mMessageList.add(message2);


        mMessageRecycler = (RecyclerView) findViewById(R.id.reyclerview_message_list);
        mMessageRecycler.setLayoutManager(new LinearLayoutManager(this));
        mMessageAdapter = new MessageListAdapter(this, mMessageList);
        mMessageRecycler.setAdapter(mMessageAdapter);


//        mMessageAdapter = new MessageListAdapter(this, mMessageList);


    }
}
