package com.hasan.austinpetsalive;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class DogAdoptionFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";

    public DogAdoptionFragment() {
    }


    public static DogAdoptionFragment newInstance(int sectionNumber) {
        DogAdoptionFragment fragment = new DogAdoptionFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        Log.i("section number", Integer.toString(sectionNumber));
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recyclerview, container, false);

        RecyclerView rv = (RecyclerView)rootView.findViewById(R.id.recyclerView);
        rv.setHasFixedSize(true);
        Log.i("test","1");
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        Log.i("test","2");
        rv.setLayoutManager(llm);
        Log.i("test","3");

        RVAdapter adapter = new RVAdapter(SplashActivity.dogInfo,getContext());
        Log.i("test","4");
        rv.setAdapter(adapter);
        Log.i("test","5");

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}