package com.piedpipergeeks.aviate;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CalendarFragment extends Fragment {

    String USER_ID;
    FirebaseFirestore fsClient;
    CalendarView calendarView;
    ProgressBar progressBar;
    View v;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager manager;
    CalendarEventsAdapter adapter;

    ArrayList<Event> events;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public CalendarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalendarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalendarFragment newInstance(String param1, String param2) {
        CalendarFragment fragment = new CalendarFragment();
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


    }

    private void fetchEventsForUser(final int day, final int month, final int year) {

        final String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

//        for(int i = 0; i < user.clubs.size(); i++) {

        Timestamp currentTime = new Timestamp(new Date().getTime() / 1000, 0);

        fsClient.collection("Clubs")
                .document("1mfJzyo0z5vML82osVq0")
                .collection("Events")
                .whereLessThanOrEqualTo("timestamp", currentTime)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot documentSnapshot : task.getResult()) {
                                Event event = documentSnapshot.toObject(Event.class);
                                EpochConverter time = new EpochConverter(event.getTimestamp());

                                int d = time.getDay();
                                String m = time.getMonth();
                                int y = time.getYear();

                                if (d == day && m.equals(months[month]) && y == year) {
//                                    Log.d("LIST", "ADDED EVENT TO LIST");
                                    events.add(event);
//                                    Log.d("ADDING EVENT", String.valueOf(events.size()));
                                }

                            }
                            progressBar.setVisibility(View.GONE);
                            adapter.updateEvents(events);
                            adapter.notifyDataSetChanged();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("QUERY", e.toString());
                    }
                });
//        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_calendar, container, false);

        events = new ArrayList<>();
        recyclerView = v.findViewById(R.id.recycler_calendar_events);
        adapter = new CalendarEventsAdapter(events, getActivity());
        manager = new LinearLayoutManager(getActivity());
        ((LinearLayoutManager) manager).setOrientation(LinearLayoutManager.VERTICAL);

        progressBar = (ProgressBar) v.findViewById(R.id.recycler_calendar_events_progress_bar);
        progressBar.setVisibility(View.GONE);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager((LinearLayoutManager) manager);

        USER_ID = "some id";    //fetch userid from shared pref storage
        fsClient = FirebaseFirestore.getInstance();

        calendarView = v.findViewById(R.id.calendarView);

//        EpochConverter time = new EpochConverter(calendarView.getDate());
//        fetchEventsForUser(time.getYear(), time.getMonthAsInt(), time.getDay());

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                progressBar.setVisibility(View.VISIBLE);
                events.clear();
//                fetchEventsForUser(dayOfMonth, month, year);
                getEventsForUser(dayOfMonth, month, year);
                Log.d("FRAGMENT", String.valueOf(events.size()));

            }
        });
        return v;

    }

    private void getEventsForUser(int dayOfMonth, int month, int year) {

        SimpleDateFormat df = new SimpleDateFormat("dd MM yyyy");
//        try {
//            Log.d("TIMEEE", String.valueOf(df.parse(str)));
//        } catch (Exception e) {
//            Log.d("TIMEEE", "ERROR AALA");
//        }

        month++;

        String str_lower = dayOfMonth + " " + month + " " + year;
        String str_upper = dayOfMonth + " " + month + " " + year;

        Date selected_date_lower = new Date();
        Date selected_date_upper;
        try {
            selected_date_lower = df.parse(str_lower);
            selected_date_upper = df.parse(str_upper);
        } catch (Exception e) {

        }

        Log.d("TIMEEE", String.valueOf(selected_date_lower.getTime()));

        final Timestamp timestamp_lower = new Timestamp(selected_date_lower.getTime() / 1000, 0);
        final Timestamp timestamp_upper = new Timestamp((selected_date_lower.getTime() / 1000) + 86400, 0);


        fsClient = FirebaseFirestore.getInstance();
        fsClient.collection("Clubs")
                .whereArrayContains("members", FirebaseAuth.getInstance().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot snapshot : task.getResult()) {
                                fsClient.collection("Events")
//                                        .whereGreaterThan("timestamp", timestamp_lower)
                                        .whereLessThanOrEqualTo("timestamp", new Timestamp((new Date().getTime()/1000), 0))
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    Log.d("TIMEEE", "TASK SUCCESSFUL");
                                                    for (DocumentSnapshot snapshot1 : task.getResult()) {
                                                        events.add(snapshot1.toObject(Event.class));
                                                    }
                                                    Log.d("TIMEEE", events.toString());
                                                    progressBar.setVisibility(View.GONE);
                                                    adapter.updateEvents(events);
                                                    adapter.notifyDataSetChanged();
                                                }
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d("TIMEEE", e.toString());
                                            }
                                        });
                            }

                            adapter.updateEvents(events);
                            adapter.notifyDataSetChanged();

                        }
                    }
                });

    }

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

    public class EpochConverter {
        private int day, year;
        private String month;
        private long epochSeconds;
        private String date;
        private ArrayList<String> months;

        public EpochConverter(Timestamp timestamp) {
            epochSeconds = timestamp.getSeconds() * 1000;
            date = new Date(epochSeconds).toString();
            calculate();
        }

        private void calculate() {
            months = new ArrayList<>();
            months.add("Jan");
            months.add("Feb");
            months.add("Mar");
            months.add("Apr");
            months.add("May");
            months.add("Jun");
            months.add("Jul");
            months.add("Aug");
            months.add("Sep");
            months.add("Oct");
            months.add("Nov");
            months.add("Dec");
            day = Integer.valueOf(date.substring(8, 10));
            month = date.substring(4, 7);
            Log.d("DATE", date);
            year = Integer.valueOf(date.substring(30, 34));
        }

        public EpochConverter(long epochSeconds) {
            this.epochSeconds = epochSeconds;
            date = new Date(epochSeconds).toString();
            calculate();
        }

        public int getDay() {
            return day;
        }

        public String getMonth() {
            return month;
        }

        public int getYear() {
            return year;
        }

        public int getMonthAsInt() {
            return months.indexOf(month) + 1;
        }
    }

}
