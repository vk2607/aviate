package com.piedpipergeeks.aviate;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DiscoverFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DiscoverFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DiscoverFragment extends Fragment {

    View v;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    LinearLayoutManager manager;
    DiscoverAdapter discoverAdapter;
    Boolean isScrolling = false;

    String USER_ID;

    FirebaseFirestore fsClient;
    FirebaseAuth authClient;

    int currentItems, totalItems, scrolledOutItems, fetchedUsers = 0;

    ArrayList<String> fives = new ArrayList<>();
    ArrayList<String> fours = new ArrayList<>();
    ArrayList<String> threes = new ArrayList<>();
    ArrayList<String> twos = new ArrayList<>();
    ArrayList<String> ones = new ArrayList<>();
    ArrayList<String> matched_users = new ArrayList<>();

    ArrayList<Profile> display_list = new ArrayList<>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public DiscoverFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DiscoverFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DiscoverFragment newInstance(String param1, String param2) {
        DiscoverFragment fragment = new DiscoverFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        authClient = FirebaseAuth.getInstance();
        USER_ID = authClient.getCurrentUser().getUid();
        fsClient = FirebaseFirestore.getInstance();

        fetchMatches();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_discover, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.discover_recycler_view);
        progressBar = (ProgressBar) v.findViewById(R.id.discover_progress_bar);
        manager = new LinearLayoutManager(getActivity());
//        manager.setOrientation(LinearLayoutManager.VERTICAL);

        progressBar.setVisibility(View.GONE);


        Profile user = new Profile();
        user.setFirstName("Adwait");
        user.setLastName("Bhope");
        user.setBusinessName("Alchem Services");
        user.setBusinessCategory("Electronics");
        user.setBusinessDescription("Alchem Services is an authorized service center for Canon EOS DSLRs");
        user.setBio("I am an enthusiastic entrepreneur who likes to grow by collaborating with other electronics manufacturers");
        user.addHaves("Servicing");
        user.addHaves("Part replacement");
        user.addWants("Eletronic products");
        user.addWants("Spare parts");

        for (int i = 0; i < 15; i++) {
            display_list.add(user);
        }

        discoverAdapter = new DiscoverAdapter(display_list, getActivity());

        recyclerView.setAdapter(discoverAdapter);
        recyclerView.setLayoutManager(manager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = manager.getChildCount();
                totalItems = manager.getItemCount();
                scrolledOutItems = manager.findFirstVisibleItemPosition();

                if (isScrolling && (currentItems + scrolledOutItems >= totalItems - 5)) {
                    progressBar.setVisibility(View.VISIBLE);
                    fetchData();
                    progressBar.setVisibility(View.GONE);
                }

            }
        });

        return v;

    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.card_view_profile:
//                break;
//        }
//    }

    private void fetchMatches() {

//        TODO: merge the following two queries into one for better speed

        fsClient.collection("Users")
                .document(USER_ID)
                .collection("Details")
                .document("matchesFiveFourThree")
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot snapshot = task.getResult();
                            updateScoreLists(snapshot.getData());
                        } else {
                            Log.d("QUERY ERROR", "Task was not successful");
                        }
                    }
                });

        fsClient.collection("Users")
                .document(USER_ID)
                .collection("Details")
                .document("matchesTwoOne")
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot snapshot = task.getResult();
                            try {
                                updateScoreLists(snapshot.getData());
                            } catch (NullPointerException e) {
                                Log.d("QUERY ERROR", "NULL pointer exception");
                            }
                        } else {
                            Log.d("QUERY ERROR", "Task was not successful");
                        }
                    }
                });


    }

    private void updateScoreLists(Map<String, Object> data) {
        if (data != null) {
            if (data.containsKey("fives")) {
                fives = (ArrayList<String>) data.get("fives");
                fours = (ArrayList<String>) data.get("fours");
                threes = (ArrayList<String>) data.get("threes");

                Collections.shuffle(fives);
                Collections.shuffle(fours);
                Collections.shuffle(threes);

                matched_users.addAll(fives);
                matched_users.addAll(fours);
                matched_users.addAll(threes);

            } else {
                twos = (ArrayList<String>) data.get("twos");
                ones = (ArrayList<String>) data.get("ones");

                Collections.shuffle(twos);
                Collections.shuffle(ones);

                matched_users.addAll(twos);
                matched_users.addAll(ones);
            }
        }
    }

    private void fetchData() {

        //gets 5 more items from server

        //userIds are stored in matched_users. Index in fetchedUsers
        //fetches next 5 users in one single query

        try {
            fsClient.collection("Users")
                    .whereEqualTo("userId", matched_users.get(fetchedUsers++))
                    .whereEqualTo("userId", matched_users.get(fetchedUsers++))
                    .whereEqualTo("userId", matched_users.get(fetchedUsers++))
                    .whereEqualTo("userId", matched_users.get(fetchedUsers++))
                    .whereEqualTo("userId", matched_users.get(fetchedUsers++))
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    display_list.add(document.toObject(Profile.class));
                                }
                            }
                        }
                    });

            discoverAdapter.notifyDataSetChanged();
        } catch (Exception e) {
//            ERROR: COULD NOT FETCH USER
            Log.d("QUERY ERROR:", "NO USERS IN MATCHED USERS ARRAY");
        }

    }

//    private void loadInitialUsers() {
//
//        //get wants from shared preferences in ArrayList<String> wants
//        //in the format {businessCategory}_{want}
//
//        ArrayList<String> wants = new ArrayList<>();    //placeholder declaration as a substitute
//
//        //TODO optimize the following query structure
//        //to get the job done in a single query instead of five
//
//        for (wantId = 0; wantId < 5; wantId++) {
//            fsClient.collection("Haves")
//                    .document(wants.get(wantId))
//                    .get()
//                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                        @Override
//                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                            if (task.isSuccessful()) {
//                                updateUsersForWants((ArrayList<String>) task.getResult().getData().get("users"));
//                            }
//                        }
//                    });
//        }
//
//        sortMatches();
//        fetchData();
//
//    }
//
//    private void sortMatches() {
//
//    }
//
//    private void updateUsersForWants(ArrayList<String> usersForWant) {
//        usersForWants.add(wantId, usersForWant);
//    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
