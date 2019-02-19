package com.piedpipergeeks.aviate;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeScreenActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private Button logOutButton;
    private FirebaseAuth hAuth;
    private FirebaseFirestore fsClient;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_updates:
//                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_search:
//                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_discover:
//                    mTextMessage.setText(R.string.title_notifications);
                    return true;
                case R.id.navigation_chats:
                    return true;
                case R.id.navigation_my_profile:
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.getMenu().getItem(2).setChecked(true);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        hAuth = FirebaseAuth.getInstance();
        Intialize();
        LogOut();

        fsClient = FirebaseFirestore.getInstance();
    }

    protected void Intialize() {
        logOutButton = (Button) findViewById(R.id.logout_homescreen_button);
    }

    protected void LogOut() {
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeScreenActivity.this, "Logging Out...", Toast.LENGTH_SHORT).show();
                hAuth.signOut();
                Intent logOutIntent = new Intent(HomeScreenActivity.this, MainActivity.class);
                startActivity(logOutIntent);
                finish();
            }
        });
    }

}
