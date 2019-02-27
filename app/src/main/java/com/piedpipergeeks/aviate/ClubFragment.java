package com.piedpipergeeks.aviate;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
//import android.support.v4.app.Fragment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ClubFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ClubFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClubFragment extends Fragment {
    View v;
    RecyclerView recyclerView;
    LinearLayoutManager manager;
    ClubAdapter clubadapter;
    Boolean isScrolling = false;
    ArrayList<Club> display_list = new ArrayList<>();
    FloatingActionButton button;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ClubFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ClubFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ClubFragment newInstance(String param1, String param2) {
        ClubFragment fragment = new ClubFragment();
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
       // Toast.makeText(getActivity(), "Fragment called", Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_club, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.club_recycler_view);
        button = (FloatingActionButton) v.findViewById(R.id.club_create_club);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CreateClubActivity.class));
            }
        });

        manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        Club user = new Club();
        user.setName("deAsra Club");
        user.setInfo("deAsra Club");
        user.setPresident("Shreyas Garud");
        user.setSecretary("Prashant Bhandari");
        user.addAdmin("Adwait Bhope");
        user.addGuest("Vinod Kamat");

        for (int i = 0; i < 13; i++) {
            display_list.add(user);
        }

//        recyclerView.noti
//        recyclerView.setLayoutManager(manager);
        clubadapter = new ClubAdapter(display_list, getActivity());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(clubadapter);
//        clubadapter.updateData(display_list);
//        clubadapter.notifyDataSetChanged();
        return v;
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
}
