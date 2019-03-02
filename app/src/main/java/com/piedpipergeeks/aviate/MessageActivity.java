package com.piedpipergeeks.aviate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.Calendar;
import java.util.Date;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.text.DateFormat;

import java.text.SimpleDateFormat;

import java.text.DateFormat;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends AppCompatActivity {
    ImageView groupIconImageView;
    TextView groupNameTextView;
    LinearLayoutManager manager;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    String clubId, clubName, clubInfo;

    ArrayList<Messages> chats;

    private RecyclerView mMessageRecycler;
    private ArrayList<Messages> mMessageList = new ArrayList<>();
    private MessageListAdapter mMessageAdapter;
    private ImageButton imageButton;
    private FirebaseDatabase firebaseDatabase;
    private TextView messageTextView;
    private SharedPreferences sharedPreferences;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore fsClient;
    private String time;
    private SharedPreferences pref;
    private boolean isChatMuted;
    private Menu pinMenu;
    private String muteChat = "Mute Chat";
    private String unmuteChat = "Unmute Chat";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        Intent intent = getIntent();
        clubId = (String) intent.getStringExtra("clubId");
        clubName = intent.getStringExtra("clubName");
        clubInfo = intent.getStringExtra("clubInfo");
        isChatMuted = Boolean.valueOf(intent.getStringExtra("isChatMuted"));

        if (clubId != null) {

            pref = getSharedPreferences("MessageActivityPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("clubId", clubId);
            editor.putString("clubName", clubName);
            editor.putString("isChatMuted", String.valueOf(isChatMuted));
            editor.putString("clubInfo", clubInfo);
            editor.apply();
        } else {
            pref = getSharedPreferences("MessageActivityPrefs", Context.MODE_PRIVATE);
            clubId = pref.getString("clubId", null);
            clubName = pref.getString("clubName", null);
            isChatMuted = Boolean.valueOf(pref.getString("isChatMuted", null));
            clubInfo = pref.getString("clubInfo", null);
        }


//        getSupportActionBar().setTitle(clubName);

//        Toolbar actionBar = findViewById(R.id.message_toolbar);
//        actionBar.setTitle(clubName);
//        actionBar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });


        imageButton = (ImageButton) findViewById(R.id.send_messages_button);
        messageTextView = (TextView) findViewById(R.id.edittext_chatbox);
        mMessageRecycler = (RecyclerView) findViewById(R.id.reyclerview_message_list);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        fsClient = FirebaseFirestore.getInstance();

        setOnDataChangeListener();

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setStackFromEnd(true);
        manager.setReverseLayout(false);
        mMessageRecycler.setLayoutManager(manager);

        mMessageAdapter = new MessageListAdapter(this, mMessageList);
        mMessageAdapter.IntialiseDatabase();
        mMessageRecycler.setAdapter(mMessageAdapter);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendMessages();
            }
        });
        UpdateMessages();
        mMessageAdapter.notifyDataSetChanged();
        getUpcomingEvent();

        invalidateOptionsMenu();


    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

//        Toast.makeText(this, "Callback received", Toast.LENGTH_SHORT).show();

        MenuItem chat_item = pinMenu.findItem(R.id.action_mute_chat);

        if (sharedPreferences.getString("userType", "user").equals("user")) {
            chat_item.setVisible(false);

            if (isChatMuted) {
//                Toast.makeText(this, "Chat muted", Toast.LENGTH_SHORT).show();
                findViewById(R.id.layout_chatbox).setVisibility(View.GONE);
                findViewById(R.id.layout_no_chatbox).setVisibility(View.VISIBLE);
            } else {
                findViewById(R.id.layout_chatbox).setVisibility(View.VISIBLE);
                findViewById(R.id.layout_no_chatbox).setVisibility(View.GONE);
            }

        } else {
            if (isChatMuted) {

                chat_item.setTitle(unmuteChat);
            } else {
                chat_item.setTitle(muteChat);
            }
        }

        return super.onPrepareOptionsMenu(menu);
    }

    private void setOnDataChangeListener() {
        fsClient.collection("Clubs")
                .document(clubId)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        Club club = documentSnapshot.toObject(Club.class);
                        Toast.makeText(MessageActivity.this, String.valueOf(club.getIsChatMuted()), Toast.LENGTH_SHORT).show();
                        isChatMuted = club.getIsChatMuted();
                        invalidateOptionsMenu();

//                        MenuItem chat_item = pinMenu.findItem(R.id.action_mute_chat);

//                        if (sharedPreferences.getString("userType", "user").equals("user")) {
//                            chat_item.setVisible(false);
//
//                            if (isChatMuted) {
//                                Toast.makeText(MessageActivity.this, "Chat muted", Toast.LENGTH_SHORT).show();
//                                findViewById(R.id.layout_chatbox).setVisibility(View.GONE);
//                                findViewById(R.id.layout_no_chatbox).setVisibility(View.VISIBLE);
//                            } else {
//                                findViewById(R.id.layout_chatbox).setVisibility(View.VISIBLE);
//                                findViewById(R.id.layout_no_chatbox).setVisibility(View.GONE);
//                            }
//
//                        } else {
//                            if (isChatMuted) {
//                                chat_item.setTitle(unmuteChat);
//                            } else {
//                                chat_item.setTitle(muteChat);
//                            }
//                        }


                    }
                });
    }

    private void getUpcomingEvent() {

        Timestamp currentTime = new Timestamp(new Date().getTime() / 1000, 0);

//        fsClient = FirebaseFirestore.getInstance();
        fsClient.collection("Clubs")
                .document(clubId)
                .collection("Events")
                .whereGreaterThan("timestamp", currentTime)
                .orderBy("timestamp", Query.Direction.ASCENDING)
                .limit(1)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot snapshot : task.getResult()) {
                                Event event = snapshot.toObject(Event.class);
                                showUpcomingEvent(event);
                            }

                        }
                    }
                });
    }

    private void showUpcomingEvent(final Event event) {

        findViewById(R.id.pinned_event_layout).setVisibility(View.VISIBLE);
        ((TextView) findViewById(R.id.pinned_event_type)).setText("Upcoming event: " + String.valueOf(event.getEventType()));

        Date date = new Date(((Timestamp) event.getTimestamp()).getSeconds() * 1000);
        final String timeOfEvent = DateFormat.getTimeInstance(DateFormat.SHORT).format(date);
        final String dateOfEvent = DateFormat.getDateInstance().format(date);

        ((TextView) findViewById(R.id.pinned_event_time)).setText(dateOfEvent + " at " + timeOfEvent);

        ((CardView) findViewById(R.id.pinned_event_layout)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventDialog dialog = new EventDialog();
//                dialog.setDetails(String.valueOf(snapshot.get("eventType")), dateOfEvent + " at " + timeOfEvent);
                dialog.show(getFragmentManager(), "event dialog");
                dialog.setDetails(event);
            }
        });


    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        Toast.makeText(this, "onResume method called", Toast.LENGTH_SHORT).show();
//
////        if (clubId == null) {
//        pref = getSharedPreferences("MessageActivityPrefs", Context.MODE_PRIVATE);
//        clubId = pref.getString("clubId", "");
//        clubName = pref.getString("clubName", "");
////        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        pinMenu = menu;
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_club_info:
                Intent intent = new Intent(MessageActivity.this, ClubDetails.class);
                intent.putExtra("clubName", clubName);
                intent.putExtra("clubId", clubId);
                intent.putExtra("clubInfo", clubInfo);

                Event event = new Event();
                event.setEventType("Webinar");
                Date date = new Date();
                event.setTimestamp(new Timestamp(date.getTime() / 1000, 0));
                startActivity(intent);
                break;

            case R.id.action_mute_chat:
                fsClient = FirebaseFirestore.getInstance();

                fsClient.collection("Clubs")
                        .document(clubId)
                        .update("isChatMuted", !isChatMuted)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
//                                    isChatMuted = !isChatMuted;
//                                    invalidateOptionsMenu();
                                }
                            }
                        });
                break;
//                if (isChatMuted) {
//                    fsClient.collection("Clubs")
//                            .document(clubId)
//                            .update("isChatMuted", false)
//                            .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    if (task.isSuccessful()) {
//                                        isChatMuted = ! isChatMuted;
//                                        invalidateOptionsMenu();
//                                    }
//                                }
//                            });
//
//                } else {
//                    fsClient.collection("Clubs")
//                            .document(clubId)
//                            .update("isChatMuted", true)
//                            .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    if (task.isSuccessful()) {
//                                        isChatMuted = !isChatMuted;
//                                        invalidateOptionsMenu();
//                                    }
//                                }
//                            });
//                }
        }

        return super.onOptionsItemSelected(item);
    }

    private void UpdateMessages() {
//        chats = new ArrayList<>();
//        firebaseDatabase.getReference("Clubs")
//                .child(clubId)
//                .addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////                        chats.clear();
//                        mMessageAdapter.notifyDataSetChanged();
//                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                            Messages chat = dataSnapshot1.getValue(Messages.class);
//
//                            mMessageAdapter.addMessage(chat);
//                            mMessageAdapter.notifyDataSetChanged();
//
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });

        final List<Messages> messages = new ArrayList<>();
        firebaseDatabase.getReference("Clubs")
                .child(clubId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        messages.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Messages message = snapshot.getValue(Messages.class);
                            messages.add(message);
                        }
                        mMessageAdapter.setMessages(messages);
                        mMessageAdapter.notifyDataSetChanged();
                        mMessageRecycler.scrollToPosition(mMessageAdapter.getItemCount() - 1);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }

    public void SendMessages() {
        Messages message = new Messages();
//        Messages message2 = new Messages();
        String text = messageTextView.getText().toString();
        if (!text.isEmpty()) {
            Profile user = new Profile();
            Calendar cal = Calendar.getInstance();

            Date date = cal.getTime();

//            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
//
//            time=dateFormat.format(date).substring(0,5);

//        Profile user2 = new Profile();

            user.setFirstName(sharedPreferences.getString("firstName", ""));
            user.setLastName(sharedPreferences.getString("lastName", ""));
            user.setUserId(FirebaseAuth.getInstance().getUid());
            message.setMessage(text);
            message.setSender(user);
            message.setDate(date);
            mMessageAdapter.addMessage(message);
            mMessageAdapter.notifyDataSetChanged();
            mMessageRecycler.scrollToPosition(mMessageAdapter.getItemCount() - 1);

            messageTextView.setText("");
            String messageUniqueKey = firebaseDatabase.getReference("Clubs").child(clubId).push().getKey();
            firebaseDatabase.getReference("Clubs").child(clubId).child(messageUniqueKey).setValue(message);
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

//            messageTextView.setText(clubId);
        }

    }
}
