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

/**
 * Created by Hasan on 8/18/2017.
 */

public class DogAdoptionFragment extends Fragment {
    int clickCounter = 0;

    private static final String ARG_SECTION_NUMBER = "section_number";
    private TextView dogNameTextView;
    private ImageView dogImageView;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tabbed, container, false);

        dogNameTextView = (TextView) rootView.findViewById(R.id.NameTextView);
        dogImageView = (ImageView) rootView.findViewById(R.id.Image);

        dogNameTextView.setText(SplashActivity.dogNamesArray.get(0));
        Picasso.with(this.getContext()).load(SplashActivity.dogURLsArray.get(0)).into(dogImageView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        dogImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("click counter", Integer.toString(clickCounter));
                clickCounter++;
                if (clickCounter == SplashActivity.dogNamesArray.size() - 1) {
                    clickCounter = 0;
                }
                try {
                    Log.i("image counter", "inside");
                    Picasso.with(getActivity()).load(SplashActivity.dogURLsArray.get(clickCounter)).into(dogImageView);
                    dogNameTextView.setText(SplashActivity.dogNamesArray.get(clickCounter));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}