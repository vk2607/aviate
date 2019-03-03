package com.piedpipergeeks.aviate;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
//import android.support.v4.app.FragmentManager;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
//import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


public class HomeScreenActivity extends AppCompatActivity implements ClubFragment.OnFragmentInteractionListener, RegistrationFragment.OnFragmentInteractionListener,ChatsFragment.OnFragmentInteractionListener, NavigationView.OnNavigationItemSelectedListener {


    private TextView mTextMessage;
    private Button logOutButton;
    private FirebaseAuth hAuth;
    private FirebaseFirestore fsClient;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private FirebaseAuth firebaseAuth;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
//                case R.id.navigation_updates:
//                    return true;
//                case R.id.navigation_search:
//                    fragmentTransaction = fragmentManager.beginTransaction();
//                    fragmentTransaction.replace(R.id.fragment_area, new SearchFragment());
//                    fragmentTransaction.commit();
//                    return true;
//                case R.id.navigation_discover:
//                    fragmentTransaction = fragmentManager.beginTransaction();
//                    fragmentTransaction.replace(R.id.fragment_area, new DiscoverFragment());
//                    fragmentTransaction.commit();
//                    return true;
                case R.id.navigation_chats:
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_area, new ClubFragment());
//                    fragmentTransaction.replace(R.id.fragment_area, new ChatsFragment());
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_my_profile:
                    fragmentTransaction=fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_area,new RegistrationFragment());
                    fragmentTransaction.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
//        getActionBar().setTitle(R.string.app_name);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_bar);
//        navigation.getMenu().getItem(2).setChecked(true);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        hAuth = FirebaseAuth.getInstance();
//        Intialize();
//        LogOut();

        fsClient = FirebaseFirestore.getInstance();
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_area, new ClubFragment());
        fragmentTransaction.commit();
        firebaseAuth=FirebaseAuth.getInstance();
        setNavigationViewListner();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        // Do not delete me
    }

//    protected void Intialize() {
//        logOutButton = (Button) findViewById(R.id.logout_homescreen_button);
//    }

    //    protected void LogOut() {
//        logOutButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(HomeScreenActivity.this, "Logging Out...", Toast.LENGTH_SHORT).show();
//                hAuth.signOut();
//                Intent logOutIntent = new Intent(HomeScreenActivity.this, MainActivity.class);
//                startActivity(logOutIntent);
//                finish();
//            }
//        });
//    }
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId())
        {
            case R.id.nav_sendfeedback:
                Intent intent=new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "aviateapp@gmail.com", null));
                intent.putExtra(Intent.EXTRA_SUBJECT,"Feedback about service");
                startActivity(Intent.createChooser(intent,null));
                break;
            case R.id.nav_settings:
                Intent settings=new Intent(HomeScreenActivity.this,Settings.class);
                startActivity(settings);
                break;
            case R.id.nav_logout:
                new AlertDialog.Builder(this)
                        .setIcon(null)
                        .setTitle("Log Out")
                        .setMessage("Are you sure you want to log out ?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                //startActivity();
                                firebaseAuth.signOut();
                                Intent intent1=new Intent(HomeScreenActivity.this,MainActivity.class);
                                startActivity(intent1);
                                finish();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .show();
                break;
        }
        return true;
    }
    private void setNavigationViewListner() {
        NavigationView navigationView=(NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
}
