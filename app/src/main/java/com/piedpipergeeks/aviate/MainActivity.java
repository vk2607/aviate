package com.piedpipergeeks.aviate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {


    FirebaseAuth mAuth;
    FirebaseUser currentUser;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startActivity(new Intent(MainActivity.this, PickClubActivity.class));

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//             public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        startActivity(new Intent(MainActivity.this, HomeScreenActivity.class));
        mAuth = FirebaseAuth.getInstance();
        DefaultLogin();

    }

//    @Override
//    public void onFragmentInteraction(Uri uri) {
//
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void toSignInActivity(View view) {
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }

    public void toSignUpeEntrepreneurActivity(View view) {
        startActivity(new Intent(MainActivity.this, RegisterEntrepreneurActivity.class));
    }

    public void toSignUpdeAsraActivity(View view) {
        startActivity(new Intent(MainActivity.this, RegisterdeAsraActivity.class));
    }

    public void DefaultLogin() {
        currentUser = mAuth.getCurrentUser();
        if (currentUser != null && currentUser.isEmailVerified()) {
//             Intent homeIntent = new Intent(MainActivity.this, HomeScreenActivity.class);
//             startActivity(homeIntent);
//             finish();

            Intent clubIntennt = new Intent(MainActivity.this, PickClubActivity.class);
            startActivity(clubIntennt);
            finish();
        }
    }
}
