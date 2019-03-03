package com.piedpipergeeks.aviate;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class SettingsHeader extends AppCompatActivity {
    TextView userNameTextView;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_header_homescreen);
        userNameTextView = (TextView) findViewById(R.id.usernamesettings_textview);
        String firstName = sharedPreferences.getString("firstName", "");
        String lastName = sharedPreferences.getString("lastName", "");
        userNameTextView.setText(firstName + " " + lastName);
    }
}
