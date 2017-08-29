package com.hasan.austinpetsalive;

import android.content.Context;
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

public class CatAdoptionFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";

    public CatAdoptionFragment() {
    }

    public static CatAdoptionFragment newInstance(int sectionNumber) {
        CatAdoptionFragment fragment = new CatAdoptionFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        Log.i("section number", Integer.toString(sectionNumber));
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recyclerview, container, false);

        RecyclerView rvCat = (RecyclerView)rootView.findViewById(R.id.recyclerView);
        rvCat.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rvCat.setLayoutManager(llm);

        CatRVAdapter adapter = new CatRVAdapter(SplashActivity.catInfo,getContext());
        rvCat.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
